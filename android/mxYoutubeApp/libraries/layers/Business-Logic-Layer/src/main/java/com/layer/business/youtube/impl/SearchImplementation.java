package com.layer.business.youtube.impl;

import android.content.Context;
import com.example.api.Search;
import com.google.api.services.youtube.model.Video;

import java.io.InputStream;
import java.util.List;

import com.layer.business.R;
import com.layer.business.youtube.SearchInterface;


public class SearchImplementation implements SearchInterface {

	@Override
	public List<Video> search(Context context) {
		InputStream in = context.getResources().openRawResource(R.raw.youtube);
		List<Video> videoList = Search.searchByQuery(in,"sketch3");
		return videoList;
	}
}
