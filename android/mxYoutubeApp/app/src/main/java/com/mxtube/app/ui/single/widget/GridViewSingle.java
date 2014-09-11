package com.mxtube.app.ui.single.widget;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.services.youtube.model.Video;
import com.layer.business.utils.AppConstant;
import com.layer.business.youtube.impl.SearchImplementation;
import com.mxtube.app.R;
import com.mxtube.app.adapter.YoutubeListAdapter;
import com.mxtube.app.ui.single.Single;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.widget_gridview)
public class GridViewSingle extends Single {

	@ViewById(R.id.gridView)
	android.widget.GridView gridView;

	@Bean
	YoutubeListAdapter adapter;

	SearchImplementation searchInterface = new SearchImplementation();

	private static Parcelable mListInstanceState;

	@AfterInject
	void calledAfterInjection() {
		this.getYoutubeInBackground();
	}

	@AfterViews
	protected void calledAfterViewInjection() {

	}

	@ItemClick(R.id.gridView)
	void youtubeListItemClicked(Video video) {
		Single.selectedVideo = video;
		this.getIndex().push(AppConstant.TYPE_FRAGMENT_MEDIA_PLAYER);
	}

	@Background
	void getYoutubeInBackground() {
		List<Video> videoList = searchInterface.search(getContext());
		// v1.0
		update(videoList);
		// v2.0
		// youtubeListItemClicked(videoList.get(0));
	}

	@UiThread
	void update(List<Video> videoList) {
		adapter.updateVideoList(videoList);
		gridView.setAdapter(adapter);
		if (mListInstanceState != null)
			gridView.onRestoreInstanceState(mListInstanceState);
	}

	@Override
	public void initSingle() {
		this.setTitle("Subscriptions");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void saveInstanceState() {
		mListInstanceState = this.gridView.onSaveInstanceState();
	}

	@Override
	public void abstract001() {

	}

	@Override
	public void abstract002() {

	}

	@Override
	public void abstract003() {

	}

}
