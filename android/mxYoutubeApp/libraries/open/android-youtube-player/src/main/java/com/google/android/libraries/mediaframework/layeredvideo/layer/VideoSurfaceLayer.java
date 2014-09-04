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

package com.google.android.libraries.mediaframework.layeredvideo.layer;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.LayerManager;

/**
 * Creates a view which can render video.
 */
public class VideoSurfaceLayer implements Layer {

	/**
	 * Whether the video starts playing as soon as the surface is created.
	 */
	private boolean autoplay;

	/**
	 * The {@link com.google.android.libraries.mediaframework.layeredvideo.LayerManager} whcih is responsible for
	 * creating this layer's view and adding it to the video player.
	 */
	private LayerManager layerManager;

	/**
	 * This is created by the
	 * {@link VideoSurfaceLayer#createView(com.google.android.libraries.mediaframework.layeredvideo.LayerManager)}
	 * function.
	 */
	private FrameLayout view;
	private LinearLayout frameLayout;
	private ProgressBar progressBar;

	/**
	 * @param autoplay
	 *            Whether the video should start playing as soon as the surface view is created.
	 */
	public VideoSurfaceLayer(boolean autoplay) {
		this.autoplay = autoplay;
	}

	@Override
	public FrameLayout createView(LayerManager layerManager) {
		this.layerManager = layerManager;

		LayoutInflater inflater = layerManager.getActivity().getLayoutInflater();
		view = (FrameLayout) inflater.inflate(R.layout.video_loading_layer, null);

		this.frameLayout = (LinearLayout) view.findViewById(R.id.bottom_layout);
		this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		return view;
	}

	@Override
	public void onLayerDisplayed(LayerManager layerManager) {

	}

	/**
	 * When mutliple surface layers are used (ex. in the case of ad playback), one layer must be overlaid on top of
	 * another. This method sends this player's surface layer to the background so that other surface layers can be
	 * overlaid on top of it.
	 */
	public void moveSurfaceToBackground() {
		this.frameLayout.setVisibility(View.GONE);
		// surfaceView.setZOrderMediaOverlay(false);
	}

	/**
	 * When mutliple surface layers are used (ex. in the case of ad playback), one layer must be overlaid on top of
	 * another. This method sends this player's surface layer to the foreground so that it is overlaid on top of all
	 * layers which are in the background.
	 */
	public void moveSurfaceToForeground() {
		this.frameLayout.setVisibility(View.VISIBLE);
		// surfaceView.setZOrderMediaOverlay(true);
	}

	/**
	 * Sets whether the video should start playing as soon as the surface is created.
	 * 
	 * @param autoplay
	 *            If true, the video starts playing as soon as the surface is created. If false, then the video must be
	 *            started programmatically.
	 */
	public void setAutoplay(boolean autoplay) {
		this.autoplay = autoplay;
	}

	/**
	 * When you are finished using this object, call this method.
	 */
	public void release() {
		// layerManager.getExoplayerWrapper().removeListener(playbackListener);
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}
}
