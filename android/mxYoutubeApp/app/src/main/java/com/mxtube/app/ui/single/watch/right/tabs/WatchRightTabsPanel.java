package com.mxtube.app.ui.single.watch.right.tabs;

import android.content.Context;
import android.util.AttributeSet;
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

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.watch_right_tabs)
public class WatchRightTabsPanel extends LinearLayout implements android.widget.TabHost.OnTabChangeListener {
	private Context mContext;
	private WatchPlayerSingle watchPlayerSingle;

	@ViewById(android.R.id.tabhost)
	public FragmentTabHost mTabHost;

	public WatchRightTabsPanel(Context context) {
		super(context);
	}

	public WatchRightTabsPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@AfterViews
	protected void calledAfterViewInjection() {

	}

	public void bind(android.content.Context context, android.support.v4.app.FragmentManager manager) {
		// mTabHost = new FragmentTabHost(getSherlockActivity());
		mTabHost.setup(context, manager, R.id.realtabcontent);
		mTabHost.setOnTabChangedListener(this);

		mTabHost.addTab(mTabHost.newTabSpec("comments").setIndicator("Comments"), WatchCommentsSingle.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("morefrom").setIndicator("More From"), WatchMoreFromSingle.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("suggestions").setIndicator("Suggestions"), WatchMoreFromSingle.class, null);
	}

	@Override
	public void onTabChanged(String s) {
		View currentTabView = mTabHost.getCurrentTabView();
		int x = 0;// debug
	}
}
