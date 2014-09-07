package com.mxtube.app.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import com.actionbarsherlock.app.SherlockFragment;
import com.mxtube.app.Index;
import com.mxtube.app.R;
import com.mxtube.app.ui.single.HomeSingle_;
import com.mxtube.app.ui.single.WatchPlayerSingle_;
import com.mxtube.app.ui.single.Single;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.List;

@EFragment(R.layout.main_footer)
public class Footer extends SherlockFragment {

	public static final int TYPE_FRAGMENT_HOME = 0;
	public static final int TYPE_FRAGMENT_MEDIA_PLAYER = 1;

	private int mCurSel;
	private RadioButton lastRadioButton;

	private Single currentFragment;

	@AfterViews
	protected void calledAfterViewInjection() {
		this.mCurSel = -1;
		setButtonClickEvent(null, TYPE_FRAGMENT_HOME);
		// setButtonClickEvent(null, TYPE_FRAGMENT_MEDIA_PLAYER);
	}

	public void setButtonClickEvent(View view, int pos) {
		setTabFragment(pos);
		setCurPoint(view, pos);
	}

	private void setTabFragment(int pos) {
		// Fragments have access to their parent Activity's FragmentManager. You can
		// obtain the FragmentManager like this.
		FragmentManager fm = getFragmentManager();

		if (fm != null) {
			if (pos != this.mCurSel) {

				setFragment(pos, fm);
				initFragment(pos, fm);
				this.mCurSel = pos;
			}
		}
	}

	private void initFragment(int pos, FragmentManager fm) {
		this.currentFragment.initSingle();
	}

	private void setFragment(int pos, FragmentManager manager) {
		currentFragment = getFragment(pos);
		replaceFragment(manager, currentFragment);
	}

	private void replaceFragment(FragmentManager manager, SherlockFragment fragment) {
		String backStateName = fragment.getClass().getName();

		boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

		if (!fragmentPopped) { // fragment not in back stack, create it.
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(R.id.fragment_content, fragment);
//			ft.addToBackStack(backStateName);
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
		FragmentManager fm = getFragmentManager();
		return this.returnBackStackImmediate(fm);
	}

	public boolean pressBack123() {
		FragmentManager fm = getFragmentManager();

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

	// private void setFragment123(int pos, FragmentManager manager) {
	// // String backStateName = manager.getClass().getName();
	// // Perform the FragmentTransaction to load in the list tab content.
	// // Using FragmentTransaction#replace will destroy any Fragments
	// // currently inside R.id.fragment_content and add the new Fragment
	// // in its place.
	// FragmentTransaction ft = manager.beginTransaction();
	//
	// currentFragment = getFragment(pos);
	// ft.replace(R.id.fragment_content, currentFragment);
	// ft.commit();
	// }

	private Single getFragment(int pos) {
		Single fragment = null;
		switch (pos) {
		case TYPE_FRAGMENT_HOME:// Home
			fragment = new HomeSingle_();
			break;
		case TYPE_FRAGMENT_MEDIA_PLAYER:// Media Player
			fragment = new WatchPlayerSingle_();
			break;
		case 2:// Recent
				// fragment = new TweetMenuFragment_();
			break;
		case 3:// Local
			break;
		}

		return fragment;
	}

	private void setCurPoint(View view, int index) {
		// RadioButton newButton = (RadioButton) view;

		if (lastRadioButton != null) {
			lastRadioButton.setChecked(false);
		}
		// newButton.setChecked(true);
		Index activity = (Index) getSherlockActivity();
		// activity.setTitle(index);
		mCurSel = index;

		// lastRadioButton = newButton;
	}

}
