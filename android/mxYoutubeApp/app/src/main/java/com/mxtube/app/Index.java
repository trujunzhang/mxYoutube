package com.mxtube.app;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.common.utils.UIHelper;
import com.mxtube.app.ui.Footer;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class Index extends SherlockFragmentActivity {

	@App
	public AppContext appContext;

	@FragmentById(R.id.fragment_footer_tab)
	public Footer fragmentFooterTab;

	@AfterInject
	public void calledAfterInjection() {
		appContext.index = this;
	}

	@AfterViews
	public void calledAfterViewInjection() {
	}

	public void onBackPressed() {
		DialogInterface.OnClickListener sureListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 退出
				// AppManager.getAppManager().AppExit(cont);
				finish();
				System.exit(0);
			}
		};
		UIHelper.Exit(this, sureListener);

	}

}
