package com.mxtube.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.api.services.youtube.model.Video;
import com.mxtube.app.adapter.views.YoutubeItemView;
import com.mxtube.app.adapter.views.YoutubeItemView_;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Collections;
import java.util.List;

@EBean
public class YoutubeListAdapter extends BaseAdapter {

	private List<Video> videoList = Collections.emptyList();

	public void updateVideo(List<Video> bananaPhones) {
		if (bananaPhones == null)
			return;
		this.videoList = bananaPhones;
		notifyDataSetChanged();
	}

	@RootContext
	Context context;

	@AfterInject
	void initAdapter() {

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		YoutubeItemView youtubeItemView;
		if (convertView == null) {
			youtubeItemView = YoutubeItemView_.build(context);
		} else {
			youtubeItemView = (YoutubeItemView) convertView;
		}

		youtubeItemView.bind(context, getItem(position));

		return youtubeItemView;
	}

	@Override
	public int getCount() {
		return videoList.size();
	}

	@Override
	public Video getItem(int position) {
		return videoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}