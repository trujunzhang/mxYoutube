package com.keyes.youtube.ui;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.AbstractAQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.keyes.youtube.beans.*;
import com.keyes.youtube.callback.VideoInfoTaskCallback;
import com.keyes.youtube.utils.YouTubeUtility;
import com.keyes.youtube.utils.YoutubeQuality;

/**
 * <p>
 * Activity that will play a video from YouTube. A specific video or the latest video in a YouTube playlist can be
 * specified in the intent used to invoke this activity. The data of the intent can be set to a specific video by using
 * an Intent data URL of the form:
 * </p>
 * <p/>
 * 
 * <pre>
 *     ytv://videoid
 * </pre>
 * <p/>
 * <p>
 * where
 * 
 * <pre>
 * videoid
 * </pre>
 * 
 * is the ID of the YouTube video to be played.
 * </p>
 * <p/>
 * <p>
 * If the user wishes to play the latest video in a YouTube playlist, the Intent data URL should be of the form:
 * </p>
 * <p/>
 * 
 * <pre>
 *     ytpl://playlistid
 * </pre>
 * <p/>
 * <p>
 * where
 * 
 * <pre>
 * playlistid
 * </pre>
 * 
 * is the ID of the YouTube playlist from which the latest video is to be played.
 * </p>
 * <p/>
 * <p>
 * Code used to invoke this intent should look something like the following:
 * </p>
 * <p/>
 * 
 * <pre>
 * Intent lVideoIntent = new Intent(null, Uri.parse(&quot;ytpl://&quot; + YOUTUBE_PLAYLIST_ID), this,
 * 		OpenYouTubePlayerActivity.class);
 * startActivity(lVideoIntent);
 * </pre>
 * <p/>
 * <p>
 * There are several messages that are displayed to the user during various phases of the video load process. If you
 * wish to supply text other than the default english messages (e.g., internationalization, etc.), you can pass the text
 * to be used via the Intent's extended data. The messages that can be customized include:
 * <p/>
 * <ul>
 * <li>com.keyes.video.msg.init - activity is initializing.</li>
 * <li>com.keyes.video.msg.detect - detecting the bandwidth available to download video.</li>
 * <li>com.keyes.video.msg.playlist - getting latest video from playlist.</li>
 * <li>com.keyes.video.msg.token - retrieving token from YouTube.</li>
 * <li>com.keyes.video.msg.loband - buffering low-bandwidth.</li>
 * <li>com.keyes.video.msg.hiband - buffering hi-bandwidth.</li>
 * <li>com.keyes.video.msg.error.title - dialog title displayed if anything goes wrong.</li>
 * <li>com.keyes.video.msg.error.msg - message displayed if anything goes wrong.</li>
 * </ul>
 * <p/>
 * <p>
 * For example:
 * </p>
 * <p/>
 * 
 * <pre>
 *      Intent lVideoIntent = new Intent(null, Uri.parse("ytpl://"+YOUTUBE_PLAYLIST_ID), this, OpenYouTubePlayerActivity.class);
 *      lVideoIntent.putExtra("com.keyes.video.msg.init", getString("str_video_intro"));
 *      lVideoIntent.putExtra("com.keyes.video.msg.detect", getString("str_video_detecting_bandwidth"));
 *      ...
 *      startActivity(lVideoIntent);
 * </pre>
 *
 * @author David Keyes
 */
public class YouTubePlayerHelper {
	private VideoInfoTaskCallback videoInfoTaskCallback;

	public YoutubeTaskInfo taskInfo;

	public String mVideoId = null;
	public YoutubeQuality youtubeQuality = null;

	public void setVideoInfoTaskCallback(VideoInfoTaskCallback videoInfoTaskCallback) {
		this.videoInfoTaskCallback = videoInfoTaskCallback;
	}

	private String getVideoString(Uri lVideoIdUri) {
		String lVideoIdStr = lVideoIdUri.getEncodedSchemeSpecificPart();
		if (lVideoIdStr.startsWith("//")) {
			if (lVideoIdStr.length() > 2) {
				lVideoIdStr = lVideoIdStr.substring(2);
			} else {
				Log.i(this.getClass().getSimpleName(),
						"No video ID was specified in the intent.  Closing video activity.");
			}
		}
		return lVideoIdStr;
	}

	public YouTubeId getYouTubeId(Uri lVideoIdUri) {
		String lVideoIdStr = getVideoString(lVideoIdUri);

		return this.getYouTubeId(lVideoIdUri.getScheme(), lVideoIdStr);
	}

	public YouTubeId getYouTubeId(String lVideoSchemeStr, String lVideoIdStr) {
		// /////////////////
		// extract either a video id or a playlist id, depending on the uri scheme
		YouTubeId lYouTubeId = null;
		if (lVideoSchemeStr != null && lVideoSchemeStr.equalsIgnoreCase(Youtube_URL.SCHEME_YOUTUBE_PLAYLIST)) {
			lYouTubeId = new PlaylistId(lVideoIdStr);
		} else if (lVideoSchemeStr != null && lVideoSchemeStr.equalsIgnoreCase(Youtube_URL.SCHEME_YOUTUBE_VIDEO)) {
			lYouTubeId = new VideoId(lVideoIdStr);
		} else if (lVideoSchemeStr != null && lVideoSchemeStr.equalsIgnoreCase(Youtube_URL.SCHEME_FILE)) {
			lYouTubeId = new FileId(lVideoIdStr);
		}
		return lYouTubeId;
	}

	public View setupView(Context context) {
		LinearLayout lLinLayout = new LinearLayout(context);
		return lLinLayout;
	}

	public void setupView() {

	}

	public void makeAndExecuteYoutubeTask(Context context, Uri lVideoIdUri) {
		prepareAndPlay(context, Youtube_URL.YOUTUBE_VIDEO_INFORMATION_URL + this.getYouTubeId(lVideoIdUri).getId());
	}

	/**
	 * 
	 * http://www.youtube.com/get_video_info?&video_id=AV2OkzIGykA
	 * 
	 * enablecsi=1&avg_rating=4.71428571429&plid=AAUEBYz02k2neG9C&iurlmq=http%3A%2F%2Fi.ytimg.com%2Fvi%2FAV2OkzIGykA%2F
	 * mqdefault
	 * .jpg&adaptive_fmts=init%3D0-712%26clen%3D10156939%26itag%3D137%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7
	 * .googlevideo
	 * .com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252
	 * C927622%25252
	 * C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252
	 * C945534%25252
	 * C946013%25252C947209%25252C952302%25252C953801%2526signature%253D57B4EE0BFB6158E6AFFA5297ABD0ED6E257047B5
	 * .BA22217B5B0C5181270DF36CC84E98E9B1851494
	 * %2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252
	 * Cip%25252Cipbits%25252Citag%25252Clmt%25252
	 * Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6
	 * SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv
	 * -zz78JS8Hn0TU-muurJp0%2526itag%253D137%2526initcwndbps%253D10895000
	 * %2526sver%253D3%2526lmt%253D1400979696242889%2526
	 * clen%253D10156939%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%
	 * 2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.201%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.640028%2522%26size%3D1920x1080%26bitrate%3D1652166%26fps%3D30%26index%3D713-1428%26lmt%3D1400979696242889%2Cinit%3D0-710%26clen%3D5587638%26itag%3D136%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253DA5EC5E99E2D53534740ECB26D915768037364304.BFD1C54951CC9738D6E5837C162E95478BCFB809%2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D136%2526initcwndbps%253D10895000%2526sver%253D3%2526lmt%253D1400979674247329%2526clen%253D5587638%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.201%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d401f%2522%26size%3D1280x720%26bitrate%3D876254%26fps%3D30%26index%3D711-1426%26lmt%3D1400979674247329%2Cinit%3D0-709%26clen%3D3116920%26itag%3D135%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D667B7D73F56FF1355F12B3DF5A17FB0E0F854E6E.E01A8DE2CB9588F8201F2D9D2492AA0C43762679%2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D135%2526initcwndbps%253D10895000%2526sver%253D3%2526lmt%253D1400979667737312%2526clen%253D3116920%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.201%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d401f%2522%26size%3D854x480%26bitrate%3D460515%26fps%3D30%26index%3D710-1425%26lmt%3D1400979667737312%2Cinit%3D0-709%26clen%3D1824866%26itag%3D134%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D61B7C2917578410755781D17E38810BF3FA6C2F4.50C23D0122D77836244F89C39E4023A7D4E8B25A%2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D134%2526initcwndbps%253D10895000%2526sver%253D3%2526lmt%253D1400979674250680%2526clen%253D1824866%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.201%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d401e%2522%26size%3D640x360%26bitrate%3D237172%26fps%3D30%26index%3D710-1425%26lmt%3D1400979674250680%2Cinit%3D0-674%26clen%3D5341276%26itag%3D133%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D90738D3E3558959F31595CC360860A720A39F4C8.E9696B4A43ECF026ECAFA54E64EEB102C56CE9E2%2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D133%2526initcwndbps%253D10895000%2526sver%253D3%2526lmt%253D1400979665741651%2526clen%253D5341276%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.201%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d4015%2522%26size%3D426x240%26bitrate%3D312857%26fps%3D30%26index%3D675-1390%26lmt%3D1400979665741651%2Cinit%3D0-671%26clen%3D2395057%26itag%3D160%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253DE1D4451A229B279E0C9B6FEECFAD7BB1DC880B67.6096465A5A4D4A821E28287F96D19F0B69B1A151%2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D160%2526initcwndbps%253D10895000%2526sver%253D3%2526lmt%253D1400979679195307%2526clen%253D2395057%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.201%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d400c%2522%26size%3D256x144%26bitrate%3D126419%26fps%3D15%26index%3D672-1387%26lmt%3D1400979679195307%2Cinit%3D0-591%26clen%3D4580527%26itag%3D140%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D882137833FEE46CFC3C0B4F412010BCB204FB36B.C28EBC3308228F7E2C7FA762FB6E9B2A8FEE057D%2526sparams%253Dclen%25252Cdur%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253D6SQjV_zN8RQ%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D140%2526initcwndbps%253D10895000%2526sver%253D3%2526lmt%253D1400979658705689%2526clen%253D4580527%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526gir%253Dyes%2526mt%253D1411796664%2526expire%253D1411818369%2526mv%253Dm%2526dur%253D285.280%2526source%253Dyoutube%26type%3Daudio%252Fmp4%253B%2Bcodecs%253D%2522mp4a.40.2%2522%26bitrate%3D129681%26index%3D592-971%26lmt%3D1400979658705689&csi_page_type=embed&vid=AV2OkzIGykA&allow_ratings=1&video_verticals=%5B5%2C+3%2C+24%2C+358%2C+694%5D&view_count=15154&video_id=AV2OkzIGykA&idpj=-9&storyboard_spec=http%3A%2F%2Fi.ytimg.com%2Fsb%2FAV2OkzIGykA%2Fstoryboard3_L%24L%2F%24N.jpg%7C48%2327%23100%2310%2310%230%23default%23_uXoXmQ1zEN_hF8znOaRPTfsiTE%7C80%2345%23144%2310%2310%232000%23M%24M%23ANEBr6hw-7StJ6QRF7aPeYktJG4%7C160%2390%23144%235%235%232000%23M%24M%23qctXDd_UnDWugylK5o3Qq19-gM8&sffb=True&iurl=http%3A%2F%2Fi.ytimg.com%2Fvi%2FAV2OkzIGykA%2Fhqdefault.jpg&url_encoded_fmt_stream_map=quality%3Dhd720%26fallback_host%3Dtc.v24.cache1.googlevideo.com%26itag%3D22%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253DE3679C7EF40777BBF30596A0B530A91CD0E7E661.9CC7FAFD229F9DAD6A139F1FE60A3C240A77647C%2526sparams%253Did%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Cmm%25252Cms%25252Cmv%25252Cratebypass%25252Csource%25252Cupn%25252Cexpire%2526upn%253DN_lz2zavchE%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D22%2526initcwndbps%253D10895000%2526key%253Dyt5%2526ratebypass%253Dyes%2526mm%253D31%2526ms%253Dau%2526mv%253Dm%2526mt%253D1411796664%2526expire%253D1411818369%2526sver%253D3%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.64001F%252C%2Bmp4a.40.2%2522%2Cquality%3Dmedium%26fallback_host%3Dtc.v16.cache6.googlevideo.com%26itag%3D43%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253DD4503AC20D9392EAD3B1EE25B10306E2B0342495.0FFE21E4604730F2A647862EFE18CCB528914A04%2526sparams%253Did%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Cmm%25252Cms%25252Cmv%25252Cratebypass%25252Csource%25252Cupn%25252Cexpire%2526upn%253DN_lz2zavchE%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D43%2526initcwndbps%253D10895000%2526key%253Dyt5%2526ratebypass%253Dyes%2526mm%253D31%2526ms%253Dau%2526mv%253Dm%2526mt%253D1411796664%2526expire%253D1411818369%2526sver%253D3%2526source%253Dyoutube%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp8.0%252C%2Bvorbis%2522%2Cquality%3Dmedium%26fallback_host%3Dtc.v5.cache1.googlevideo.com%26itag%3D18%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D88F7D3F2C7336697E2B4456C50F91FB01567C97B.0EABF39B2FD8F8C24C9E536D5540AB7602E87FDB%2526sparams%253Did%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Cmm%25252Cms%25252Cmv%25252Cratebypass%25252Csource%25252Cupn%25252Cexpire%2526upn%253DN_lz2zavchE%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D18%2526initcwndbps%253D10895000%2526key%253Dyt5%2526ratebypass%253Dyes%2526mm%253D31%2526ms%253Dau%2526mv%253Dm%2526mt%253D1411796664%2526expire%253D1411818369%2526sver%253D3%2526source%253Dyoutube%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.42001E%252C%2Bmp4a.40.2%2522%2Cquality%3Dsmall%26fallback_host%3Dtc.v3.cache1.googlevideo.com%26itag%3D5%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D1EC7E5A79FBEE810A1222DF12EDB48D092EF7518.5E3C6CB2CC5FB91FA5C65971CDB64D81CB4F0788%2526sparams%253Did%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253DN_lz2zavchE%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D5%2526initcwndbps%253D10895000%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526mv%253Dm%2526mt%253D1411796664%2526expire%253D1411818369%2526sver%253D3%2526source%253Dyoutube%26type%3Dvideo%252Fx-flv%2Cquality%3Dsmall%26fallback_host%3Dtc.v24.cache1.googlevideo.com%26itag%3D36%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253D9C28D8D83657C61572C346F45506421A1561A09F.E17655BE78ED3AFDD60D9770558432795A225752%2526sparams%253Did%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253DN_lz2zavchE%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D36%2526initcwndbps%253D10895000%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526mv%253Dm%2526mt%253D1411796664%2526expire%253D1411818369%2526sver%253D3%2526source%253Dyoutube%26type%3Dvideo%252F3gpp%253B%2Bcodecs%253D%2522mp4v.20.3%252C%2Bmp4a.40.2%2522%2Cquality%3Dsmall%26fallback_host%3Dtc.v10.cache8.googlevideo.com%26itag%3D17%26url%3Dhttp%253A%252F%252Fr7---sn-a5m7lne7.googlevideo.com%252Fvideoplayback%253Fip%253D174.139.13.196%2526ipbits%253D0%2526fexp%253D908579%25252C921729%25252C927622%25252C930666%25252C931983%25252C932404%25252C934030%25252C936117%25252C937426%25252C945118%25252C945310%25252C945534%25252C946013%25252C947209%25252C952302%25252C953801%2526signature%253DF9306769D049EA79593BFD9FE1ECB6EDC0C9560E.46BAE3384A37BA91CDF47E7D7E9C1E917276FF67%2526sparams%253Did%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Cmm%25252Cms%25252Cmv%25252Csource%25252Cupn%25252Cexpire%2526upn%253DN_lz2zavchE%2526id%253Do-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2526itag%253D17%2526initcwndbps%253D10895000%2526key%253Dyt5%2526mm%253D31%2526ms%253Dau%2526mv%253Dm%2526mt%253D1411796664%2526expire%253D1411818369%2526sver%253D3%2526source%253Dyoutube%26type%3Dvideo%252F3gpp%253B%2Bcodecs%253D%2522mp4v.20.3%252C%2Bmp4a.40.2%2522&eventid=IU8mVMDiLJKx-gOl6YDwAw&baseUrl=http%3A%2F%2Fgoogleads.g.doubleclick.net%2Fpagead%2Fviewthroughconversion%2F962985656%2F&allowed_ads=set%28%5B8%2C+1%2C+2%2C+10%2C+0%5D%29&oid=vvt0p7onXAQm3hFTAhagKA&ptchn=yU5wkjgQYGRB0hIHMwm2Sg&gut_tag=%2F4061%2Fytunknown%2Fmain&mpvid=5yYkfzVY4D5EGqfE&instream_long=False&author=LevelUpTuts&show_content_thumbnail=True&aid=P-emJUC5mPc&cl=76266293&iurlsd=http%3A%2F%2Fi.ytimg.com%2Fvi%2FAV2OkzIGykA%2Fsddefault.jpg&ad_logging_flag=1&ad_slots=0&token=vjVQa1PpcFP7STYOXYFePFmXoTWfk5-l5fDrW2SX-8g%3D&iurlhq=http%3A%2F%2Fi.ytimg.com%2Fvi%2FAV2OkzIGykA%2Fhqdefault.jpg&allow_embed=1&ad_module=http%3A%2F%2Fs.ytimg.com%2Fyts%2Fswfbin%2Fplayer-vfla8uKPM%2Fad.swf&excluded_ads=3%3D1_1%2C1_2_1%2C1_3%2C2_2_1%2C2_3&ad_flags=0&ptk=LevelUpTuts%252Buser&vq=auto&watermark=%2Chttp%3A%2F%2Fs.ytimg.com%2Fyts%2Fimg%2Fwatermark%2Fyoutube_watermark-vflHX6b6E.png%2Chttp%3A%2F%2Fs.ytimg.com%2Fyts%2Fimg%2Fwatermark%2Fyoutube_hd_watermark-vflAzLcD6.png&c=WEB&use_cipher_signature=False&cafe_experiment_id=&uid=yU5wkjgQYGRB0hIHMwm2Sg&cid=2954351&iv_module=http%3A%2F%2Fs.ytimg.com%2Fyts%2Fswfbin%2Fplayer-vfljfyyDW%2Fiv_module.swf&title=Sketch+3+Tutorials+-+%231+Series+Introduction&length_seconds=286&host_language=en&ldpj=-22&cut_ad_for_ypc=False&cc_font=Arial+Unicode+MS%2C+arial%2C+verdana%2C+_sans&afv_ad_tag=http%3A%2F%2Fgoogleads.g.doubleclick.net%2Fpagead%2Fads%3Fad_type%3Dtext_image_flash%26client%3Dca-pub-6219811747049371%26description_url%3Dhttp%253A%252F%252Fwww.youtube.com%252Fvideo%252FAV2OkzIGykA%26hl%3Den%26host%3Dca-host-pub-0771856019955508%26ht_id%3D4608523%26loeid%3D908579%2C921729%2C936117%2C937426%2C945118%2C945310%2C945534%2C946013%26url%3Dhttp%253A%252F%252Fwww.youtube.com%252Fvideo%252FAV2OkzIGykA%26ytdevice%3D1%26yt_pt%3DAPb3F2_RTAu1ZpUoeBbXtSmPk6MAjrdElovelg5itUWQY6hAQeox3_8zsG9dWM4KN1w4DVX2tzloAKC2Jiqoj3gZAIlgLZXmTqQuGyhkNgjqgNN5lFANCAB5Af1iSLduF8QxGSWcFChP5bLS6Vsbsp0mupPfZrvUtez27QqHjg%26channel%3Dyt_mpvid_5yYkfzVY4D5EGqfE%252Byt_cid_2954351%252Bivp%252Byt_no_ap%252Bytdevice_1%252Byt_no_cp%252Bafv_user_id_yU5wkjgQYGRB0hIHMwm2Sg%252Bafv_user_leveluptuts%252Byt_gp%252Bytel_embedded%252Bytps_default%252Bafv_overlay%252Binvideo_overlay_480x70_cat27&ad_device=1&iv3_module=1&tag_for_child_directed=False&iv_load_policy=1&pltype=content&focEnabled=1&ttsurl=http%3A%2F%2Fwww.youtube.com%2Fapi%2Ftimedtext%3Fhl%3Den_US%26asr_langs%3Dko%252Cja%252Cen%252Cru%252Cpt%252Cde%252Cfr%252Cnl%252Cit%252Ces%26sparams%3Dasr_langs%252Ccaps%252Cv%252Cexpire%26signature%3D95C29415856FDE9EAF5B83FCCA29DCB517E3576B.C492A2E9FB3A6FF13CC080CDB17998415B46D1D6%26v%3DAV2OkzIGykA%26expire%3D1411821969%26key%3Dyttt1%26caps%3Dasr&iv_invideo_url=http%3A%2F%2Fwww.youtube.com%2Fannotations_invideo%3Fcap_hist%3D1%26cta%3D2%26video_id%3DAV2OkzIGykA&midroll_freqcap=420.0&thumbnail_url=http%3A%2F%2Fi.ytimg.com%2Fvi%2FAV2OkzIGykA%2Fdefault.jpg&cc_asr=1&fmt_list=22%2F1280x720%2F9%2F0%2F115%2C43%2F640x360%2F99%2F0%2F0%2C18%2F640x360%2F9%2F0%2F115%2C5%2F426x240%2F7%2F0%2F0%2C36%2F426x240%2F99%2F1%2F0%2C17%2F256x144%2F99%2F1%2F0&tmi=1&cc_module=http%3A%2F%2Fs.ytimg.com%2Fyts%2Fswfbin%2Fplayer-vfljfyyDW%2Fsubtitle_module.swf&afv=True&loeid=908579%2C921729%2C936117%2C937426%2C945118%2C945310%2C945534%2C946013&timestamp=1411796769&dash=1&fexp=908579%2C921729%2C927622%2C930666%2C931983%2C932404%2C934030%2C936117%2C937426%2C945118%2C945310%2C945534%2C946013%2C947209%2C952302%2C953801&iurlmaxres=http%3A%2F%2Fi.ytimg.com%2Fvi%2FAV2OkzIGykA%2Fmaxresdefault.jpg&muted=0&ytfocEnabled=1&allow_html5_ads=1&status=ok&has_cc=True&cc3_module=1&no_get_video_log=1&hl=en_US&adsense_video_doc_id=yt_AV2OkzIGykA&iv_allow_in_place_switch=1&dashmpd=http%3A%2F%2Fmanifest.googlevideo.com%2Fapi%2Fmanifest%2Fdash%2Fip%2F174.139.13.196%2Fkey%2Fyt5%2Fipbits%2F0%2Ffexp%2F908579%252C921729%252C927622%252C930666%252C931983%252C932404%252C934030%252C936117%252C937426%252C945118%252C945310%252C945534%252C946013%252C947209%252C952302%252C953801%2Fsignature%2F2BD0FC2178E8A2413305CB820ECF4E347DFCA730.DCF4E5CD719B00266002961A423C912E5C224D16%2Fmm%2F31%2Fplayback_host%2Fr7---sn-a5m7lne7.googlevideo.com%2Fms%2Fau%2Fid%2Fo-AEgplaZRRCgqDbOLW7CxcIv-zz78JS8Hn0TU-muurJp0%2Fupn%2F6mh3ovPWhAQ%2Fitag%2F0%2Fmt%2F1411796664%2Fsver%2F3%2Fexpire%2F1411818369%2Fmv%2Fm%2Fsparams%2Fas%252Cid%252Cip%252Cipbits%252Citag%252Cmm%252Cms%252Cmv%252Cplayback_host%252Csource%252Cexpire%2Fas%2Ffmp4_audio_clear%252Cwebm_audio_clear%252Cfmp4_sd_hd_clear%252Cwebm_sd_hd_clear%252Cwebm2_sd_hd_clear%2Fsource%2Fyoutube&keywords=tutorials%2Ccms%2Chtml%2Ccss%2Chtml5%2Ccss3%2Ceducation%2Cfree%2Clessons%2Ctuts%2Ctutorial%2Clearn%2Csoftware%2Cweb+development%2Cweb+developer%2Cdeveloper%2Cweb%2Cwebsite%2Csketch%2Csketch+app%2Csketch+3%2Cvector&as_launched_in_country=1&pyv_in_related_cafe_experiment_id=&shortform=True&account_playback_token=QUFFLUhqa1ZUX3doODJUYlRndExiUUNKckthcXpGdTdCUXxBQ3Jtc0tuTDc4eFpoVTQ0TGFOZkJ4LVNsd0JDczkwUjB2TTdST01jV0t1UDVZcktKWERuT3l5azkxeUx5ZjdxZzRRZUZ3NDd0UkRWZ1ZFb2JhdVFLVzFJRXh1dHNRYnZ1MGlpWDhHazZLajlMeXNnZDhhTnRaWQ%3D%3D&midroll_prefetch_size=
	 * 1
	 * 
	 * @param context
	 * @param uri
	 */
	private void prepareAndPlay(final Context context, String uri) {
		// AV2OkzIGykA
		// perform a Google search in just a few lines of code
		final AbstractAQuery aq = new AQuery(context);
		aq.ajax(uri, String.class, new AjaxCallback<String>() {
			@Override
			public void callback(String url, String json, AjaxStatus status) {
				if (json != null) {
					// successful ajax call, show status code and json content
					youtubeQuality = YouTubeUtility.getFinalUri(json);
					String lUriStr = YouTubeUtility.getUrlByQuality(youtubeQuality, true, taskInfo.lYouTubeFmtQuality);
					videoInfoTaskCallback.startYoutubeTask(lUriStr);
				} else {
					// ajax error, show error code
					Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public void stopYoutubeTask(Context context) {
		YouTubeUtility.markVideoAsViewed(context, mVideoId);
	}

}