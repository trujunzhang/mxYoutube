package com.ramesh.fragmenttabhostdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class Fragment3 extends SherlockFragment {

	private FragmentTabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mTabHost = new FragmentTabHost(getSherlockActivity());
		mTabHost.setup(getSherlockActivity(), getChildFragmentManager(), R.id.menu_settings);

		Bundle b = new Bundle();
		b.putString("key", "Simple");
		mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"), Fragment1.class, b);
		//
		b = new Bundle();
		b.putString("key", "Contacts");
		mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"), Fragment2.class, b);
		return mTabHost;
	}
}
