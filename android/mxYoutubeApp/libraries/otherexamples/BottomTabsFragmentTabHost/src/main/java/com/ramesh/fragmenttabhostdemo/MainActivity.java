package com.ramesh.fragmenttabhostdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ramesh.fragmenttabhostdemo.panels.FragmentPanel;
import com.ramesh.fragmenttabhostdemo.panels.FragmentPanel_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.bottom_tabs)
public class MainActivity extends SherlockFragmentActivity {

	@ViewById(android.R.id.tabhost)
	public FragmentTabHost mTabHost;

	@AfterViews
	protected void calledAfterViewInjection() {

		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		Bundle b = new Bundle();
//		b.putString("key", "Simple");
//		mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"), Fragment1.class, b);
//		b = new Bundle();

//		System.out.print("hello git");
//		b.putString("key", "Contacts");
//		mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"), Fragment2.class, b);

		b = new Bundle();
		b.putString("key", "Custom");
		mTabHost.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"), FragmentPanel_.class, b);
	}

}
