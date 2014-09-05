package com.mxtube.app.ui.single;

import android.net.Uri;
import android.view.ViewGroup;
import com.google.android.libraries.mediaframework.layeredvideo.SimpleVideoPlayer;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.keyes.youtube.beans.YoutubeTaskInfo;
import com.keyes.youtube.utils.YoutubeQuality;
import com.mxtube.app.R;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.single_media_player)
public class MediaPlayerSingle extends Single implements FullscreenCallback {

	@ViewById(R.id.video_frame)
	android.widget.FrameLayout videoPlayerContainer;
	@ViewById(R.id.user_header)
	android.widget.ImageView userHeader;
	@ViewById(R.id.user_name)
	android.widget.TextView userName;

	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private SimpleVideoPlayer videoPlayer;

	@AfterInject
	void calledAfterInjection() {

	}

	@AfterViews
	protected void calledAfterViewInjection() {
		// determine the messages to be displayed as the view loads the video

		Uri lVideoIdUri = Uri.parse("ytv://" + "AV2OkzIGykA");
		// Uri lVideoIdUri = Uri.parse("ytv://" + this.selectedVideo.getId());

		this.videoPlayer = new SimpleVideoPlayer(this.getSherlockActivity(), this.videoPlayerContainer, this);
		this.videoPlayer.play(lVideoIdUri);

	}


	@Override
	public void initSingle() {

	}

	@Override
	public void onGoToFullscreen() {

	}

	@Override
	public void onReturnFromFullscreen() {

	}
}
