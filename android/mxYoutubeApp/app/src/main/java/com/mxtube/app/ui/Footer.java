package com.mxtube.app.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import com.actionbarsherlock.app.SherlockFragment;
import com.mxtube.app.Index;
import com.mxtube.app.R;
import com.mxtube.app.ui.single.Home_;
import com.mxtube.app.ui.single.Single;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.main_footer)
public class Footer extends SherlockFragment {
	private int mCurSel;
	private RadioButton lastRadioButton;

	private Single currentFragment;

	@AfterViews
	protected void calledAfterViewInjection() {
		this.mCurSel = -1;
		setButtonClickEvent(null, 0);
	}

	void setButtonClickEvent(View view, int pos) {
		setTabFragment(pos);
		setCurPoint(view, pos);
	}

	private void setTabFragment(int pos) {
		// Fragments have access to their parent Activity's FragmentManager. You can
		// obtain the FragmentManager like this.
		FragmentManager fm = getFragmentManager();

		if (fm != null) {
			if (pos != this.mCurSel) {

				setFragment(pos, fm);
				initFragment(pos, fm);
				this.mCurSel = pos;
			}
		}
	}

	private void initFragment(int pos, FragmentManager fm) {
		this.currentFragment.initSingle();
	}

	private void setFragment(int pos, FragmentManager fm) {
		// Perform the FragmentTransaction to load in the list tab content.
		// Using FragmentTransaction#replace will destroy any Fragments
		// currently inside R.id.fragment_content and add the new Fragment
		// in its place.
		FragmentTransaction ft = fm.beginTransaction();
		currentFragment = getFragment(pos);
		ft.replace(R.id.fragment_content, currentFragment);
		ft.commit();
	}

	private Single getFragment(int pos) {
		Single fragment = null;
		switch (pos) {
		case 0:// Home
			fragment = new Home_();
			break;
		case 1:// Search
				// fragment = new QuestionMenuFragment_();
			break;
		case 2:// Recent
				// fragment = new TweetMenuFragment_();
			break;
		case 3:// Local
			break;
		}

		return fragment;
	}

	private void setCurPoint(View view, int index) {
		// RadioButton newButton = (RadioButton) view;

		if (lastRadioButton != null) {
			lastRadioButton.setChecked(false);
		}
		// newButton.setChecked(true);
		Index activity = (Index) getSherlockActivity();
//		activity.setTitle(index);
		mCurSel = index;

		// lastRadioButton = newButton;
	}

}
