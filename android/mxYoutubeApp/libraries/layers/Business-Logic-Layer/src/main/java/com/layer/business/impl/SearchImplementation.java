package com.layer.business.impl;

import android.content.Context;
import com.example.api.Search;
import com.google.api.services.youtube.model.Video;
import com.layer.business.SearchInterface;

import java.io.InputStream;
import java.util.List;

import com.layer.business.R;


public class SearchImplementation implements SearchInterface {

	@Override
	public List<Video> search(Context context) {
		InputStream in = context.getResources().openRawResource(R.raw.youtube);
		List<Video> videoList = Search.searchByQuery(in,"sketch3");
		return videoList;
	}
}
