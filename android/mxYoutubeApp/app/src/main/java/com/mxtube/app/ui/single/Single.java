package com.mxtube.app.ui.single;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.api.services.youtube.model.Video;
import com.mxtube.app.AppContext;
import com.mxtube.app.Index;
import com.mxtube.app.R;

public abstract class Single extends SherlockFragment {

	public static Video selectedVideo;

	public Index getIndex() {
		return AppContext.instance.index;
	}

	public Context getContext() {
		return this.getSherlockActivity();
	}

	public abstract void initSingle();

	public void setTitle(String title) {
		AppContext.instance.index.setTitle(title);
	}

	public abstract void saveInstanceState();

	public abstract void abstract001();

	public abstract void abstract002();

	public abstract void abstract003();

	// public void addFragmentToWatchPanel(Fragment newFragment) {
	// // Add the fragment to the activity, pushing this transaction
	// // on to the back stack.
	// // FragmentTransaction ft = getIndex().getSupportFragmentManager().beginTransaction();
	// // ft.replace(R.id.fragment_tab_content, newFragment);
	// // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	// // ft.commit();
	// }
}
