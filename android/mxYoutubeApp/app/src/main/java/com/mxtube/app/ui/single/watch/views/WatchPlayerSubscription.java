package com.mxtube.app.ui.single.watch.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.google.api.services.youtube.model.Video;
import com.mxtube.app.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.watch_player_subscription)
public class WatchPlayerSubscription extends LinearLayout {

	@ViewById(R.id.user_header)
	android.widget.ImageView userHeader;
	@ViewById(R.id.user_name)
	android.widget.TextView userName;

	public WatchPlayerSubscription(Context context) {
		super(context);
	}

	public void bind(Context context, Video singleVideo) {
		this.userName.setText(singleVideo.getSnippet().getChannelTitle());
	}

}
