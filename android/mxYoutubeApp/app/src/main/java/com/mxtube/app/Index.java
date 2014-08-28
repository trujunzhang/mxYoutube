package com.mxtube.app;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mxtube.app.ui.Footer;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class Index extends SherlockFragmentActivity {

	@App
	public AppContext appContext;

	@FragmentById(R.id.fragment_footer_tab)
	Footer fragmentFooterTab;

	@AfterInject
	public void calledAfterInjection() {
		appContext.index = this;
	}

	@AfterViews
	public void calledAfterViewInjection() {
	}

}
