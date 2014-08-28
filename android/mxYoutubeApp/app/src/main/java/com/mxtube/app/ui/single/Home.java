package com.mxtube.app.ui.single;

import com.google.api.services.youtube.model.SearchResult;
import com.mxtube.app.R;
import com.mxtube.app.adapter.YoutubeListAdapter;
import org.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.single_home)
public class Home extends Single {

	@ViewById(R.id.gridView)
	android.widget.GridView gridView;

	@Bean
	YoutubeListAdapter adapter;

	@AfterInject
	void calledAfterInjection() {
		this.getYoutubeInBackground();
	}

	@AfterViews
	protected void calledAfterViewInjection() {

	}

	@ItemClick
	void youtubeListItemClicked(SearchResult person) {

	}

	@Background
	void getYoutubeInBackground() {
		List<SearchResult> resultList = search();
		showResult(resultList);
	}

	@UiThread
	void showResult(List<SearchResult> resultList) {
		adapter.updateSearchResult(resultList);
		gridView.setAdapter(adapter);
	}
}
