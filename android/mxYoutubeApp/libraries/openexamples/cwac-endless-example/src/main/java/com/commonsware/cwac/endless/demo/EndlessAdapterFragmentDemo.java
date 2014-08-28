/***
  Copyright (c) 2012 CommonsWare, LLC
  
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

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class EndlessAdapterFragmentDemo extends Activity implements
    ActionBar.TabListener {
  private EndlessAdapterFragment simple=null;
  private EndlessAdapterCustomTaskFragment customTask=null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActionBar bar=getActionBar();

    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    bar.addTab(bar.newTab().setText(R.string.simple)
                  .setTabListener(this).setTag(Tabs.TAB_SIMPLE));
    bar.addTab(bar.newTab().setText(R.string.custom)
                  .setTabListener(this).setTag(Tabs.TAB_CUSTOM_TASK));
  }

  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {
    onTabSelected(tab, ft);
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    switch ((Tabs)tab.getTag()) {
      case TAB_SIMPLE:
        showSimple(ft);
        break;
      case TAB_CUSTOM_TASK:
        showCustom(ft);
        break;
    }
  }

  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    // no-op
  }

  private void showSimple(FragmentTransaction ft) {
    if (simple == null) {
      simple=new EndlessAdapterFragment();
    }

    ft.replace(android.R.id.content, simple);
  }

  private void showCustom(FragmentTransaction ft) {
    if (customTask == null) {
      customTask=new EndlessAdapterCustomTaskFragment();
    }

    ft.replace(android.R.id.content, customTask);
  }

  private static enum Tabs {
    TAB_SIMPLE, TAB_CUSTOM_TASK;
  }
}
