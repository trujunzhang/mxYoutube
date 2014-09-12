package com.mxtube.app.ui.single.watch.right.tabs;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.app.FragmentManager;
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
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import android.support.v4.app.FragmentTabHost;

@EViewGroup(R.layout.watch_right_tabs)
public class WatchRightTabsPanel extends LinearLayout implements android.widget.TabHost.OnTabChangeListener {
	@ViewById(android.R.id.tabhost)
	public FragmentTabHost mTabHost;
	private FragmentManager manager;

	private String lastTag;

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
		this.manager = manager;
		mTabHost.setup(context, manager, R.id.realtabcontent);
		mTabHost.setOnTabChangedListener(this);

		mTabHost.addTab(mTabHost.newTabSpec("comments").setIndicator("Comments"), WatchCommentsSingle.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("morefrom").setIndicator("More From"), WatchMoreFromSingle.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("suggestions").setIndicator("Suggestions"), WatchSuggestionsSingle.class,
				null);
	}

	@Override
	public void onTabChanged(String tag) {
		toggleTabByTag(this.lastTag);
		this.lastTag = tag;
	}

	private void toggleTabByTag(String tag) {
		if (tag == null)
			return;

		Single lastSingle = (Single) this.manager.findFragmentByTag(tag);
		if (this.manager == null)
			return;
		if (this.manager.getFragments() == null)
			return;
		lastSingle.saveInstanceState();
	}
}
