package com.mxtube.app.ui.single;

import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.layer.business.impl.SearchImplementation;
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

	SearchImplementation searchInterface = new SearchImplementation();

	@AfterInject
	void calledAfterInjection() {
		this.setTitle("Subscriptions");
		this.getYoutubeInBackground();
	}

	@AfterViews
	protected void calledAfterViewInjection() {

	}

	@ItemClick(R.id.gridView)
	void youtubeListItemClicked(SearchResult person) {

	}

	@Background
	void getYoutubeInBackground() {
		List<Video> resultList = searchInterface.search(getContext());
		// List<SearchResult> resultList = search();
		// showResult(resultList);
	}

	@UiThread
	void showResult(List<SearchResult> resultList) {
		adapter.updateSearchResult(resultList);
		gridView.setAdapter(adapter);
	}

	@Override
	public void initSingle() {

	}
}
