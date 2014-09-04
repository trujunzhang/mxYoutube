package com.google.android.libraries.mediaframework.layeredvideo.layer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.LayerManager;
import com.google.android.libraries.mediaframework.layeredvideo.utils.DensityUtil;
import com.google.android.libraries.mediaframework.layeredvideo.widgets.FullScreenVideoView;

public class VideoSurfaceViewLayer implements Layer {

	// 自定义VideoView
	private FullScreenVideoView mVideo;

	/**
	 * The {@link com.google.android.libraries.mediaframework.layeredvideo.LayerManager} which is responsible for adding
	 * this layer to the container and displaying it on top of the video player.
	 */
	private LayerManager layerManager;

	protected Context mContext;

	/**
	 * The view that is created by this layer (it contains SubtitleLayer#subtitles).
	 */
	private FrameLayout view;
	private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
		@Override
		public void onPrepared(MediaPlayer mp) {
			mVideo.setVideoWidth(mp.getVideoWidth());
			mVideo.setVideoHeight(mp.getVideoHeight());

			mVideo.start();
			int playTime = 0;
			if (playTime != 0) {
				mVideo.seekTo(playTime);
			}

			layerManager.getControl().preparedController();
		}
	};

	private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			layerManager.getControl().completionWithController();
		}
	};

	@Override
	public FrameLayout createView(LayerManager layerManager) {
		this.layerManager = layerManager;
		this.mContext = layerManager.getActivity();

		LayoutInflater inflater = layerManager.getActivity().getLayoutInflater();

		view = (FrameLayout) inflater.inflate(R.layout.video_surface_view_layer, null);
		mVideo = (FullScreenVideoView) view.findViewById(R.id.videoview);
		threshold = DensityUtil.dip2px(mContext, 18);

		return view;
	}

	@Override
	public void onLayerDisplayed(LayerManager layerManager) {

	}

	public void playVideo(String videoUrl, final int playTime) {
		mVideo.setVideoURI(Uri.parse(videoUrl));
		mVideo.requestFocus();

		mVideo.setOnPreparedListener(mPreparedListener);
		mVideo.setOnCompletionListener(mCompletionListener);
		mVideo.setOnTouchListener(mTouchListener);
	}

	private float mLastMotionX;
	private float mLastMotionY;
	private int startX;
	private int startY;
	private int threshold;
	private boolean isClick = true;

	private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final float x = event.getX();
			final float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = x;
				mLastMotionY = y;
				startX = (int) x;
				startY = (int) y;
				break;
			case MotionEvent.ACTION_MOVE:
				float deltaX = x - mLastMotionX;
				float deltaY = y - mLastMotionY;
				float absDeltaX = Math.abs(deltaX);
				float absDeltaY = Math.abs(deltaY);

				if (deltaX > 0) {
					layerManager.getControl().forward(absDeltaX);
				} else if (deltaX < 0) {
					layerManager.getControl().backward(absDeltaX);
				}
				mLastMotionX = x;
				mLastMotionY = y;
				break;
			case MotionEvent.ACTION_UP:
				if (Math.abs(x - startX) > threshold || Math.abs(y - startY) > threshold) {
					isClick = false;
				}
				mLastMotionX = 0;
				mLastMotionY = 0;
				startX = (int) 0;
				if (isClick) {
					layerManager.getControl().showOrHide();
				}
				isClick = true;
				break;

			default:
				break;
			}
			return true;
		}

	};

}
