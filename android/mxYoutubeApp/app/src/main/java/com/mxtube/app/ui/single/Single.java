package com.mxtube.app.ui.single;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.api.Search;
import com.google.api.services.youtube.model.SearchResult;
import com.mxtube.app.R;

import java.io.InputStream;
import java.util.List;

public class Single extends SherlockFragment {

	public List<SearchResult> search() {
		InputStream in = this.getResources().openRawResource(R.raw.youtube);
		List<SearchResult> resultList = Search.searchByQuery(in);
		return resultList;
	}
}
