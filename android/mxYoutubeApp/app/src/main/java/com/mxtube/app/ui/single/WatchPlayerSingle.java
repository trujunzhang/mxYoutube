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
import com.mxtube.app.ui.single.watch.right.tabs.WatchRightTabsPanel;
import com.mxtube.app.ui.single.watch.right.tabs.WatchRightTabsPanel_;
import com.mxtube.app.ui.single.watch.views.WatchDescriptionCard;
import com.mxtube.app.ui.single.watch.views.WatchDescriptionCard_;
import com.mxtube.app.ui.single.watch.views.WatchPlayerSubscription;
import com.mxtube.app.ui.single.watch.views.WatchPlayerSubscription_;
import com.youtube.widget.YoutubeLayoutUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.single_watch_player)
public class WatchPlayerSingle extends Single implements FullscreenCallback {

	@ViewById(R.id.video_frame)
	public android.widget.FrameLayout videoFrame;
	@ViewById(R.id.watch_left_details_linear)
	public android.widget.LinearLayout watchLeftDetailsLinear;
	@ViewById(R.id.watch_right_linear)
	android.widget.LinearLayout watchRightLinear;

	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private SimpleVideoPlayer videoPlayer;

	private WatchPlayerSubscription watchPlayerSubscription;
	private WatchDescriptionCard watchDescriptionCard;

	private WatchRightTabsPanel watchRightTabsPanel;

	@AfterInject
	public void calledAfterInjection() {

	}

	@AfterViews
	public void calledAfterViewInjection() {
		setLeftPanel();

		setRightPanel();
	}

	private void setLeftPanel() {
		int screenWidth = Tools.getScreenWidth(this.getContext());

		// width: 854px; height: 480px; left: 0px; top: 0px; transform: none;
		int videoViewHeight = YoutubeLayoutUtils.getVideoViewHeight(screenWidth / 2, 854, 480);
		videoFrame.setLayoutParams(Util.getLayoutParamsBasedOnParent(videoFrame, ViewGroup.LayoutParams.MATCH_PARENT,
				videoViewHeight));

		this.watchPlayerSubscription = WatchPlayerSubscription_.build(this.getContext());
		this.watchPlayerSubscription.bind(this.getContext(), this.selectedVideo);

		this.watchDescriptionCard = WatchDescriptionCard_.build(this.getContext());
		this.watchDescriptionCard.bind(this.getContext(), this.selectedVideo);

		watchLeftDetailsLinear.addView(this.watchPlayerSubscription);
		watchLeftDetailsLinear.addView(this.watchDescriptionCard);

		// determine the messages to be displayed as the view loads the video

		// Uri lVideoIdUri = Uri.parse("ytv://" + "AV2OkzIGykA");
		Uri lVideoIdUri = Uri.parse("ytv://" + this.selectedVideo.getId());

		this.videoPlayer = new SimpleVideoPlayer(this.getSherlockActivity(), this.videoFrame, this);
		this.videoPlayer.play(lVideoIdUri);
	}

	private void setRightPanel() {
		this.watchRightTabsPanel = WatchRightTabsPanel_.build(this.getContext());
		this.watchRightTabsPanel.bind(this.getContext(), this.getChildFragmentManager());
		watchRightLinear.addView(this.watchRightTabsPanel);
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
