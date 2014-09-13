package de.appetites.tabbackstack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import de.appetites.tabbackstack.util.FragmentBackStack;

public class TabBackStackHelper {
	public TabBackStackHelper(TabBackStackInterface tabBackStackInterface) {
		this.tabBackStackInterface = tabBackStackInterface;
	}

	private TabBackStackInterface tabBackStackInterface;

	private static final String TAG = "TabBackStackHelper";

	protected SparseArray<FragmentBackStack> mTabBackStacks;

	public void onCreate(Context context, Bundle savedInstanceState, int tabCount) {
		if (savedInstanceState != null) {
			mTabBackStacks = savedInstanceState.getSparseParcelableArray("TAB_STACKS");
			if (mTabBackStacks != null) {
				for (int i = 0; i < mTabBackStacks.size(); i++) {
					mTabBackStacks.get(i).recreateBackStack(context);
				}
			}
		} else if (mTabBackStacks == null) {
			mTabBackStacks = new SparseArray<FragmentBackStack>();
			for (int i = 0; i < tabCount; i++) {
				mTabBackStacks.append(i, new FragmentBackStack());
			}
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		outState.putSparseParcelableArray("TAB_STACKS", mTabBackStacks);
	}

	/**
	 * Pushes a fragment onto the currents tab backstack and displays it
	 *
	 * @param fragment
	 *            Fragment to push and display
	 */
	public void push(FragmentTransaction fragmentTransaction, Fragment fragment, int position) {
		mTabBackStacks.get(position).push(fragment, fragmentTransaction, this.tabBackStackInterface.getContainerId());
		fragmentTransaction.commit();
	}

	/**
	 * Pops the top most fragment from the current tab, removes it from the backstack and displays the last fragment in
	 * the changed backstack
	 *
	 * @return true if the fragment could be popped, false if the backstack only has one element left
	 */
	public boolean pop(FragmentManager fm, int position) {
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);

		if (fragmentBackStack.size() > 1) {
			// Pop last fragment from stack
			fragmentBackStack.pop();

			// Replace current fragment with the last fragment in the stack
			FragmentTransaction fragmentTransaction = fm.beginTransaction();
			fragmentTransaction.replace(this.tabBackStackInterface.getContainerId(), fragmentBackStack.getCurrent());
			fragmentTransaction.commit();

			return true;
		} else {
			return false;
		}
	}

	public boolean hasBackEmpty(int position) {
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);

		return fragmentBackStack.size() == 1;
	}

	public Fragment getCurrent(int position) {
		// get current tab position and retrieve the backstack
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);

		return fragmentBackStack.getCurrent();
	}

	/**
	 * Returns the FragmentBackStack for the given tab position
	 * 
	 * @param tabPosition
	 *            The tabs position whose backstack shall be fetched
	 * @return The backstack for the given tab position
	 */
	public FragmentBackStack getBackStackForTab(int tabPosition) {
		return mTabBackStacks.get(tabPosition);
	}

	public void onTabSelected(FragmentTransaction fragmentTransaction, int position) {
		Fragment fragment;

		// get current tab position and retrieve the backstack
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);

		// if backstack is empty call initTab to get initial fragment
		if (fragmentBackStack.size() == 0) {
			// init tab
			fragment = this.tabBackStackInterface.initTab(position);
		} else {
			// display last fragment of backstack
			fragment = fragmentBackStack.getCurrent();
		}

		// replace current fragment with the new fragment
		if (fragment != null) {
			fragmentBackStack.push(fragment, fragmentTransaction, this.tabBackStackInterface.getContainerId());
		} else {
			Log.e(TAG, "Could not replace fragment, new fragment must not be null", new NullPointerException());
		}
	}

	public void onTabReselected(FragmentTransaction fragmentTransaction, int position) {
		// get current tab position and retrieve the backstack
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);
		if (fragmentBackStack.size() > 1) {
			// replace current fragment with the root fragment
			fragmentBackStack.push(fragmentBackStack.popToRoot(), fragmentTransaction,
					this.tabBackStackInterface.getContainerId());
		}
	}

}
