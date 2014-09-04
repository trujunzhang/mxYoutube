package com.google.android.libraries.mediaframework.layeredvideo.layer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.LayerManager;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.utils.Util;
import com.google.android.libraries.mediaframework.layeredvideo.utils.DensityUtil;
import com.google.android.libraries.mediaframework.layeredvideo.widgets.FullScreenVideoView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class YoutubeControlLayer implements Layer {

	private FullscreenCallback fullscreenCallback;

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

	private Context mContext;

	/**
	 * The view created by this
	 */
	private FrameLayout view;

	public YoutubeControlLayer(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public YoutubeControlLayer(String videoTitle, FullscreenCallback fullscreenCallback) {
		this(videoTitle);
		this.fullscreenCallback = fullscreenCallback;
	}

	@Override
	public FrameLayout createView(LayerManager layerManager) {
		this.layerManager = layerManager;
		this.mContext = layerManager.getActivity();

		LayoutInflater inflater = layerManager.getActivity().getLayoutInflater();

		view = (FrameLayout) inflater.inflate(R.layout.normal_control_layer, null);
		setupView();

		originalContainerLayoutParams = layerManager.getContainer().getLayoutParams();

		return view;
	}

	@Override
	public void onLayerDisplayed(LayerManager layerManager) {

	}

	// 自定义VideoView
	private FullScreenVideoView mVideo;

	// 头部View
	private View mTopView;

	// 底部View
	private View mBottomView;
	// 视频播放拖动条
	private SeekBar mSeekBar;
	private ImageView mPlay;
	private TextView mPlayTime;
	private TextView mDurationTime;
	private ImageView fullscreenButton;

	// 屏幕宽高
	private float width;
	private float height;

	// 视频播放时间
	private int playTime;

	// 自动隐藏顶部和底部View的时间
	private static final int HIDE_TIME = 5000;

	// 原始屏幕亮度
	private int orginalLight;

	protected void setupView() {
		mVideo = (FullScreenVideoView) view.findViewById(R.id.videoview);
		mPlayTime = (TextView) view.findViewById(R.id.play_time);
		mDurationTime = (TextView) view.findViewById(R.id.total_time);
		mPlay = (ImageView) view.findViewById(R.id.play_btn);
		fullscreenButton = (ImageView) view.findViewById(R.id.fullscreen);
		mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
		mTopView = view.findViewById(R.id.top_layout);
		mBottomView = view.findViewById(R.id.bottom_layout);

		width = DensityUtil.getWidthInPx(mContext);
		height = DensityUtil.getHeightInPx(mContext);
		threshold = DensityUtil.dip2px(mContext, 18);

		mPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mVideo.isPlaying()) {
					mVideo.pause();
					mPlay.setImageResource(R.drawable.video_btn_down);
				} else {
					mVideo.start();
					mPlay.setImageResource(R.drawable.video_btn_on);
				}
			}
		});
		// Go into fullscreen when the fullscreen button is clicked.
		fullscreenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doToggleFullscreen();
				// show(DEFAULT_TIMEOUT_MS);
				// updateActionButtons();
				// updateColors();
			}
		});
		mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
	}

	private OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			mHandler.postDelayed(hideRunnable, HIDE_TIME);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			mHandler.removeCallbacks(hideRunnable);
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (fromUser) {
				int time = progress * mVideo.getDuration() / 100;
				mVideo.seekTo(time);
			}
		}
	};

	private void backward(float delataX) {
		int current = mVideo.getCurrentPosition();
		int backwardTime = (int) (delataX / width * mVideo.getDuration());
		int currentTime = current - backwardTime;
		mVideo.seekTo(currentTime);
		mSeekBar.setProgress(currentTime * 100 / mVideo.getDuration());
		mPlayTime.setText(formatTime(currentTime));
	}

	private void forward(float delataX) {
		int current = mVideo.getCurrentPosition();
		int forwardTime = (int) (delataX / width * mVideo.getDuration());
		int currentTime = current + forwardTime;
		mVideo.seekTo(currentTime);
		mSeekBar.setProgress(currentTime * 100 / mVideo.getDuration());
		mPlayTime.setText(formatTime(currentTime));
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (mVideo.getCurrentPosition() > 0) {
					mPlayTime.setText(formatTime(mVideo.getCurrentPosition()));
					int progress = mVideo.getCurrentPosition() * 100 / mVideo.getDuration();
					mSeekBar.setProgress(progress);
					if (mVideo.getCurrentPosition() > mVideo.getDuration() - 100) {
						mPlayTime.setText("00:00");
						mSeekBar.setProgress(0);
					}
					mSeekBar.setSecondaryProgress(mVideo.getBufferPercentage());
				} else {
					mPlayTime.setText("00:00");
					mSeekBar.setProgress(0);
				}

				break;
			case 2:
				showOrHide();
				break;

			default:
				break;
			}
		}
	};

	public void playVideo(String videoUrl) {
		mVideo.setVideoURI(Uri.parse(videoUrl));
		mVideo.requestFocus();
		mVideo.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideo.setVideoWidth(mp.getVideoWidth());
				mVideo.setVideoHeight(mp.getVideoHeight());

				mVideo.start();
				if (playTime != 0) {
					mVideo.seekTo(playTime);
				}

				mHandler.removeCallbacks(hideRunnable);
				mHandler.postDelayed(hideRunnable, HIDE_TIME);
				mDurationTime.setText(formatTime(mVideo.getDuration()));
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						mHandler.sendEmptyMessage(1);
					}
				}, 0, 1000);
			}
		});
		mVideo.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mPlay.setImageResource(R.drawable.video_btn_down);
				mPlayTime.setText("00:00");
				mSeekBar.setProgress(0);
			}
		});
		mVideo.setOnTouchListener(mTouchListener);
	}

	private Runnable hideRunnable = new Runnable() {

		@Override
		public void run() {
			showOrHide();
		}
	};

	@SuppressLint("SimpleDateFormat")
	private String formatTime(long time) {
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		return formatter.format(new Date(time));
	}

	private float mLastMotionX;
	private float mLastMotionY;
	private int startX;
	private int startY;
	private int threshold;
	private boolean isClick = true;

	private OnTouchListener mTouchListener = new OnTouchListener() {

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
					forward(absDeltaX);
				} else if (deltaX < 0) {
					backward(absDeltaX);
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
					showOrHide();
				}
				isClick = true;
				break;

			default:
				break;
			}
			return true;
		}

	};

	private void showOrHide() {
		if (mTopView.getVisibility() == View.VISIBLE) {
			mTopView.clearAnimation();
			Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.option_leave_from_top);
			animation.setAnimationListener(new AnimationImp() {
				@Override
				public void onAnimationEnd(Animation animation) {
					super.onAnimationEnd(animation);
					mTopView.setVisibility(View.GONE);
				}
			});
			mTopView.startAnimation(animation);

			mBottomView.clearAnimation();
			Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.option_leave_from_bottom);
			animation1.setAnimationListener(new AnimationImp() {
				@Override
				public void onAnimationEnd(Animation animation) {
					super.onAnimationEnd(animation);
					mBottomView.setVisibility(View.GONE);
				}
			});
			mBottomView.startAnimation(animation1);
		} else {
			mTopView.setVisibility(View.VISIBLE);
			mTopView.clearAnimation();
			Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.option_entry_from_top);
			mTopView.startAnimation(animation);

			mBottomView.setVisibility(View.VISIBLE);
			mBottomView.clearAnimation();
			Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.option_entry_from_bottom);
			mBottomView.startAnimation(animation1);
			mHandler.removeCallbacks(hideRunnable);
			mHandler.postDelayed(hideRunnable, HIDE_TIME);
		}
	}

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

	private class AnimationImp implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

	}

}
