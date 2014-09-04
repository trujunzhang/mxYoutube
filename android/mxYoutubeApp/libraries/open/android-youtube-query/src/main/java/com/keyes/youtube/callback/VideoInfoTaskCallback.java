package com.keyes.youtube.callback;

import android.net.Uri;

/**
 * In order to imbue the {@link com.google.android.libraries.mediaframework.layeredvideo.layer.PlaybackControlLayer}
 * with the ability make the player fullscreen, a {@link VideoInfoTaskCallback} must be assigned to it. The
 * {@link VideoInfoTaskCallback} implementation is responsible for hiding/showing the other views on the screen when
 * the player enters/leaves fullscreen mode.
 */
public interface VideoInfoTaskCallback {

	/**
	 * When triggered, the activity should show any views that were hidden when the player went to fullscreen.
	 */
	public void startYoutubeTask(String videoUrl);
}
