package com.google.android.libraries.mediaframework.layeredvideo.layer.playback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.libraries.mediaframework.R;
import com.google.android.libraries.mediaframework.layeredvideo.LayerManager;
import com.google.android.libraries.mediaframework.layeredvideo.callback.FullscreenCallback;

public class ResumePlayBackControlLayer extends PlayBackControlLayer {

    public ResumePlayBackControlLayer(FullscreenCallback fullscreenCallback) {
        super(fullscreenCallback);
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

		mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

		return view;
	}

    @Override
    public void onLayerDisplayed(LayerManager layerManager) {
        this.hideTopChrome();
        this.hideBottomChrome();
    }

}
