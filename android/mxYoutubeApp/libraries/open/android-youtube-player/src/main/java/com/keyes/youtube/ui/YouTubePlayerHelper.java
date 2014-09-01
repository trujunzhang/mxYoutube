package com.keyes.youtube.ui;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import com.keyes.youtube.beans.*;
import com.keyes.youtube.task.QueryYouTubeTask;
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
	protected QueryYouTubeTask mQueryYouTubeTask;

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
		this.mQueryYouTubeTask = null;
		this.mVideoView = null;
	}

	public void initProgressBar() {
		mProgressBar.bringToFront();
		mProgressBar.setVisibility(View.VISIBLE);
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
		mQueryYouTubeTask = (QueryYouTubeTask) new QueryYouTubeTask(context, this).execute(lYouTubeId);
	}

	public void stopYoutubeTask(Context context) {
		YouTubeUtility.markVideoAsViewed(context, mVideoId);

		if (mQueryYouTubeTask != null) {
			mQueryYouTubeTask.cancel(true);
		}

		if (mVideoView != null) {
			mVideoView.stopPlayback();
		}
	}
}