package com.mxtube.app.ui.single;

import com.google.api.services.youtube.model.Video;
import com.layer.business.impl.SearchImplementation;
import com.mxtube.app.R;
import com.mxtube.app.adapter.YoutubeListAdapter;
import com.mxtube.app.ui.Footer;
import org.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.single_home)
public class HomeSingle extends Single {

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
	void youtubeListItemClicked(Video video) {
		Single.selectedVideo = video;
		this.getFooterFragement().setButtonClickEvent(null, Footer.TYPE_FRAGMENT_MEDIA_PLAYER);
	}

	@Background
	void getYoutubeInBackground() {
		List<Video> videoList = searchInterface.search(getContext());
		// v1.0
		update(videoList);
		// v2.0
//		youtubeListItemClicked(videoList.get(0));
	}

	@UiThread
	void update(List<Video> videoList) {
		adapter.updateVideoList(videoList);
		gridView.setAdapter(adapter);
	}

	@Override
	public void initSingle() {

	}
}
