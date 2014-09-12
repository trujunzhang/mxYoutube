package com.mxtube.app.ui.single.watch.right.tabs;

import android.content.Context;
import android.widget.LinearLayout;
import android.view.View;

import com.google.api.services.youtube.model.Video;
import com.layer.business.utils.AppConstant;
import com.mxtube.app.R;

import org.androidannotations.annotations.*;

import java.util.List;

import com.mxtube.app.ui.single.Single;
import com.mxtube.app.ui.single.WatchPlayerSingle;
import com.mxtube.app.ui.single.watch.right.tabs.widget.WatchCommentsSingle;
import com.mxtube.app.ui.single.watch.right.tabs.widget.WatchMoreFromSingle;
import com.mxtube.app.ui.single.watch.right.tabs.widget.WatchSuggestionsSingle;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.watch_right_tabs)
public class WatchRightTabsPanel extends LinearLayout {
	private Context mContext;
	private WatchPlayerSingle watchPlayerSingle;

	private Single selectedSingle, lastSingle;

	@ViewById(R.id.button_comments)
	public android.widget.Button buttonComments;
	@ViewById(R.id.button_more_from)
	public android.widget.Button buttonMoreFrom;
	@ViewById(R.id.button_suggestions)
	public android.widget.Button buttonSuggestions;

	@Click(R.id.button_comments)
	public void button_commentsOnClick(View view) {

	}

	@Click(R.id.button_more_from)
	public void button_more_fromOnClick(View view) {

	}

	@Click(R.id.button_suggestions)
	public void button_suggestionsOnClick(View view) {

	}

	public WatchRightTabsPanel(Context context) {
		super(context);
	}

	@AfterInject
	public void calledAfterInjection() {

	}

	@AfterViews
	public void calledAfterViewInjection() {

	}

	@Background
	public void getYoutubeInBackground() {
		// List<Video> videoList = searchInterface.search(getContext());
		// v1.0
		// update(videoList);
		// v2.0
		// youtubeListItemClicked(videoList.get(0));
	}

	@UiThread
	public void update(List<Video> videoList) {
		// getAdapter().updateVideoList(videoList);
		// gridView.setAdapter(getAdapter());
		// if (mListInstanceState != null)
		// gridView.onRestoreInstanceState(mListInstanceState);
	}

	public void bind(WatchPlayerSingle watchPlayerSingle, Context context) {
		this.watchPlayerSingle = watchPlayerSingle;
		this.mContext = context;

		toggleTab(AppConstant.WATCH_RIGHT_TAB_SUGGESTIONS);
	}

	private void toggleTab(int type) {
		// Toggle fragment.
		selectedSingle = getFragment(type);
		this.watchPlayerSingle.addFragmentToWatchPanel(selectedSingle);
		// Set the selected navigation item in list or tabbed navigation modes.
		this.setSelectedNavigationItem(type);
	}

	public void setSelectedNavigationItem(int position) {

	}

	private Single getFragment(int type) {
		Single fragment = null;
		switch (type) {
		case AppConstant.WATCH_RIGHT_TAB_COMMENTS:// right_tab(comments)
			fragment = new WatchCommentsSingle();
			break;
		case AppConstant.WATCH_RIGHT_TAB_MORE_FROM:// right_tab(more from)
			fragment = new WatchMoreFromSingle();
			break;
		case AppConstant.WATCH_RIGHT_TAB_SUGGESTIONS:// right_tab(suggestions)
			fragment = new WatchSuggestionsSingle();
			break;
		}
		fragment.initSingle();

		return fragment;
	}

}
