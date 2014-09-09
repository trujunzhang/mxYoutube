package com.mxtube.app.ui.single;

import android.app.Activity;
import android.content.Context;
import com.actionbarsherlock.app.SherlockFragment;
import com.google.api.services.youtube.model.Video;
import com.mxtube.app.AppContext;

public abstract class Single extends SherlockFragment {

	public static Video selectedVideo;

//	public com.mxtube.app.ui.Footer getFooterFragement() {
////		return AppContext.instance.index.fragmentFooterTab;
//        return null;
//	}

	public Context getContext() {
		return this.getSherlockActivity();
	}

	public abstract void initSingle();

	public void setTitle(String title) {
		AppContext.instance.index.setTitle(title);
	}
}
