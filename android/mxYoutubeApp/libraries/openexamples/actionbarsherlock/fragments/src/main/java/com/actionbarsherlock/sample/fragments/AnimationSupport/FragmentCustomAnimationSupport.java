/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionbarsherlock.sample.fragments.AnimationSupport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.fragments.R;
import com.actionbarsherlock.sample.fragments.SampleList;

public class FragmentCustomAnimationSupport extends SherlockFragmentActivity {
	int mStackLevel = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(SampleList.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_stack);

		// Watch for button clicks.
		Button button = (Button) findViewById(R.id.new_fragment);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addFragmentToStack();
			}
		});

		if (savedInstanceState == null) {
			// Do first time initialization -- add initial fragment.
			Fragment newFragment = CountingFragment.newInstance(mStackLevel);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.simple_fragment, newFragment).commit();
		} else {
			mStackLevel = savedInstanceState.getInt("level");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("level", mStackLevel);
	}

	void addFragmentToStack() {
		mStackLevel++;

		// Instantiate a new fragment.
		Fragment newFragment = CountingFragment.newInstance(mStackLevel);

		// Add the fragment to the activity, pushing this transaction
		// on to the back stack.
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit,
				R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
		ft.replace(R.id.simple_fragment, newFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

}
