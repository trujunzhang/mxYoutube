package com.mxtube.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.common.utils.UIHelper;
import com.layer.business.utils.AppConstant;
import com.mxtube.app.ui.single.HomeSingle_;
import com.mxtube.app.ui.single.Single;
import com.mxtube.app.ui.single.WatchPlayerSingle_;
import com.mxtube.app.ui.views.FooterView;
import com.mxtube.app.ui.views.FooterView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.appetites.tabbackstack.TabBackStackHelper;
import de.appetites.tabbackstack.TabBackStackInterface;

public class IndexFragmentActivity extends SherlockFragmentActivity implements TabBackStackInterface {
	private TabBackStackHelper tabBackStackHelper;

	protected void initTabBackStackHelper() {
		this.tabBackStackHelper = new TabBackStackHelper(this);
	}

	private Single currentFragment;

	public void addTabFragment(int type) {

		FragmentManager fm = getSupportFragmentManager();

		if (fm != null) {
			setFragment(type);
			initFragment(type);
		}
	}

	private void initFragment(int type) {
		this.currentFragment.initSingle();
	}

	private void setFragment(int type) {
		currentFragment = getFragment(type);
		replaceFragment(currentFragment);
	}

	private void replaceFragment(SherlockFragment fragment) {
		String backStateName = fragment.getClass().getName();

		boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName, 0);

		if (!fragmentPopped) { // fragment not in back stack, create it.
			android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_content, fragment);
			// ft.addToBackStack(backStateName);
			ft.commit();
		}
	}


	private Single getFragment(int type) {
		Single fragment = null;
		switch (type) {
		case AppConstant.MAIN_FOOTBAR_HOME:// Home
			fragment = new HomeSingle_();
			break;
		case AppConstant.MAIN_FOOTBAR_SEARCH:// Search
			break;
		case AppConstant.MAIN_FOOTBAR_HISTRORY:// History
			break;
		case AppConstant.MAIN_FOOTBAR_CACHE:// Cache
			break;
		case AppConstant.MAIN_FOOTBAR_MORE:// More
			break;
		case AppConstant.TYPE_FRAGMENT_MEDIA_PLAYER:// Media Player
			fragment = new WatchPlayerSingle_();
			break;
		}

		return fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// windowManager should not be null
		this.tabBackStackHelper.onCreate(this, savedInstanceState, 1);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		this.tabBackStackHelper.onSaveInstanceState(outState);
	}

	/**
	 * Pops the current fragment of the current tab if back is pressed
	 */
	@Override
	public void onBackPressed() {
		if (!this.tabBackStackHelper.pop(this.getSupportFragmentManager(), 0)) {
			// super.onBackPressed();
		}
	}

	@Override
	public int getContainerId() {
		return 0;
	}

	@Override
	public Fragment initTab(int position) {
		return null;
	}
}
