package com.google.android.libraries.example;

import android.app.Activity;
import android.net.Uri;
import android.widget.FrameLayout;
import com.keyes.youtube.beans.YoutubeTaskInfo;
import com.keyes.youtube.ui.YouTubePlayerHelper;
import com.keyes.youtube.utils.YoutubeQuality;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.media_player_activity)
public class MediaPlayerActivity extends Activity {
	@ViewById(R.id.videoView)
	android.widget.VideoView videoView;
	@ViewById(R.id.progressBar_message)
	android.widget.TextView progressBarMessage;
	@ViewById(R.id.media_player_top_linear_line)
	android.widget.LinearLayout mediaPlayerTopLinearLine;
	@ViewById(R.id.progressBar)
	android.widget.ProgressBar progressBar;
	@ViewById(R.id.media_player_bottom_linear_line)
    FrameLayout mediaPlayerBottomLinearLine;
	@ViewById(R.id.media_player_linear_layout)
	android.widget.FrameLayout mediaPlayerLinearLayout;

	// ................ include [*media_player_fragment.xml*] .......................

	private YouTubePlayerHelper playerHelper = new YouTubePlayerHelper();

	@AfterInject
	void calledAfterInjection() {
	}

	@AfterViews
	protected void calledAfterViewInjection() {
		this.playerHelper.setupView(this.videoView, this.progressBar, this.progressBarMessage);
		// this.playerHelper.setupView(this.getSherlockActivity(), mediaPlayerLinearLayout);

		// determine the messages to be displayed as the view loads the video
		this.playerHelper.taskInfo = getExtractMessages();

		this.playerHelper.initProgressBar();

		Uri lVideoIdUri = Uri.parse("ytv://" + "AV2OkzIGykA");
		// Uri lVideoIdUri = Uri.parse("ytv://" + this.selectedVideo.getId());

		this.playerHelper.makeAndExecuteYoutubeTask(this, lVideoIdUri);
	}

	/**
	 * Determine the messages to display during video load and initialization.
	 */
	protected YoutubeTaskInfo getExtractMessages() {
		YoutubeTaskInfo _taskInfo = new YoutubeTaskInfo();

		_taskInfo.lYouTubeFmtQuality = YoutubeQuality.getYoutubeFmtQuality(this);
		_taskInfo.showControllerOnStartup = false;

		return _taskInfo;
	}

}