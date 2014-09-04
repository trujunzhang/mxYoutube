package com.google.android.libraries.mediaframework.layeredvideo.callback;

/**
 * In order to imbue the with the ability make the player fullscreen, a {@link FullscreenCallback} must be assigned to
 * it. The {@link FullscreenCallback} implementation is responsible for hiding/showing the other views on the screen
 * when the player enters/leaves fullscreen mode.
 */
public interface FullscreenCallback {

	/**
	 * When triggered, the activity should hide any additional views.
	 */
	public void onGoToFullscreen();

	/**
	 * When triggered, the activity should show any views that were hidden when the player went to fullscreen.
	 */
	public void onReturnFromFullscreen();
}
