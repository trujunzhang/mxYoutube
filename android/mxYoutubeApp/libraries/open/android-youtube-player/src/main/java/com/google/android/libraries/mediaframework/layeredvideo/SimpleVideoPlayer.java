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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.layer.Layer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.LoadingLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.SubtitleLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.VideoSurfaceViewLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.playback.FullscreenPlayBackControlLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.playback.NormalPlayBackControlLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.playback.PlayBackControlLayer;
import com.google.android.libraries.mediaframework.layeredvideo.layer.playback.ResumePlayBackControlLayer;
import com.keyes.youtube.beans.YoutubeTaskInfo;
import com.keyes.youtube.callback.VideoInfoTaskCallback;
import com.keyes.youtube.ui.YouTubePlayerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A video player which includes subtitle support and a customizable UI for playback control.
 *
 * <p>
 * NOTE: If you want to get a video player up and running with minimal effort, just instantiate this class and call
 * play();
 */
public class SimpleVideoPlayer implements VideoInfoTaskCallback {

	private YouTubePlayerHelper playerHelper = new YouTubePlayerHelper();

	protected void setupPlayerHelper(Context context) {
		this.playerHelper.setVideoInfoTaskCallback(this);

		// determine the messages to be displayed as the view loads the video
		this.playerHelper.taskInfo = getExtractMessages();

		Uri lVideoIdUri = Uri.parse("ytv://" + "AV2OkzIGykA");

		this.playerHelper.makeAndExecuteYoutubeTask(context, lVideoIdUri);
	}

	/**
	 * Determine the messages to display during video load and initialization.
	 */
	protected YoutubeTaskInfo getExtractMessages() {
		YoutubeTaskInfo _taskInfo = new YoutubeTaskInfo();

		_taskInfo.lYouTubeFmtQuality = "18";// YoutubeQuality.getYoutubeFmtQuality(this);
		_taskInfo.showControllerOnStartup = false;

		return _taskInfo;
	}

	private String videoUrl;

	@Override
	public void startYoutubeTask(String videoUrl) {
		this.videoUrl = videoUrl;
		mHandler.postDelayed(playVideo, 3000);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			default:
				break;
			}
		}
	};
	private Runnable playVideo = new Runnable() {

		@Override
		public void run() {
			loadingLayer.moveSurfaceToBackground();
			videoSurfaceViewLayer.playVideo(videoUrl);
		}
	};
	/**
	 * The {@link android.app.Activity} that contains this video player.
	 */
	private final Activity activity;

	/**
	 * The underlying {@link LayerManager} which is used to assemble the player.
	 */
	private final LayerManager layerManager;

	/**
	 * Displays loading message at bottom center of video player.
	 */
	private LoadingLayer loadingLayer;

	/**
	 * Displays subtitles at bottom center of video player.
	 */
	private SubtitleLayer subtitleLayer;

	/**
	 * Renders the video.
	 */
	private VideoSurfaceViewLayer videoSurfaceViewLayer;

	/**
	 * The customizable view for playback control. It handles pause/play, fullscreen, seeking, title, and action
	 * buttons.
	 */
	private PlayBackControlLayer playbackControlLayer;
	private NormalPlayBackControlLayer normalPlayBackControlLayer;
	private FullscreenPlayBackControlLayer fullscreenPlayBackControlLayer;
//	private ResumePlayBackControlLayer resumePlayBackControlLayer;

	/**
	 * @param activity
	 *            The activity that will contain the video player.
	 * @param container
	 *            The {@link android.widget.FrameLayout} which will contain the video player.
	 * @param videoTitle
	 *            The title of the video (displayed on the left of the top chrome).
	 * @param autoplay
	 *            Whether the video should start playing immediately.
	 */
	public SimpleVideoPlayer(Activity activity, FrameLayout container, String videoTitle, boolean autoplay) {
		this(activity, container, videoTitle, autoplay, null);
	}

	/**
	 * @param activity
	 *            The activity that will contain the video player.
	 * @param container
	 *            The {@link android.widget.FrameLayout} which will contain the video player.
	 * @param videoTitle
	 *            The title of the video (displayed on the left of the top chrome).
	 * @param autoplay
	 *            Whether the video should start playing immediately.
	 * @param fullscreenCallback
	 *            The callback which gets triggered when the player enters or leaves fullscreen mode.
	 */
	public SimpleVideoPlayer(Activity activity, FrameLayout container, String videoTitle, boolean autoplay,
			FullscreenCallback fullscreenCallback) {
		this.activity = activity;

		normalPlayBackControlLayer = new NormalPlayBackControlLayer(null);
		fullscreenPlayBackControlLayer = new FullscreenPlayBackControlLayer(null);
//		resumePlayBackControlLayer = new ResumePlayBackControlLayer(null);

		subtitleLayer = new SubtitleLayer();
		loadingLayer = new LoadingLayer(autoplay);
		videoSurfaceViewLayer = new VideoSurfaceViewLayer();

		List<Layer> layers = new ArrayList<Layer>();

		layers.add(videoSurfaceViewLayer);
		layers.add(fullscreenPlayBackControlLayer);
		layers.add(normalPlayBackControlLayer);
		layers.add(subtitleLayer);
		layers.add(loadingLayer);

		layerManager = new LayerManager(activity,fullscreenCallback, container, layers);
		layerManager.setControl(normalPlayBackControlLayer);
		layerManager.setVideoSurfaceViewLayer(videoSurfaceViewLayer);
	}

	/**
	 * Creates a button to put in the top right of the video player.
	 *
	 * @param icon
	 *            The image of the action (ex. trash can).
	 * @param contentDescription
	 *            The text description this action. This is used in case the action buttons do not fit in the video
	 *            player. If so, an overflow button will appear and, when clicked, it will display a list of the content
	 *            descriptions for each action.
	 * @param onClickListener
	 *            The handler for when the action is triggered.
	 */
	public void addActionButton(Drawable icon, String contentDescription, View.OnClickListener onClickListener) {
		// playbackControlLayer.addActionButton(activity, icon, contentDescription, onClickListener);
	}

	/**
	 * Hides the seek bar thumb and prevents the user from seeking to different time points in the video.
	 */
	public void disableSeeking() {
		// playbackControlLayer.disableSeeking();
	}

	/**
	 * Makes the seek bar thumb visible and allows the user to seek to different time points in the video.
	 */
	public void enableSeeking() {
		// playbackControlLayer.enableSeeking();
	}

	/**
	 * Fades the playback control layer out and then removes it from the {@link LayerManager}'s container.
	 */
	public void hide() {
		// playbackControlLayer.hide();
		subtitleLayer.setVisibility(View.GONE);
	}

	/**
	 * Hides the top chrome (which displays the logo, title, and action buttons).
	 */
	public void hideTopChrome() {
		this.playbackControlLayer.hideTopChrome();
	}

	/**
	 * Returns whether the player is currently in fullscreen mode.
	 */
	public boolean isFullscreen() {
		// return playbackControlLayer.isFullscreen();
		return false;
	}

	/**
	 * Make the player enter or leave fullscreen mode.
	 * 
	 * @param shouldBeFullscreen
	 *            If true, the player is put into fullscreen mode. If false, the player leaves fullscreen mode.
	 */
	public void setFullscreen(boolean shouldBeFullscreen) {
		// playbackControlLayer.setFullscreen(shouldBeFullscreen);
	}

	/**
	 * When mutliple surface layers are used (ex. in the case of ad playback), one layer must be overlaid on top of
	 * another. This method sends this player's surface layer to the background so that other surface layers can be
	 * overlaid on top of it.
	 */
	public void moveSurfaceToBackground() {
		loadingLayer.moveSurfaceToBackground();
	}

	/**
	 * When mutliple surface layers are used (ex. in the case of ad playback), one layer must be overlaid on top of
	 * another. This method sends this player's surface layer to the foreground so that it is overlaid on top of all
	 * layers which are in the background.
	 */
	public void moveSurfaceToForeground() {
		loadingLayer.moveSurfaceToForeground();
	}

	/**
	 * Pause video playback.
	 */
	public void pause() {
		// Set the autoplay for the video surface layer in case the surface hasn't been created yet.
		// This way, when the surface is created, it won't start playing.
		// videoSurfaceLayer.setAutoplay(false);

		// layerManager.getControl().pause();
	}

	/**
	 * Resume video playback.
	 */
	public void play() {
		// Set the autoplay for the video surface layer in case the surface hasn't been created yet.
		// This way, when the surface is created, it will automatically start playing.
		this.loadingLayer.moveSurfaceToForeground();
		setupPlayerHelper(this.activity);
	}

	/**
	 * Sets the color of the top chrome, bottom chrome, and background.
	 * 
	 * @param color
	 *            a color derived from the @{link Color} class (ex. {@link android.graphics.Color#RED}).
	 */
	public void setChromeColor(int color) {
		// playbackControlLayer.setChromeColor(color);
	}

	/**
	 * Set the callback which will be called when the player enters and leaves fullscreen mode.
	 * 
	 * @param fullscreenCallback
	 *            The callback should hide other views in the activity when the player enters fullscreen mode and show
	 *            other views when the player leaves fullscreen mode.
	 */
	public void setFullscreenCallback(FullscreenCallback fullscreenCallback) {
		// playbackControlLayer.setFullscreenCallback(fullscreenCallback);
	}

	/**
	 * Set the logo with appears in the left of the top chrome.
	 * 
	 * @param logo
	 *            The drawable which will be the logo.
	 */
	public void setLogoImage(Drawable logo) {
		// playbackControlLayer.setLogoImageView(logo);
	}

	/**
	 * Sets the color of the buttons and seek bar.
	 * 
	 * @param color
	 *            a color derived from the @{link Color} class (ex. {@link android.graphics.Color#RED}).
	 */
	public void setPlaybackControlColor(int color) {
		// playbackControlLayer.setControlColor(color);
	}

	/**
	 * Sets the color of the seekbar.
	 * 
	 * @param color
	 *            a color derived from the @{link Color} class (ex. {@link android.graphics.Color#RED}).
	 */
	public void setSeekbarColor(int color) {
		// playbackControlLayer.setSeekbarColor(color);
	}

	/**
	 * Sets the color of the text views
	 * 
	 * @param color
	 *            a color derived from the @{link Color} class (ex. {@link android.graphics.Color#RED}).
	 */
	public void setTextColor(int color) {
		// playbackControlLayer.setTextColor(color);
	}

	/**
	 * Returns whether the player should be playing (based on whether the user has tapped pause or play). This can be
	 * used by other classes to look at the playback control layer's play/pause state and force the player to play or
	 * pause accordingly.
	 */
	public boolean shouldBePlaying() {
		// return playbackControlLayer.shouldBePlaying();
		return false;
	}

	/**
	 * Add the playback control layer back to the container. It will disappear when the user taps the screen.
	 */
	public void show() {
		// playbackControlLayer.show();
		subtitleLayer.setVisibility(View.VISIBLE);
	}

	/**
	 * Shows the top chrome (which displays the logo, title, and action buttons).
	 */
	public void showTopChrome() {
		// playbackControlLayer.showTopChrome();
	}

	/**
	 * When you are finished using this {@link SimpleVideoPlayer}, make sure to call this method.
	 */
	public void release() {
		// videoSurfaceLayer.release();
		layerManager.release();
	}

}
