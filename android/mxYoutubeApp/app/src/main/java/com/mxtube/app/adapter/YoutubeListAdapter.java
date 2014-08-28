package com.mxtube.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.api.services.youtube.model.SearchResult;
import com.mxtube.app.adapter.views.YoutubeItemView;
import com.mxtube.app.adapter.views.YoutubeItemView_;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Collections;
import java.util.List;

@EBean
public class YoutubeListAdapter extends BaseAdapter {

	private List<SearchResult> youtubeList = Collections.emptyList();

	public void updateSearchResult(List<SearchResult> bananaPhones) {
		if (bananaPhones == null)
			return;
		this.youtubeList = bananaPhones;
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
		return youtubeList.size();
	}

	@Override
	public SearchResult getItem(int position) {
		return youtubeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}