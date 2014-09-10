package com.mxtube.app.ui.single;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.common.utils.Tools;
import com.google.android.libraries.mediaframework.layeredvideo.SimpleVideoPlayer;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;
import com.mxtube.app.R;
import com.mxtube.app.ui.single.views.watch.WatchDescriptionCard;
import com.mxtube.app.ui.single.views.watch.WatchDescriptionCard_;
import com.mxtube.app.ui.single.views.watch.WatchPlayerSubscription;
import com.mxtube.app.ui.single.views.watch.WatchPlayerSubscription_;
import com.youtube.widget.YoutubeLayoutUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.single_watch_player)
public class WatchPlayerSingle extends Single implements FullscreenCallback {

	@ViewById(R.id.video_frame)
	android.widget.FrameLayout videoFrame;
	@ViewById(R.id.watch_linear)
	android.widget.LinearLayout watchLinear;

	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private SimpleVideoPlayer videoPlayer;

	private WatchPlayerSubscription watchPlayerSubscription;
	private WatchDescriptionCard watchDescriptionCard;

	@AfterInject
	void calledAfterInjection() {

	}

	@AfterViews
	protected void calledAfterViewInjection() {
		int screenWidth = Tools.getScreenWidth(this.getContext());

		// width: 854px; height: 480px; left: 0px; top: 0px; transform: none;
		int videoViewHeight = YoutubeLayoutUtils.getVideoViewHeight(screenWidth / 2, 854, 480);
		videoFrame.setLayoutParams(Util.getLayoutParamsBasedOnParent(videoFrame, ViewGroup.LayoutParams.MATCH_PARENT,
				videoViewHeight));

		this.watchPlayerSubscription = WatchPlayerSubscription_.build(this.getContext());
		this.watchPlayerSubscription.bind(this.getContext(), this.selectedVideo);

		this.watchDescriptionCard = WatchDescriptionCard_.build(this.getContext());
		this.watchDescriptionCard.bind(this.getContext(), this.selectedVideo);

		watchLinear.addView(this.watchPlayerSubscription);
		watchLinear.addView(this.watchDescriptionCard);

		// determine the messages to be displayed as the view loads the video

		// Uri lVideoIdUri = Uri.parse("ytv://" + "AV2OkzIGykA");
		Uri lVideoIdUri = Uri.parse("ytv://" + this.selectedVideo.getId());

		this.videoPlayer = new SimpleVideoPlayer(this.getSherlockActivity(), this.videoFrame, this);
		this.videoPlayer.play(lVideoIdUri);
	}

	@Override
	public void initSingle() {
		this.setTitle(this.selectedVideo.getSnippet().getTitle());
	}

	@Override
	public void onGoToFullscreen() {

	}

	@Override
	public void onReturnFromFullscreen() {

	}

	@Override
	public void onStop() {
		super.onStop();

		this.videoPlayer.release();
	}

	@Override
	public void saveInstanceState() {

	}

	@Override
	public void abstract001() {

	}

	@Override
	public void abstract002() {

	}

	@Override
	public void abstract003() {

	}
}
