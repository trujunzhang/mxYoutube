package com.mxtube.app.ui.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.keyes.youtube.ui.YouTubePlayerHelper;
import org.androidannotations.annotations.EFragment;

@EFragment
public class MediaPlayer extends Single {
	private YouTubePlayerHelper playerHelper = new YouTubePlayerHelper();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return this.playerHelper.setupView(this.getSherlockActivity());
	}

	@Override
	public void initSingle() {

	}
}
