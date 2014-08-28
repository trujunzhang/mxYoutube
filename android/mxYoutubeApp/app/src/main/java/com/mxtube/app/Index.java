package com.mxtube.app;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.api.Search;
import com.mxtube.app.AppContext;
import com.mxtube.app.R;
import com.mxtube.app.ui.Footer;
import org.androidannotations.annotations.*;

import java.io.InputStream;

@EActivity(R.layout.activity_main)
public class Index extends SherlockFragmentActivity {

	@App
	public AppContext appContext;

	@FragmentById(R.id.fragment_footer_tab)
    Footer fragmentFooterTab;

	@AfterInject
	public void calledAfterInjection() {
	}

	@AfterViews
	public void calledAfterViewInjection() {
        setTitle("Subscriptions");
//		search();
	}

	@UiThread
	public void search() {
		InputStream in = this.getResources().openRawResource(R.raw.youtube);
		Search.searchByQuery(in);
	}

}
