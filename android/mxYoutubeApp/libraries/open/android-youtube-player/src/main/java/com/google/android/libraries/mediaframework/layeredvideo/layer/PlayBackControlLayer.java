package com.google.android.libraries.mediaframework.layeredvideo.layer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.LayerManager;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;

public abstract class PlayBackControlLayer implements Layer {
	// 头部View
	private View mTopView;

	// 底部View
	private View mBottomView;

	private ImageView fullscreenButton;

	private FullscreenCallback fullscreenCallback;

	/**
	 * The view created by this
	 */
	private FrameLayout view;

	/**
	 * Returns the {@link LayerManager} which is responsible for displaying this layer's view.
	 */
	public LayerManager getLayerManager() {
		return layerManager;
	}

	/**
	 * This is the layout of the container before fullscreen mode has been entered. When we leave fullscreen mode, we
	 * restore the layout of the container to this layout.
	 */
	private ViewGroup.LayoutParams originalContainerLayoutParams;

	private boolean isFullscreen;

	/**
	 * Returns whether the player is currently in fullscreen mode.
	 */
	public boolean isFullscreen() {
		return isFullscreen;
	}

	/**
	 * Make the player enter or leave fullscreen mode.
	 *
	 * @param shouldBeFullscreen
	 *            If true, the player is put into fullscreen mode. If false, the player leaves fullscreen mode.
	 */
	public void setFullscreen(boolean shouldBeFullscreen) {
		if (shouldBeFullscreen != isFullscreen) {
			doToggleFullscreen();
		}
	}

	/**
	 * Fullscreen mode will rotate to landscape mode, hide the action bar, hide the navigation bar, hide the system
	 * tray, and make the video player take up the full size of the display. The developer who is using this function
	 * must ensure the following:
	 *
	 * <p>
	 * 1) Inside the android manifest, the activity that uses the video player has the attribute
	 * android:configChanges="orientation".
	 *
	 * <p>
	 * 2) Other views in the activity (or fragment) are hidden (or made visible) when this method is called.
	 */
	public void doToggleFullscreen() {

		// If there is no callback for handling fullscreen, don't do anything.
		if (fullscreenCallback == null) {
			return;
		}

		Activity activity = getLayerManager().getActivity();
		FrameLayout container = getLayerManager().getContainer();

		if (isFullscreen) {
			fullscreenCallback.onReturnFromFullscreen();
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			container.setLayoutParams(originalContainerLayoutParams);

			fullscreenButton.setImageResource(R.drawable.ic_action_full_screen);

			isFullscreen = false;
		} else {
			fullscreenCallback.onGoToFullscreen();

			container.setLayoutParams(Util.getLayoutParamsBasedOnParent(container, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));

			fullscreenButton.setImageResource(R.drawable.ic_action_return_from_full_screen);

			isFullscreen = true;
		}
	}

	private String videoTitle;

	/**
	 * The {@link com.google.android.libraries.mediaframework.layeredvideo.LayerManager} which is responsible for adding
	 * this layer to the container and displaying it on top of the video player.
	 */
	private LayerManager layerManager;

	protected Context mContext;

	public PlayBackControlLayer(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public PlayBackControlLayer(String videoTitle, FullscreenCallback fullscreenCallback) {
		this(videoTitle);
		this.fullscreenCallback = fullscreenCallback;
	}

	@Override
	public FrameLayout createView(LayerManager layerManager) {
		this.layerManager = layerManager;
		this.mContext = layerManager.getActivity();

		LayoutInflater inflater = layerManager.getActivity().getLayoutInflater();

		view = setupView(inflater);

		originalContainerLayoutParams = layerManager.getContainer().getLayoutParams();

		return view;
	}

	@Override
	public void onLayerDisplayed(LayerManager layerManager) {

	}

	protected abstract FrameLayout setupView(LayoutInflater inflater);

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void hideChrome() {
		mTopView.setVisibility(View.GONE);
		mBottomView.setVisibility(View.GONE);
	}

	public void hideTopChrome() {
		mTopView.setVisibility(View.GONE);
	}

	public void showTopChrome() {
		mTopView.setVisibility(View.VISIBLE);
	}

    public void setmTopView(View mTopView) {
        this.mTopView = mTopView;
    }

    public void setmBottomView(View mBottomView) {
        this.mBottomView = mBottomView;
    }
}
