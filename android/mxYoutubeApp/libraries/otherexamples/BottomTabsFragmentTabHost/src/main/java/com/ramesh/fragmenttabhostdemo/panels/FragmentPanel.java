package com.ramesh.fragmenttabhostdemo.panels;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.ramesh.fragmenttabhostdemo.Fragment1;
import com.ramesh.fragmenttabhostdemo.Fragment2;
import com.ramesh.fragmenttabhostdemo.R;
import com.ramesh.fragmenttabhostdemo.panels.views.TabLinearLayout;
import com.ramesh.fragmenttabhostdemo.panels.views.TabLinearLayout_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_panel)
public class FragmentPanel extends SherlockFragment {

	@ViewById(R.id.left_linear_layout)
	android.widget.LinearLayout leftLinearLayout;

	@AfterViews
	protected void calledAfterViewInjection() {
		TabLinearLayout tabLinearLayout = TabLinearLayout_.build(this.getSherlockActivity());
		tabLinearLayout.bind(this.getSherlockActivity(), this.getChildFragmentManager());
		this.leftLinearLayout.addView(tabLinearLayout);
	}

}
