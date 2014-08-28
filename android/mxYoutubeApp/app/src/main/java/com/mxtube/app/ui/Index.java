package com.mxtube.app.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.api.Search;
import com.mxtube.app.AppContext;
import com.mxtube.app.R;
import org.androidannotations.annotations.*;

import java.io.InputStream;

@EActivity(R.layout.activity_main)
public class Index extends SherlockFragmentActivity {

	@App
	public AppContext appContext;

	@FragmentById(R.id.fragment_footer_tab)
    Footer fragmentFooterTab;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@AfterInject
	public void calledAfterInjection() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	@AfterViews
	public void calledAfterViewInjection() {
		search();
	}

	@UiThread
	public void search() {
		InputStream in = this.getResources().openRawResource(R.raw.youtube);
		Search.searchByQuery(in);
	}

}
