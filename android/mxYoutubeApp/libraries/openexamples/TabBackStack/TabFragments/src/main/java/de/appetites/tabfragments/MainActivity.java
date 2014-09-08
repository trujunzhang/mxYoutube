package de.appetites.tabfragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.appetites.tabbackstack.util.FragmentBackStack;
import de.appetites.tabbackstack.TabBackStackActivity;
import de.appetites.tabfragments.fragments.DetailsFragment;
import de.appetites.tabfragments.fragments.ListInfoFragment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class MainActivity extends TabBackStackActivity {

	private static final String TAG = "TabFragments";
	protected ActionBar mActionBar;
	SparseArray<FragmentBackStack> mTabBackStacks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActionBar = getActionBar();
		// create tabs
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar.newTab().setText("Tab 1").setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("Tab 2").setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("Tab 3").setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("Tab 4").setTabListener(this));

		findViewById(R.id.add_button).setOnClickListener(addButtonListener);
	}

	@Override
	public int getContainerId() {
		return R.id.container;
	}

	private HashMap<Integer, Fragment> list = new LinkedHashMap<Integer, Fragment>();

	@Override
	protected Fragment initTab(int position) {
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putString(DummySectionFragment.ARG_SECTION_NUMBER, UUID.randomUUID().toString());
		fragment.setArguments(args);

		fragment = new ListInfoFragment();
		return fragment;
	}

	private View.OnClickListener addButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Fragment fragment = new DetailsFragment();
			push(fragment);
		}
	};

	public static class DummySectionFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "placeholder_text";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			LinearLayout layout = new LinearLayout(getActivity());
			layout.setOrientation(LinearLayout.VERTICAL);

			// text view with info about current fragment
			// displays : {tabposition}-{size_of_current_tabstack}
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(getArguments().getString(ARG_SECTION_NUMBER));
			layout.addView(textView);

			// creat button for pushing new dummy fragment
			Button button = new Button(getActivity());
			button.setText("Push");
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Fragment fragment = new DummySectionFragment();
					Bundle args = new Bundle();
					args.putString(DummySectionFragment.ARG_SECTION_NUMBER, UUID.randomUUID().toString());
					fragment.setArguments(args);
					((MainActivity) getActivity()).push(fragment);
				}
			});
			layout.addView(button);

			return layout;
		}
	}
}
