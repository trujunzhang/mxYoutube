package com.mxtube.app.ui.single;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.keyes.youtube.beans.YoutubeTaskInfo;
import com.keyes.youtube.ui.YouTubePlayerHelper;
import com.keyes.youtube.utils.YoutubeQuality;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public class MediaPlayer extends Single {
	private YouTubePlayerHelper playerHelper = new YouTubePlayerHelper();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return this.playerHelper.setupView(this.getSherlockActivity());
	}

	@AfterViews
	protected void calledAfterViewInjection() {

		// determine the messages to be displayed as the view loads the video
		this.playerHelper.taskInfo = getExtractMessages();

		this.playerHelper.initProgressBar();

		Uri lVideoIdUri = Uri.parse("ytv://" + "zcPfh0ePokM");

		this.playerHelper.makeAndExecuteYoutubeTask(this.getSherlockActivity(), lVideoIdUri);
	}

	/**
	 * Determine the messages to display during video load and initialization.
	 */
	protected YoutubeTaskInfo getExtractMessages() {
		YoutubeTaskInfo _taskInfo = new YoutubeTaskInfo();

		_taskInfo.lYouTubeFmtQuality = YoutubeQuality.getYoutubeFmtQuality(this.getSherlockActivity());
		_taskInfo.showControllerOnStartup = false;

		return _taskInfo;
	}

	@Override
	public void initSingle() {

	}
}
