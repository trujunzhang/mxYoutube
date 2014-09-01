package com.keyes.youtube.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.keyes.youtube.beans.YoutubeTaskInfo;
import com.keyes.youtube.utils.YoutubeQuality;

public class OpenYouTubePlayerActivity extends Activity {
	private YouTubePlayerHelper playerHelper = new YouTubePlayerHelper();

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// create the layout of the view
//		this.setContentView(this.playerHelper.setupView(this));

		// determine the messages to be displayed as the view loads the video
		this.playerHelper.taskInfo = getExtractMessages();

		// set the flag to keep the screen ON so that the video can play without the screen being turned off
		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.playerHelper.initProgressBar();

		// extract the playlist or video id from the intent that started this video

		Uri lVideoIdUri = this.getIntent().getData();

		// if (lVideoIdUri == null) {
		// Log.i(this.getClass().getSimpleName(), "No video ID was specified in the intent.  Closing video activity.");
		// finish();
		// }

		// String lVideoIdStr = lVideoIdUri.getEncodedSchemeSpecificPart();
		// if (lVideoIdStr == null) {
		// Log.i(this.getClass().getSimpleName(), "No video ID was specified in the intent.  Closing video activity.");
		// finish();
		// }

		// if (lYouTubeId == null) {
		// Log.i(this.getClass().getSimpleName(),
		// "Unable to extract video ID from the intent.  Closing video activity.");
		// finish();
		// }

		this.playerHelper.makeAndExecuteYoutubeTask(this, lVideoIdUri);
	}

	/**
	 * Determine the messages to display during video load and initialization.
	 */
	protected YoutubeTaskInfo getExtractMessages() {
		YoutubeTaskInfo _taskInfo = new YoutubeTaskInfo();
		Intent lInvokingIntent = getIntent();
		String lMsgInit = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_INIT);
		if (lMsgInit != null) {
			_taskInfo.mMsgInit = lMsgInit;
		}
		String lMsgDetect = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_DETECT);
		if (lMsgDetect != null) {
			_taskInfo.mMsgDetect = lMsgDetect;
		}
		String lMsgPlaylist = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_PLAYLIST);
		if (lMsgPlaylist != null) {
			_taskInfo.mMsgPlaylist = lMsgPlaylist;
		}
		String lMsgToken = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_TOKEN);
		if (lMsgToken != null) {
			_taskInfo.mMsgToken = lMsgToken;
		}
		String lMsgLoBand = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_LO_BAND);
		if (lMsgLoBand != null) {
			_taskInfo.mMsgLowBand = lMsgLoBand;
		}
		String lMsgHiBand = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_HI_BAND);
		if (lMsgHiBand != null) {
			_taskInfo.mMsgHiBand = lMsgHiBand;
		}
		String lMsgErrTitle = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_ERROR_TITLE);
		if (lMsgErrTitle != null) {
			_taskInfo.mMsgErrorTitle = lMsgErrTitle;
		}
		String lMsgErrMsg = lInvokingIntent.getStringExtra(YoutubeTaskInfo.MSG_ERROR_MSG);
		if (lMsgErrMsg != null) {
			_taskInfo.mMsgError = lMsgErrMsg;
		}

		_taskInfo.lYouTubeFmtQuality = YoutubeQuality.getYoutubeFmtQuality(this);
		_taskInfo.showControllerOnStartup = false;

		return _taskInfo;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		this.playerHelper.stopYoutubeTask(this);

		// clear the flag that keeps the screen ON
		getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.playerHelper.destroyView();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}