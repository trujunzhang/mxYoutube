package com.mxtube.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import com.example.api.Search;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.io.InputStream;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@AfterInject
	void calledAfterInjection() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	@AfterViews
	protected void calledAfterViewInjection() {
		search();
	}

	@UiThread
	public void search() {
		InputStream in = this.getResources().openRawResource(R.raw.youtube);
		Search.searchByQuery(in);
	}

}
