package com.mxtube.app.ui.single;

import android.os.Handler;
import android.os.Looper;

import com.google.api.services.youtube.model.Video;
import com.layer.business.youtube.impl.SearchImplementation;
import com.mxtube.app.ui.widget.GridViewSingle;

import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

public class HomeSingle extends GridViewSingle {

	SearchImplementation searchInterface = new SearchImplementation();

	@Override
	protected void calledAfterInjection() {
		this.doGetYoutubeInBackground();
	}

	@Override
	protected void calledAfterViewInjection() {

	}

    @Override
    protected int getGridViewColumns() {
        return 4;
    }

    private Handler handler_ = new Handler(Looper.getMainLooper());

	public void runUpdate(final List<Video> videoList) {
		handler_.post(new Runnable() {

			@Override
			public void run() {
				update(videoList);
			}

		});
	}

	public void doGetYoutubeInBackground() {
		BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {

            @Override
            public void execute() {
                try {
                    getYoutubeInBackground();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        });
	}

	void getYoutubeInBackground() {
		List<Video> videoList = searchInterface.search(getContext());
		// v1.0
//		runUpdate(videoList);
		// v2.0
		youtubeListItemClicked(videoList.get(0));
	}

	void update(List<Video> videoList) {
		adapter.updateVideoList(videoList);
		gridView.setAdapter(adapter);
		this.restoreInstanceState();
	}

	@Override
	public void initSingle() {
		this.setTitle("Subscriptions");
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
