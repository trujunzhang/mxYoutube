package com.ramesh.fragmenttabhostdemo.panels.views;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ramesh.fragmenttabhostdemo.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.ramesh.fragmenttabhostdemo.Fragment1;
import com.ramesh.fragmenttabhostdemo.Fragment2;
import com.ramesh.fragmenttabhostdemo.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.tab_linearlayout)
public class TabLinearLayout extends LinearLayout {

	public TabLinearLayout(Context context) {
		super(context);
	}

	public TabLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@ViewById(android.R.id.tabhost)
	public FragmentTabHost mTabHost;

	@AfterViews
	protected void calledAfterViewInjection() {

	}

	public void bind(android.content.Context context, android.support.v4.app.FragmentManager manager) {
		// mTabHost = new FragmentTabHost(getSherlockActivity());
		mTabHost.setup(context, manager, R.id.realtabcontent);

		Bundle b = new Bundle();
		b.putString("key", "Simple");
		mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"), Fragment1.class, b);

		b = new Bundle();
		b.putString("key", "Contacts");
		mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"), Fragment2.class, b);
	}
}
