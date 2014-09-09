package com.mxtube.app;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.common.utils.UIHelper;
import com.mxtube.app.ui.Footer;
import com.mxtube.app.ui.views.FooterView;
import com.mxtube.app.ui.views.FooterView_;

import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class Index extends SherlockFragmentActivity {

	@App
	public AppContext appContext;

	@ViewById(R.id.fragment_footer_tab)
	android.widget.LinearLayout fragmentFooterTab;

	private FooterView footerView;

	@AfterInject
	public void calledAfterInjection() {
		appContext.index = this;
	}

	@AfterViews
	public void calledAfterViewInjection() {
		this.footerView = FooterView_.build(this);
        this.footerView.bind(this);
		this.fragmentFooterTab.addView(this.footerView);
	}

	public void onBackPressed() {
		// if (fragmentFooterTab.pressBack() == false) {
		exit();
		// }
	}

	private void exit() {
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
