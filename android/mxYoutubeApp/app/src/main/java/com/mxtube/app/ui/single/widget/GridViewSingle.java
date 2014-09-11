package com.mxtube.app.ui.single.widget;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.api.services.youtube.model.Video;
import com.layer.business.utils.AppConstant;
import com.layer.business.youtube.impl.SearchImplementation;
import com.mxtube.app.R;
import com.mxtube.app.adapter.YoutubeListAdapter;
import com.mxtube.app.adapter.YoutubeListAdapter_;
import com.mxtube.app.ui.single.Single;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

public abstract class GridViewSingle extends Single {
	protected abstract void calledAfterInjection();

	protected abstract void calledAfterViewInjection();

	public android.widget.GridView gridView;

	public YoutubeListAdapter adapter;

	private static Parcelable mListInstanceState;

	void youtubeListItemClicked(Video video) {
		Single.selectedVideo = video;
		this.getIndex().push(AppConstant.TYPE_FRAGMENT_MEDIA_PLAYER);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		calledAfterInjection();
		View contentView_ = super.onCreateView(inflater, container, savedInstanceState);
		if (contentView_ == null) {
			contentView_ = inflater.inflate(R.layout.widget_gridview, container, false);
			calledAfterViewInjection();
			this.gridView = (android.widget.GridView) contentView_.findViewById(R.id.gridView);
			onViewChanged(contentView_);
			this.adapter = YoutubeListAdapter_.getInstance_(this.getContext());
		}
		return contentView_;
	}

	public void onViewChanged(View hasViews) {
		gridView = ((GridView) hasViews.findViewById(R.id.gridView));
		{
			AdapterView<?> view = ((AdapterView<?>) hasViews.findViewById(com.mxtube.app.R.id.gridView));
			if (view != null) {
				view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						youtubeListItemClicked(((Video) parent.getAdapter().getItem(position)));
					}

				});
			}
		}
	}

	@Override
	public void saveInstanceState() {
		mListInstanceState = this.gridView.onSaveInstanceState();
	}

	public void restoreInstanceState() {
		if (mListInstanceState != null)
			gridView.onRestoreInstanceState(mListInstanceState);
	}

	@Override
	public void abstract001() {

	}

	@Override
	public void abstract002() {

	}

	@Override
	public void abstract003() {

	}

}
