package com.commonsware.cwac.endless.demo;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import com.commonsware.cwac.endless.EndlessAdapter;

class DemoAdapter extends EndlessAdapter {
  private RotateAnimation rotate=null;
  private View pendingView=null;
  
  DemoAdapter(Context ctxt, ArrayList<Integer> list) {
    super(new ArrayAdapter<Integer>(ctxt,
                                    R.layout.row,
                                    android.R.id.text1,
                                    list));
    rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                                0.5f, Animation.RELATIVE_TO_SELF,
                                0.5f);
    rotate.setDuration(600);
    rotate.setRepeatMode(Animation.RESTART);
    rotate.setRepeatCount(Animation.INFINITE);
  }
  
  @Override
  protected View getPendingView(ViewGroup parent) {
    View row=LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
    
    pendingView=row.findViewById(android.R.id.text1);
    pendingView.setVisibility(View.GONE);
    pendingView=row.findViewById(R.id.throbber);
    pendingView.setVisibility(View.VISIBLE);
    startProgressAnimation();
    
    return(row);
  }
  
  @Override
  protected boolean cacheInBackground() {
    SystemClock.sleep(10000);       // pretend to do work
    
    return(getWrappedAdapter().getCount()<75);
  }
  
  @Override
  protected void appendCachedData() {
    if (getWrappedAdapter().getCount()<75) {
      @SuppressWarnings("unchecked")
      ArrayAdapter<Integer> a=(ArrayAdapter<Integer>)getWrappedAdapter();
      
      for (int i=0;i<25;i++) { a.add(a.getCount()); }
    }
  }
  
  void startProgressAnimation() {
    if (pendingView!=null) {
      pendingView.startAnimation(rotate);
    }
  }
}