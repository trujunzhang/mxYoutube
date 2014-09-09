package com.mxtube.app;

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

public class IndexFragmentActivity extends SherlockFragmentActivity {

	private Single currentFragment;

	public void addTabFragment(int type) {

		FragmentManager fm = getSupportFragmentManager();

		if (fm != null) {
			// setFragment(type);
			// initFragment(type);
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

	// HACK: propagate back button press to child fragments.
	// This might not work properly when you have multiple fragments adding multiple children to the backstack.
	// (in our case, only one child fragments adds fragments to the backstack, so we're fine with this)
	private boolean returnBackStackImmediate(FragmentManager fm) {
		List<Fragment> fragments = fm.getFragments();
		if (fragments != null && fragments.size() > 0) {
			for (Fragment fragment : fragments) {
				if (fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
					if (fragment.getChildFragmentManager().popBackStackImmediate()) {
						return true;
					} else {
						return returnBackStackImmediate(fragment.getChildFragmentManager());
					}
				}
			}
		}
		return false;
	}

	public boolean pressBack() {
		return this.returnBackStackImmediate(getSupportFragmentManager());
	}

	public boolean pressBack123() {
		FragmentManager fm = getSupportFragmentManager();

		// here we believe a fragment was popped, so we need to remove the fragment from ourbackstack
		int backStackEntryCount = fm.getBackStackEntryCount();
		if (backStackEntryCount > 0) {
			// currentFragment = (Single) fm.getBackStackEntryAt(backStackEntryCount - 1);
			Log.d("custombackstack", "before back: " + backStackEntryCount + " current:" + currentFragment);
			return true;
		}
		// after popping is the size > 0, if so we set current fragment from the top of stack, otherwise we default to
		// home fragment.
		if (backStackEntryCount > 0) {
			// Log.d("custombackstack", "after back: " + fm.getBackStackEntryAt(backStackEntryCount - 1));
		} else {
			// back stack empty
			// currentFragment = HOME_FRAGMENT;
		}
		return false;
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

}
