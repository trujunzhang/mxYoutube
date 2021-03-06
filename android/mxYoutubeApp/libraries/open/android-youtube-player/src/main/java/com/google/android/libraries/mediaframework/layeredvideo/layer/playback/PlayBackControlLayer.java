package com.google.android.libraries.mediaframework.layeredvideo.layer.playback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.LayerManager;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;
import com.google.android.libraries.mediaframework.layeredvideo.layer.Layer;
import com.google.android.libraries.mediaframework.layeredvideo.utils.DensityUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class PlayBackControlLayer implements Layer {
	// 屏幕宽高
	private float width;
	private float height;

	// 视频播放时间
	private int playTime;

	// 自动隐藏顶部和底部View的时间
	private static final int HIDE_TIME = 5000;

	// 原始屏幕亮度
	private int orginalLight;

	// 头部View
	public View mTopView;

	// 底部View
	public View mBottomView;

	public ImageView mPlay;

	// 视频播放拖动条
	public SeekBar mSeekBar;

	public TextView mPlayTime;
	public TextView mDurationTime;
	public ImageView fullscreenButton;

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
	 * The {@link com.google.android.libraries.mediaframework.layeredvideo.LayerManager} which is responsible for adding
	 * this layer to the container and displaying it on top of the video player.
	 */
	private LayerManager layerManager;

	protected Context mContext;

	public PlayBackControlLayer(FullscreenCallback fullscreenCallback) {
		this.fullscreenCallback = fullscreenCallback;
	}

	@Override
	public FrameLayout createView(LayerManager layerManager) {
		this.layerManager = layerManager;
		this.mContext = layerManager.getActivity();

		width = DensityUtil.getWidthInPx(mContext);
		height = DensityUtil.getHeightInPx(mContext);

		LayoutInflater inflater = layerManager.getActivity().getLayoutInflater();

		view = setupView(inflater);

		return view;
	}

	protected abstract FrameLayout setupView(LayoutInflater inflater);

	public void hideChrome() {
		mTopView.setVisibility(View.GONE);
		mBottomView.setVisibility(View.GONE);
	}

	public void hideTopChrome() {
		mTopView.setVisibility(View.GONE);
	}

	public void hideBottomChrome() {
		mBottomView.setVisibility(View.GONE);
	}

	public void showTopChrome() {
		mTopView.setVisibility(View.VISIBLE);
	}

	public View.OnClickListener playListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (getVideo().isPlaying()) {
				getVideo().pause();
				mPlay.setImageResource(R.drawable.video_btn_down);
			} else {
				getVideo().start();
				mPlay.setImageResource(R.drawable.video_btn_on);
			}
		}
	};

	public OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {

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
				int time = progress * getVideo().getDuration() / 100;
				getVideo().seekTo(time);
			}
		}
	};

	public void preparedController(VideoView video) {
		mHandler.removeCallbacks(hideRunnable);
		mHandler.postDelayed(hideRunnable, HIDE_TIME);
		mDurationTime.setText(formatTime(video.getDuration()));
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(1);
			}
		}, 0, 1000);
	}

	public void completionWithController() {
		mPlay.setImageResource(R.drawable.video_btn_down);
		mPlayTime.setText("00:00");
		mSeekBar.setProgress(0);
	}

	public void backward(VideoView video, float delataX) {
		int current = video.getCurrentPosition();
		int backwardTime = (int) (delataX / width * video.getDuration());
		int currentTime = current - backwardTime;
		video.seekTo(currentTime);
		mSeekBar.setProgress(currentTime * 100 / video.getDuration());
		mPlayTime.setText(formatTime(currentTime));
	}

	public void forward(VideoView video, float delataX) {
		int current = video.getCurrentPosition();
		int forwardTime = (int) (delataX / width * video.getDuration());
		int currentTime = current + forwardTime;
		video.seekTo(currentTime);
		mSeekBar.setProgress(currentTime * 100 / video.getDuration());
		mPlayTime.setText(formatTime(currentTime));
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (getVideo().getCurrentPosition() > 0) {
					mPlayTime.setText(formatTime(getVideo().getCurrentPosition()));
					int progress = getVideo().getCurrentPosition() * 100 / getVideo().getDuration();
					mSeekBar.setProgress(progress);
					if (getVideo().getCurrentPosition() > getVideo().getDuration() - 100) {
						mPlayTime.setText("00:00");
						mSeekBar.setProgress(0);
					}
					mSeekBar.setSecondaryProgress(getVideo().getBufferPercentage());
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

	public void showOrHide() {
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

	public VideoView getVideo() {
		return this.getLayerManager().getVideoSurfaceViewLayer().getVideoView();
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
