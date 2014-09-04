package com.google.android.libraries.example;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.libraries.mediaframework.layeredvideo.SimpleVideoPlayer;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_youtube_layer)
public class YoutubeLayerActivity extends Activity implements FullscreenCallback {

	@ViewById(R.id.video_frame)
	FrameLayout videoPlayerContainer;
	@ViewById(R.id.button)
	android.widget.Button button;
	@ViewById(R.id.info_linear_layout)
	LinearLayout info_linear_layout;
	@ViewById(R.id.right_linear_layout)
	LinearLayout right_linear_layout;

	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private SimpleVideoPlayer videoPlayer;

	@AfterViews
	protected void calledAfterViewInjection() {
		setupView();

		onGoToFullscreen();
		this.videoPlayer = new SimpleVideoPlayer(this, this.videoPlayerContainer, "wanghao", false, this);
		this.videoPlayer.play();
	}

	private void setupView() {
		this.originalContainerLayoutParams = this.right_linear_layout.getLayoutParams();
	}

	@Override
	public void onGoToFullscreen() {
		this.info_linear_layout.setVisibility(View.INVISIBLE);
		this.right_linear_layout.setVisibility(View.INVISIBLE);
		this.right_linear_layout.setLayoutParams(Util.getLayoutParamsBasedOnParent(this.right_linear_layout, 0, 0));
	}

	@Override
	public void onReturnFromFullscreen() {
		this.info_linear_layout.setVisibility(View.VISIBLE);
		this.right_linear_layout.setVisibility(View.VISIBLE);
		this.right_linear_layout.setLayoutParams(originalContainerLayoutParams);
	}
}
