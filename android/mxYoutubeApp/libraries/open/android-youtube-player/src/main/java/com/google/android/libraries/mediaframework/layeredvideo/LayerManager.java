/**
 Copyright 2014 Google Inc. All rights reserved.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.google.android.libraries.mediaframework.layeredvideo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.layer.Layer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.VideoSurfaceViewLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.playback.PlayBackControlLayer;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;

import java.util.List;

/**
 * This is the basis for building a layered video player (i.e. a video player with views overlaid on top of it).
 *
 * <p>
 * Given a {@link android.widget.FrameLayout}, a , and a set of
 * {@link com.google.android.libraries.mediaframework.layeredvideo.layer.Layer} objects, the {@link LayerManager} will
 * create an for the object and create each {@link com.google.android.libraries.mediaframework.layeredvideo.layer.Layer}
 * object's view and overlay it on the {@link android.widget.FrameLayout} object.
 *
 * <p>
 * Look at {@link SimpleVideoPlayer} to see {@link LayerManager} in action.
 */
public class LayerManager {

	private final FullscreenCallback fullscreenCallback;

	/**
	 * The activity that will display the video.
	 */
	private Activity activity;

	/**
	 * All the views created by the {@link com.google.android.libraries.mediaframework.layeredvideo.layer.Layer} objects
	 * will be overlaid on this container.
	 */
	private FrameLayout container;

	private PlayBackControlLayer control;

	private VideoSurfaceViewLayer videoSurfaceViewLayer;

	/**
	 * Given a container, create the video layers and add them to the container.
	 * 
	 * @param activity
	 *            The activity which will display the video player.
	 * @param fullscreenCallback
	 * @param container
	 *            The frame layout which will contain the views.
	 * @param layers
	 */
	public LayerManager(Activity activity, FullscreenCallback fullscreenCallback, FrameLayout container,
			List<Layer> layers) {
		this.activity = activity;
		this.container = container;
		container.setBackgroundColor(Color.BLACK);

		this.fullscreenCallback = fullscreenCallback;

		// Put the layers into the container.
		container.removeAllViews();
		for (Layer layer : layers) {
			container.addView(layer.createView(this));
			layer.onLayerDisplayed(this);
		}

//		container.setLayoutParams(Util.getLayoutParamsBasedOnParent(container, ViewGroup.LayoutParams.MATCH_PARENT, 400));

		originalContainerLayoutParams = this.getContainer().getLayoutParams();
	}

	/**
	 * Returns the activity which displays the video player created by this {@link LayerManager}.
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Returns the {@link android.widget.FrameLayout} which contains the views of the {@link Layer}s that this
	 * {@link LayerManager} manages.
	 */
	public FrameLayout getContainer() {
		return container;
	}

	/**
	 * Returns the which can be used to control the video playback (ex. pause, play, seek).
	 */
	public PlayBackControlLayer getControl() {
		return control;
	}

	public void setControl(PlayBackControlLayer videoView) {
		this.control = videoView;
	}

	public VideoSurfaceViewLayer getVideoSurfaceViewLayer() {
		return videoSurfaceViewLayer;
	}

	public void setVideoSurfaceViewLayer(VideoSurfaceViewLayer videoSurfaceViewLayer) {
		this.videoSurfaceViewLayer = videoSurfaceViewLayer;
	}

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
	 * This is the layout of the container before fullscreen mode has been entered. When we leave fullscreen mode, we
	 * restore the layout of the container to this layout.
	 */
	private ViewGroup.LayoutParams originalContainerLayoutParams;

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

		Activity activity = this.getActivity();
		FrameLayout container = this.getContainer();

		if (isFullscreen) {
			fullscreenCallback.onReturnFromFullscreen();
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			container.setLayoutParams(originalContainerLayoutParams);

			// fullscreenButton.setImageResource(R.drawable.ic_action_full_screen);

			isFullscreen = false;
		} else {
			fullscreenCallback.onGoToFullscreen();

			container.setLayoutParams(Util.getLayoutParamsBasedOnParent(container, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));

			// fullscreenButton.setImageResource(R.drawable.ic_action_return_from_full_screen);

			isFullscreen = true;
		}
	}

	/**
	 * When the video player is no longer needed, call this method.
	 */
	public void release() {
		container.removeAllViews();
	}

}
