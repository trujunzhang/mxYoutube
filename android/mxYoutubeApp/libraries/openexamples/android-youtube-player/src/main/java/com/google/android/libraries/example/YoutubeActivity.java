package com.google.android.libraries.example;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.libraries.mediaframework.layeredvideo.SimpleVideoPlayer;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;

public class YoutubeActivity extends Activity implements FullscreenCallback {

	private FrameLayout videoPlayerContainer;

	/**
	 * This is the layout of the container before fullscreen mode has been entered. When we leave fullscreen mode, we
	 * restore the layout of the container to this layout.
	 */
	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private SimpleVideoPlayer videoPlayer;

	/**
	 * The callback that is triggered when fullscreen mode is entered or closed.
	 */
	private FullscreenCallback fullscreenCallback;

	/**
	 * Set the callback which will be called when the player enters and leaves fullscreen mode.
	 *
	 * @param fullscreenCallback
	 *            The callback should hide other views in the activity when the player enters fullscreen mode and show
	 *            other views when the player leaves fullscreen mode.
	 */
	public void setFullscreenCallback(final FullscreenCallback fullscreenCallback) {
		this.fullscreenCallback = new FullscreenCallback() {
			@Override
			public void onGoToFullscreen() {
				fullscreenCallback.onGoToFullscreen();
				videoPlayerContainer.setLayoutParams(Util.getLayoutParamsBasedOnParent(videoPlayerContainer,
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			}

			@Override
			public void onReturnFromFullscreen() {
				fullscreenCallback.onReturnFromFullscreen();
				videoPlayerContainer.setLayoutParams(originalContainerLayoutParams);
			}
		};

		videoPlayer.setFullscreenCallback(fullscreenCallback);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_youtube);

		videoPlayerContainer = (FrameLayout) this.findViewById(R.id.video_frame);

		Uri lVideoIdUri = Uri.parse("ytv://" + "AV2OkzIGykA");

		// Video video = new Video("https://www.youtube.com/v/izA_Xgbj7II", Video.VideoType.MP4);
		videoPlayer = new SimpleVideoPlayer(this, videoPlayerContainer, "wanghao", false);
		videoPlayer.play();

		this.originalContainerLayoutParams = videoPlayerContainer.getLayoutParams();

		this.setFullscreenCallback(this);
	}

	/**
	 * When the video player goes into fullscreen, hide the video list so that the video player can occupy the entire
	 * screen.
	 */
	@Override
	public void onGoToFullscreen() {
		// videoListView.setVisibility(View.INVISIBLE);
	}

	/**
	 * When the player returns from fullscreen, show the video list again.
	 */
	@Override
	public void onReturnFromFullscreen() {
		// videoListView.setVisibility(View.VISIBLE);
	}

}
