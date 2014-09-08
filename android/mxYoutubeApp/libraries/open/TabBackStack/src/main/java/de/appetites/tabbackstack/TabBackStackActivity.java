package de.appetites.tabbackstack;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import de.appetites.tabbackstack.util.FragmentBackStack;

public abstract class TabBackStackActivity extends Activity implements ActionBar.TabListener {
    /**
     * Returns the id of the container used for replacing the tab bars fragments. NEEDS TO BE IMPLEMENTED AND RETURN A
     * VALID CONTAINER ID
     *
     * @return id of the container
     */
    public abstract int getContainerId();

    protected abstract Fragment initTab(int position);

	private static final String TAG = "TabBackStackActivity";
	protected ActionBar mActionBar;
	protected SparseArray<FragmentBackStack> mTabBackStacks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getActionBar();

		if (savedInstanceState != null) {
			mTabBackStacks = savedInstanceState.getSparseParcelableArray("TAB_STACKS");
			if (mTabBackStacks != null) {
				for (int i = 0; i < mTabBackStacks.size(); i++) {
					mTabBackStacks.get(i).recreateBackStack(this);
				}
			}
		} else if (mTabBackStacks == null) {
			mTabBackStacks = new SparseArray<FragmentBackStack>();
			for (int i = 0; i < 4; i++) {
				mTabBackStacks.append(i, new FragmentBackStack());
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSparseParcelableArray("TAB_STACKS", mTabBackStacks);
		super.onSaveInstanceState(outState);
	}

	/**
	 * Pushes a fragment onto the currents tab backstack and displays it
	 *
	 * @param fragment
	 *            Fragment to push and display
	 */
	public void push(Fragment fragment) {
		ActionBar.Tab selectedTab = mActionBar.getSelectedTab();
		if (selectedTab != null) {
			int position = selectedTab.getPosition();
			FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentBackStack.push(fragment, fragmentTransaction, getContainerId());
			fragmentTransaction.commit();
		}
	}


	/**
	 * Pops the top most fragment from the current tab, removes it from the backstack and displays the last fragment in
	 * the changed backstack
	 *
	 * @return true if the fragment could be popped, false if the backstack only has one element left
	 */
	public boolean pop() {
		ActionBar.Tab selectedTab = mActionBar.getSelectedTab();
		if (selectedTab != null) {
			int position = selectedTab.getPosition();
			FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);

			if (fragmentBackStack.size() > 1) {
				// Pop last fragment from stack
				fragmentBackStack.pop();

				// Replace current fragment with the last fragment in the stack
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.replace(getContainerId(), fragmentBackStack.getCurrent());
				fragmentTransaction.commit();

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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

	/**
	 * Pops the current fragment of the current tab if back is pressed
	 */
	@Override
	public void onBackPressed() {
		if (!pop()) {
			super.onBackPressed();
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		Fragment fragment;

		// get current tab position and retrieve the backstack
		int position = tab.getPosition();
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);

		// if backstack is empty call initTab to get initial fragment
		if (fragmentBackStack.size() == 0) {
			// init tab
			fragment = initTab(position);
		} else {
			// display last fragment of backstack
			fragment = fragmentBackStack.getCurrent();
		}

		// replace current fragment with the new fragment
		if (fragment != null) {
			fragmentBackStack.push(fragment, fragmentTransaction, getContainerId());
		} else {
			Log.e(TAG, "Could not replace fragment, new fragment must not be null", new NullPointerException());
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// get current tab position and retrieve the backstack
		int position = tab.getPosition();
		FragmentBackStack fragmentBackStack = mTabBackStacks.get(position);
		if (fragmentBackStack.size() > 1) {
			// replace current fragment with the root fragment
			fragmentBackStack.push(fragmentBackStack.popToRoot(), fragmentTransaction, getContainerId());
		}
	}
}
