package com.mxtube.app.ui.single.watch.views;

import android.content.Context;
import android.widget.LinearLayout;
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

import com.mxtube.app.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.watch_right_tabs)
public class WatchRightTabs extends LinearLayout {

	@ViewById(R.id.button_comments)
	android.widget.Button buttonComments;
	@ViewById(R.id.button_more_from)
	android.widget.Button buttonMoreFrom;
	@ViewById(R.id.button_suggestions)
	android.widget.Button buttonSuggestions;

	@Click(R.id.button_comments)
	void button_commentsOnClick(View view) {

	}

	@Click(R.id.button_more_from)
	void button_more_fromOnClick(View view) {

	}

	@Click(R.id.button_suggestions)
	void button_suggestionsOnClick(View view) {

	}

	public WatchRightTabs(Context context) {
		super(context);
	}

	@AfterInject
	void calledAfterInjection() {
	}

	@AfterViews
	protected void calledAfterViewInjection() {

	}

	@Background
	void getYoutubeInBackground() {
		// List<Video> videoList = searchInterface.search(getContext());
		// v1.0
		// update(videoList);
		// v2.0
		// youtubeListItemClicked(videoList.get(0));
	}

	@UiThread
	void update(List<Video> videoList) {
		// getAdapter().updateVideoList(videoList);
		// gridView.setAdapter(getAdapter());
		// if (mListInstanceState != null)
		// gridView.onRestoreInstanceState(mListInstanceState);
	}

}
