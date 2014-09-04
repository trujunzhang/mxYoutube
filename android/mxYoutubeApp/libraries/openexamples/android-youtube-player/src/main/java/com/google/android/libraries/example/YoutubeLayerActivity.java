package com.google.android.libraries.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.libraries.mediaframework.layeredvideo.SimpleVideoPlayer;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;

public class YoutubeLayerActivity extends Activity implements FullscreenCallback {

	private FrameLayout videoPlayerContainer;
	private android.widget.Button button;
	private LinearLayout info_linear_layout;
	private LinearLayout right_linear_layout;

	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private SimpleVideoPlayer videoPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_youtube_layer);
		setupView();

		this.videoPlayer = new SimpleVideoPlayer(this, this.videoPlayerContainer, "wanghao", false, this);
		this.videoPlayer.play();
	}

	private void setupView() {
		this.videoPlayerContainer = (FrameLayout) this.findViewById(R.id.video_frame);
		this.info_linear_layout = (LinearLayout) this.findViewById(R.id.info_linear_layout);
		this.right_linear_layout = (LinearLayout) this.findViewById(R.id.right_linear_layout);
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
