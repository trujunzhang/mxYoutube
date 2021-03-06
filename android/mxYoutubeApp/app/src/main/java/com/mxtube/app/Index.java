package com.mxtube.app;

import android.content.DialogInterface;

import com.common.utils.UIHelper;
import com.layer.business.utils.AppConstant;
import com.mxtube.app.ui.layers.FooterView;
import com.mxtube.app.ui.layers.FooterView_;

import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class Index extends IndexFragmentActivity {

	@App
	public AppContext appContext;

	@ViewById(R.id.fragment_footer_tab)
	android.widget.LinearLayout fragmentFooterTab;

	private FooterView footerView;

	@AfterInject
	public void calledAfterInjection() {
		appContext.index = this;
		this.initTabBackStackHelper();
	}

	@AfterViews
	public void calledAfterViewInjection() {
		this.footerView = FooterView_.build(this);
		this.footerView.bind(this);
		this.fragmentFooterTab.addView(this.footerView);

		// Init first screen,and show home fragment
		this.onTabSelected(AppConstant.MAIN_FOOTBAR_HOME);
	}

	public void onBackPressed() {
		if (this.hasSubItemBack() == false) {
			exit();
		}
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
