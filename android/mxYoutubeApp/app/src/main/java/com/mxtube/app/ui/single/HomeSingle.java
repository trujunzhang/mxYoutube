package com.mxtube.app.ui.single;

import android.annotation.TargetApi;
import android.os.Build;
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
import com.mxtube.app.adapter.YoutubeListAdapter_;

import org.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.single_home)
public class HomeSingle extends Single {

	private static final String LIST_INSTANCE_STATE = "101";

	@ViewById(R.id.gridView)
	android.widget.GridView gridView;

	public static YoutubeListAdapter adapter;

	public YoutubeListAdapter getAdapter() {
		if (adapter == null) {
			adapter = YoutubeListAdapter_.getInstance_(this.getSherlockActivity());
		}
		return adapter;
	}

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
		getAdapter().updateVideoList(videoList);
		gridView.setAdapter(getAdapter());
		if (mListInstanceState != null)
			gridView.onRestoreInstanceState(mListInstanceState);
	}

	@Override
	public void initSingle() {
		this.setTitle("Subscriptions");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mListInstanceState = savedInstanceState.getParcelable(LIST_INSTANCE_STATE);
		}
		this.setRetainInstance(true);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(LIST_INSTANCE_STATE, this.gridView.onSaveInstanceState());
		super.onSaveInstanceState(outState);
	}

}
