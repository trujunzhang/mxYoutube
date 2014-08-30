package com.mxtube.app.ui.single;

import android.content.Context;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.api.Search;
import com.google.api.services.youtube.model.SearchResult;
import com.mxtube.app.AppContext;
import com.mxtube.app.R;

import java.io.InputStream;
import java.util.List;

public abstract class Single extends SherlockFragment {

	public Context getContext() {
		return AppContext.instance.index.getApplicationContext();
	}

	public List<SearchResult> search() {
		InputStream in = this.getResources().openRawResource(R.raw.youtube);
		List<SearchResult> resultList = Search.searchByQuery(in);
		return resultList;
	}

	public abstract void initSingle();

	public void setTitle(String title) {
		AppContext.instance.index.setTitle(title);
	}
}
