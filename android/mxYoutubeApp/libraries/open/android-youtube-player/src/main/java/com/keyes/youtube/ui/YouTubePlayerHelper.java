package com.keyes.youtube.ui;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import com.androidquery.AQuery;
import com.androidquery.AbstractAQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.keyes.youtube.beans.*;
import com.keyes.youtube.utils.YouTubeUtility;

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

	public ProgressBar mProgressBar;
	public TextView mProgressMessage;
	public VideoView mVideoView;

	public YoutubeTaskInfo taskInfo;

	/**
	 * Background task on which all of the interaction with YouTube is done
	 */
	// protected QueryYouTubeTask mQueryYouTubeTask;

	public String mVideoId = null;

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
		setupView(context, lLinLayout);
		return lLinLayout;
	}

	public void setupView(VideoView videoView, ProgressBar progressBar, TextView textView) {
		this.mVideoView = videoView;
//		this.mProgressBar = progressBar;
		this.mProgressMessage = textView;
	}

	/**
	 * Create the view in which the video will be rendered.
	 */
	public void setupView(Context context, LinearLayout lLinLayout) {
		lLinLayout.setId(1);
		lLinLayout.setOrientation(LinearLayout.VERTICAL);
		lLinLayout.setGravity(Gravity.CENTER);
		lLinLayout.setBackgroundColor(Color.BLACK);

		LayoutParams lLinLayoutParms = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lLinLayout.setLayoutParams(lLinLayoutParms);

		RelativeLayout lRelLayout = new RelativeLayout(context);
		lRelLayout.setId(2);
		lRelLayout.setGravity(Gravity.CENTER);
		lRelLayout.setBackgroundColor(Color.BLACK);
		RelativeLayout.LayoutParams lRelLayoutParms = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		lRelLayout.setLayoutParams(lRelLayoutParms);
		lLinLayout.addView(lRelLayout);

		mVideoView = new VideoView(context);
		mVideoView.setId(3);
		RelativeLayout.LayoutParams lVidViewLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		lVidViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mVideoView.setLayoutParams(lVidViewLayoutParams);
		lRelLayout.addView(mVideoView);

		mProgressBar = new ProgressBar(context);
		mProgressBar.setIndeterminate(true);
		mProgressBar.setVisibility(View.VISIBLE);
		mProgressBar.setEnabled(true);
		mProgressBar.setId(4);
		RelativeLayout.LayoutParams lProgressBarLayoutParms = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lProgressBarLayoutParms.addRule(RelativeLayout.CENTER_IN_PARENT);
		mProgressBar.setLayoutParams(lProgressBarLayoutParms);
		lRelLayout.addView(mProgressBar);

		mProgressMessage = new TextView(context);
		mProgressMessage.setId(5);
		RelativeLayout.LayoutParams lProgressMsgLayoutParms = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lProgressMsgLayoutParms.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lProgressMsgLayoutParms.addRule(RelativeLayout.BELOW, 4);
		mProgressMessage.setLayoutParams(lProgressMsgLayoutParms);
		mProgressMessage.setTextColor(Color.LTGRAY);
		mProgressMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		mProgressMessage.setText("...");
		lRelLayout.addView(mProgressMessage);
	}

	public void destroyView() {
		// this.mQueryYouTubeTask = null;
		this.mVideoView = null;
	}

	public void initProgressBar() {
//		mProgressBar.bringToFront();
//		mProgressBar.setVisibility(View.VISIBLE);
		mProgressMessage.setText(taskInfo.mMsgInit);
	}

	public void updateProgress(String pProgressMsg) {
		try {
			mProgressMessage.setText(pProgressMsg);
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "Error updating video status!", e);
		}
	}

	public void makeAndExecuteYoutubeTask(Context context, Uri lVideoIdUri) {
		makeAndExecuteYoutubeTask(context, this.getYouTubeId(lVideoIdUri));
	}

	public void makeAndExecuteYoutubeTask(Context context, YouTubeId lYouTubeId) {
		prepareAndPlay(context, Youtube_URL.YOUTUBE_VIDEO_INFORMATION_URL + lYouTubeId.getId());
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
					String lUriStr = YouTubeUtility.getFinalUri(taskInfo.lYouTubeFmtQuality, true, json);
					startYoutubeTask(context, Uri.parse(lUriStr));
				} else {
					// ajax error, show error code
					Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	protected void startYoutubeTask(Context context, Uri pResult) {
		try {
			if (pResult == null) {
				throw new RuntimeException("Invalid NULL Url.");
			}
			// http%3A%2F%2Fr7---sn-i3b7snl7.googlevideo.com%2Fvideoplayback%3Finitcwndbps%3D210375%26source%3Dyoutube%26expire%3D1409580367%26ipbits%3D0%26key%3Dyt5%26ms%3Dau%26upn%3DG_8WcfW6ibo%26mv%3Dm%26mt%3D1409558662%26fexp%3D902408%252C916600%252C916630%252C918015%252C923345%252C927622%252C931983%252C932404%252C932625%252C934024%252C934030%252C946010%252C946506%252C949501%252C953801%26id%3Do-ACP0TRiYWc7qkIrOsVu0oqPQv08yryrLy80WpqGVpVUu%26signature%3D42EA1C0406FDEEC1E65C0B4FA20558FE2931975B.A47599A424514B7A78D726A5DECCABAFA470B462%26sver%3D3%26sparams%3Did%252Cinitcwndbps%252Cip%252Cipbits%252Citag%252Cmm%252Cms%252Cmv%252Csource%252Cupn%252Cexpire%26itag%3D17%26mm%3D31%26ip%3D121.127.250.133&signature=null
			mVideoView.setVideoURI(pResult);

			// TODO: add listeners for finish of video
			mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

				public void onCompletion(MediaPlayer pMp) {
					// if (isCancelled())
					// return;
					// openYouTubePlayerActivity.finish(); // TODO [USED]
				}

			});

			final MediaController lMediaController = new MediaController(context);
			mVideoView.setMediaController(lMediaController);

			if (taskInfo.showControllerOnStartup)
				lMediaController.show(0);

			// mVideoView.setKeepScreenOn(true);
			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

				public void onPrepared(MediaPlayer pMp) {
					// if (isCancelled())
					// return;
//					mProgressBar.setVisibility(View.GONE);
//					mProgressMessage.setVisibility(View.GONE);
				}

			});

			// if (isCancelled())
			// return;

			mVideoView.requestFocus();
			mVideoView.start();
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "Error playing video!", e);

			// if (!mShowedError) {
			// showErrorAlert();
			// }
		}
	}

	public void stopYoutubeTask(Context context) {
		YouTubeUtility.markVideoAsViewed(context, mVideoId);

		// if (mQueryYouTubeTask != null) {
		// mQueryYouTubeTask.cancel(true);
		// }

		if (mVideoView != null) {
			mVideoView.stopPlayback();
		}
	}
}