/***
  Copyright (c) 2008-2009 CommonsWare, LLC
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.cwac.endless.demo;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class EndlessAdapterDemo extends ListActivity {
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		DemoAdapter adapter = (DemoAdapter) getLastNonConfigurationInstance();

		if (adapter == null) {
			ArrayList<Integer> items = new ArrayList<Integer>();

			for (int i = 0; i < 2; i++) {
				items.add(i);
			}

			adapter = new DemoAdapter(this, items);
		} else {
			adapter.startProgressAnimation();
		}

		adapter.startProgressAnimation();

		setListAdapter(adapter);
	}

	@Override
	public Object getLastNonConfigurationInstance() {
		return (getListAdapter());
	}
}
