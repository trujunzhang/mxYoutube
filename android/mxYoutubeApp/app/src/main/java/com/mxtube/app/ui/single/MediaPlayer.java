package com.mxtube.app.ui.single;

import android.net.Uri;
import com.keyes.youtube.beans.YoutubeTaskInfo;
import com.keyes.youtube.ui.YouTubePlayerHelper;
import com.keyes.youtube.utils.YoutubeQuality;
import com.mxtube.app.R;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.single_media_player)
public class MediaPlayer extends Single {
	@ViewById(R.id.videoView)
	android.widget.VideoView videoView;
	@ViewById(R.id.progressBar_message)
	android.widget.TextView progressBarMessage;
	@ViewById(R.id.media_player_top_linear_line)
	android.widget.LinearLayout mediaPlayerTopLinearLine;
	@ViewById(R.id.progressBar)
	android.widget.ProgressBar progressBar;
	@ViewById(R.id.media_player_bottom_linear_line)
	android.widget.LinearLayout mediaPlayerBottomLinearLine;
	@ViewById(R.id.media_player_linear_layout)
	android.widget.FrameLayout mediaPlayerLinearLayout;
	@ViewById(R.id.user_header)
	android.widget.ImageView userHeader;
	@ViewById(R.id.user_name)
	android.widget.TextView userName;

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
