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

	protected void setupPlayerHelper(Context context, Uri lVideoIdUri) {
		this.playerHelper.setVideoInfoTaskCallback(this);

		// determine the messages to be displayed as the view loads the video
		this.playerHelper.taskInfo = getExtractMessages();

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

	// private ResumePlayBackControlLayer resumePlayBackControlLayer;

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
		this(activity, container, null);
	}

	/**
	 * @param activity
	 *            The activity that will contain the video player.
	 * @param container
	 *            The {@link android.widget.FrameLayout} which will contain the video player.
	 * @param fullscreenCallback
	 */
	public SimpleVideoPlayer(Activity activity, FrameLayout container, FullscreenCallback fullscreenCallback) {
		this.activity = activity;

		normalPlayBackControlLayer = new NormalPlayBackControlLayer(null);
		fullscreenPlayBackControlLayer = new FullscreenPlayBackControlLayer(null);
		// resumePlayBackControlLayer = new ResumePlayBackControlLayer(null);

		subtitleLayer = new SubtitleLayer();
		loadingLayer = new LoadingLayer();
		videoSurfaceViewLayer = new VideoSurfaceViewLayer();

		List<Layer> layers = new ArrayList<Layer>();

		layers.add(videoSurfaceViewLayer);
		layers.add(fullscreenPlayBackControlLayer);
		layers.add(normalPlayBackControlLayer);
		// layers.add(subtitleLayer);
		layers.add(loadingLayer);

		layerManager = new LayerManager(activity, fullscreenCallback, container, layers);
		layerManager.setControl(normalPlayBackControlLayer);
		layerManager.setVideoSurfaceViewLayer(videoSurfaceViewLayer);
	}

	/**
	 * Returns whether the player is currently in fullscreen mode.
	 */
	public boolean isFullscreen() {
		return this.layerManager.isFullscreen();
	}

	/**
	 * Make the player enter or leave fullscreen mode.
	 * 
	 * @param shouldBeFullscreen
	 *            If true, the player is put into fullscreen mode. If false, the player leaves fullscreen mode.
	 */
	public void setFullscreen(boolean shouldBeFullscreen) {
		this.layerManager.setFullscreen(shouldBeFullscreen);
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
	 * 
	 * @param lVideoIdUri
	 */
	public void play(Uri lVideoIdUri) {
		// Set the autoplay for the video surface layer in case the surface hasn't been created yet.
		// This way, when the surface is created, it will automatically start playing.
		setupPlayerHelper(this.activity, lVideoIdUri);
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
	 * When you are finished using this {@link SimpleVideoPlayer}, make sure to call this method.
	 */
	public void release() {
        videoSurfaceViewLayer.release();
		layerManager.release();
	}

}
