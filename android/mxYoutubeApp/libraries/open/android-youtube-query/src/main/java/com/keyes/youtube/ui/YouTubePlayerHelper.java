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