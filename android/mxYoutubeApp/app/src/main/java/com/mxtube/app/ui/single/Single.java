package com.mxtube.app.ui.single;

import android.content.Context;
import com.actionbarsherlock.app.SherlockFragment;
import com.mxtube.app.AppContext;

public abstract class Single extends SherlockFragment {

	public Context getContext() {
		return AppContext.instance.index.getApplicationContext();
	}

	public abstract void initSingle();

	public void setTitle(String title) {
		AppContext.instance.index.setTitle(title);
	}
}
