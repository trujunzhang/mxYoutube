package com.mxtube.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.layer.business.utils.AppConstant;
import com.mxtube.app.ui.single.HomeSingle;
import com.mxtube.app.ui.single.Single;
import com.mxtube.app.ui.single.WatchPlayerSingle_;
import com.mxtube.app.ui.single.watch.right.tabs.widget.WatchGridViewSingle;

import de.appetites.tabbackstack.TabBackStackHelper;
import de.appetites.tabbackstack.TabBackStackInterface;

public class IndexFragmentActivity extends SherlockFragmentActivity implements TabBackStackInterface {
	private TabBackStackHelper tabBackStackHelper;

	private int mTabIndex;
	private Single currentFragment, lastFragement;

	protected void initTabBackStackHelper() {
		this.tabBackStackHelper = new TabBackStackHelper(this);
	}

	private Single getFragment(int type) {
		Single fragment = null;
		switch (type) {
		case AppConstant.MAIN_FOOTBAR_HOME:// Home
			fragment = new HomeSingle();
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
		case AppConstant.WATCH_RIGHT_TAB_COMMENTS:// right_tab(comments)

			break;
		case AppConstant.WATCH_RIGHT_TAB_MORE_FROM:// right_tab(more from)

			break;
		case AppConstant.WATCH_RIGHT_TAB_SUGGESTIONS:// right_tab(suggestions)
			fragment = new WatchGridViewSingle();
			break;
		}
		if (lastFragement != null) {
			lastFragement.saveInstanceState();
		}
		lastFragement = fragment;

		fragment.initSingle();

		return fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tabBackStackHelper.onCreate(this, savedInstanceState, 5);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		this.tabBackStackHelper.onSaveInstanceState(outState);
	}

	/**
	 * Pops the current fragment of the current tab if back is pressed
	 */
	public boolean hasSubItemBack() {
		boolean pop = this.tabBackStackHelper.pop(this.getSupportFragmentManager(), this.mTabIndex);
		if (this.tabBackStackHelper.hasBackEmpty(this.mTabIndex)) {
			this.lastFragement = (Single) this.tabBackStackHelper.getCurrent(this.mTabIndex);
		}
		return pop;
	}

	protected void onTabSelected(int type) {
		this.mTabIndex = type;

		FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		this.tabBackStackHelper.onTabSelected(fragmentTransaction, type);
		fragmentTransaction.commit();
	}

	protected void onTabReselected(int type) {
		FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		this.tabBackStackHelper.onTabReselected(fragmentTransaction, type);
		fragmentTransaction.commit();
	}

	public void push(int subType) {
		Single fragment = this.getFragment(subType);
		this.tabBackStackHelper.push(this.getSupportFragmentManager(), fragment, this.mTabIndex);
	}

	@Override
	public int getContainerId() {
		return R.id.fragment_content;
	}

	@Override
	public Fragment initTab(int position) {
		this.currentFragment = this.getFragment(position);
		return this.currentFragment;
	}

}
