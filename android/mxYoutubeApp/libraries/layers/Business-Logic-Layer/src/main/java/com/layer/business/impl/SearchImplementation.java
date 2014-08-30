package com.layer.business.impl;

import android.content.Context;
import com.google.api.services.youtube.model.Video;
import com.layer.business.SearchInterface;

import java.io.InputStream;
import java.util.List;

public class SearchImplementation implements SearchInterface {

	@Override
	public List<Video> search(Context context) {
		InputStream in = context.getResources().openRawResource(R.raw.youtube);
		// List<Video> resultList = Search.searchByQuery(in);
		return null;
	}
}
