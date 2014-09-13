/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.actionbarsherlock.sample.fragments.TabsPager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.fragments.LoaderCursorSupport;
import com.actionbarsherlock.sample.fragments.LoaderCustomSupport;
import com.actionbarsherlock.sample.fragments.LoaderThrottleSupport;
import com.actionbarsherlock.sample.fragments.R;
import com.actionbarsherlock.sample.fragments.SampleList;
import com.actionbarsherlock.sample.fragments.test.FragmentStackSupport;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI that switches between tabs and also allows
 * the user to perform horizontal flicks to move between the tabs.
 */
public class FragmentTabsPager extends SherlockFragmentActivity {
	TabHost mTabHost;
	ViewPager mViewPager;
	TabsPagerAdapter mTabsPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(SampleList.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_tabs_pager);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) findViewById(R.id.pager);

		mTabsPagerAdapter = new TabsPagerAdapter(this, mTabHost, mViewPager);

		mTabsPagerAdapter.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
				FragmentStackSupport.CountingFragment.class, null);
		mTabsPagerAdapter.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"),
				LoaderCursorSupport.CursorLoaderListFragment.class, null);
		mTabsPagerAdapter.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
				LoaderCustomSupport.AppListFragment.class, null);
		mTabsPagerAdapter.addTab(mTabHost.newTabSpec("throttle").setIndicator("Throttle"),
				LoaderThrottleSupport.ThrottledLoaderListFragment.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

}
