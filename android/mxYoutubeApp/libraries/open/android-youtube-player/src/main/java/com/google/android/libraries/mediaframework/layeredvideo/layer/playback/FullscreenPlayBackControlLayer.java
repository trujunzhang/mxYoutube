package com.google.android.libraries.mediaframework.layeredvideo.layer.playback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;

public class FullscreenPlayBackControlLayer extends PlayBackControlLayer {

	public FullscreenPlayBackControlLayer(String videoTitle) {
		super(videoTitle);
	}

	public FullscreenPlayBackControlLayer(String videoTitle, FullscreenCallback fullscreenCallback) {
		super(videoTitle, fullscreenCallback);
	}

	@Override
	protected FrameLayout setupView(LayoutInflater inflater) {
		FrameLayout view = (FrameLayout) inflater.inflate(R.layout.normal_control_layer, null);

		this.mPlayTime = (TextView) view.findViewById(R.id.play_time);
		this.mDurationTime = (TextView) view.findViewById(R.id.total_time);
		this.mPlay = (ImageView) view.findViewById(R.id.play_btn);
		this.fullscreenButton = (ImageView) view.findViewById(R.id.fullscreen);
		this.mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
		this.mTopView = view.findViewById(R.id.top_layout);
		this.mBottomView = view.findViewById(R.id.bottom_layout);

		mPlay.setOnClickListener(playListener);
		// Go into fullscreen when the fullscreen button is clicked.
		fullscreenButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				doToggleFullscreen();
			}
		});
		mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

		return view;
	}

}
