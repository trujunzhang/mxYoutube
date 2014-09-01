package com.google.android.libraries.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.google.android.libraries.mediaframework.layeredvideo.SimpleVideoPlayer;

public class YoutubeActivity extends Activity {

	private FrameLayout videoPlayerContainer;
	private SimpleVideoPlayer videoPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_youtube);

		videoPlayerContainer = (FrameLayout) this.findViewById(R.id.video_frame);

		// Video video = new Video("https://www.youtube.com/v/izA_Xgbj7II", Video.VideoType.MP4);
		videoPlayer = new SimpleVideoPlayer(this, videoPlayerContainer, null, "wanghao", false);
		videoPlayer.play();

	}
}
