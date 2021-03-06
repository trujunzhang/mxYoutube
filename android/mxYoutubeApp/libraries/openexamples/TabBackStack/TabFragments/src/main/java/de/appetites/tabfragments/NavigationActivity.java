package de.appetites.tabfragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.appetites.tabbackstack.TabBackStackHelper;
import de.appetites.tabbackstack.TabBackStackInterface;
import de.appetites.tabfragments.fragments.DetailsFragment;
import de.appetites.tabfragments.fragments.ListInfoFragment;

@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends SherlockFragmentActivity implements TabBackStackInterface {
	private TabBackStackHelper tabBackStackHelper;

	@Click(R.id.add_button)
	void frame_buttonOnClick(View view) {
		Fragment fragment = new DetailsFragment();
		this.tabBackStackHelper.push(this.getSupportFragmentManager(), fragment, 0);
	}

	@AfterInject
	void calledAfterInjection() {
		this.tabBackStackHelper = new TabBackStackHelper(this);
	}

	@AfterViews
	protected void calledAfterViewInjection() {
		FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		this.tabBackStackHelper.onTabSelected(fragmentTransaction, 0);
		fragmentTransaction.commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// windowManager should not be null
		this.tabBackStackHelper.onCreate(this, savedInstanceState, 1);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		this.tabBackStackHelper.onSaveInstanceState(outState);
	}


	/**
	 * Pops the current fragment of the current tab if back is pressed
	 */
	@Override
	public void onBackPressed() {
		if (!this.tabBackStackHelper.pop(this.getSupportFragmentManager(), 0)) {
			// super.onBackPressed();
		}
	}

	@Override
	public int getContainerId() {
		return R.id.container;
	}

	@Override
	public Fragment initTab(int position) {
		return new ListInfoFragment();
	}
}
