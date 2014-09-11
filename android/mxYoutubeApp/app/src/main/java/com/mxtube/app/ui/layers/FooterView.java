package com.mxtube.app.ui.layers;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.mxtube.app.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.footer_view)
public class FooterView extends LinearLayout {

	private Context mContext;

	@ViewById(R.id.main_footbar_home)
	RadioButton mainFootbarHome;
	@ViewById(R.id.main_footbar_search)
	RadioButton mainFootbarSearch;
	@ViewById(R.id.main_footbar_histrory)
	RadioButton mainFootbarHistrory;
	@ViewById(R.id.main_footbar_cache)
	RadioButton mainFootbarCache;
	@ViewById(R.id.main_footbar_more)
	RadioButton mainFootbarMore;

	@Click(R.id.main_footbar_home)
	void mainFootbarHomeOnClick(View view) {

	}

	@Click(R.id.main_footbar_search)
	void mainFootbarSearchOnClick(View view) {

	}

	@Click(R.id.main_footbar_histrory)
	void mainFootbarHistroryOnClick(View view) {

	}

	@Click(R.id.main_footbar_cache)
	void mainFootbarCacheOnClick(View view) {

	}

	@Click(R.id.main_footbar_more)
	void mainFootbarMoreOnClick(View view) {

	}

	public FooterView(Context context) {
		super(context);
	}

	public void bind(Context context) {
		int x = 0;// debug
	}

}
