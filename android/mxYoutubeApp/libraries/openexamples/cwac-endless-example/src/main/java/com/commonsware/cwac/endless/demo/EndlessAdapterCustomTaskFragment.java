package com.commonsware.cwac.endless.demo;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import com.commonsware.cwac.endless.EndlessAdapter;

/**
 * This example makes use of EndlessAdapter's
 * setRunInBackground feature.
 * 
 * Calling setRunInBackground(false) allows you to launch
 * your own AsyncTask with a listener callback, rather than
 * using the built in cacheInBackground functionality.
 * 
 * This is useful if you have existing AsyncTask(s) written
 * to fetch data in a background thread, and don't want
 * EndlessAdapter launching that in yet another background
 * thread.
 */
public class EndlessAdapterCustomTaskFragment extends ListFragment {

  DemoAdapter adapter=null;
  ArrayList<Integer> items=null;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    setRetainInstance(true);

    if (adapter == null) {
      items=new ArrayList<Integer>();

      for (int i=0; i < 25; i++) {
        items.add(i);
      }

      adapter=new DemoAdapter(items);
      adapter.setRunInBackground(false); // Tell the adapter
                                         // we will handle
                                         // starting the
                                         // background task
    }

    setListAdapter(adapter);
  }

  class DemoAdapter extends EndlessAdapter implements
      IItemsReadyListener {
    private RotateAnimation rotate=null;

    DemoAdapter(ArrayList<Integer> list) {
      super(new ArrayAdapter<Integer>(getActivity(), R.layout.row,
                                      android.R.id.text1, list));

      rotate=
          new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                              0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
      rotate.setDuration(600);
      rotate.setRepeatMode(Animation.RESTART);
      rotate.setRepeatCount(Animation.INFINITE);
    }

    @Override
    protected View getPendingView(ViewGroup parent) {
      View row=
          getActivity().getLayoutInflater().inflate(R.layout.row, null);

      View child=row.findViewById(android.R.id.text1);

      child.setVisibility(View.GONE);

      child=row.findViewById(R.id.throbber);
      child.setVisibility(View.VISIBLE);
      child.startAnimation(rotate);

      return(row);
    }

    @Override
    protected boolean cacheInBackground() throws Exception {
      new FetchDataTask(this, items.size()).execute();
      
      return(items.size()<75);
    }

    @Override
    public void onItemsReady(ArrayList<Integer> data) {
      items.addAll(data);
      adapter.onDataReady(); // Tell the EndlessAdapter to
                             // remove it's pending
                             // view and call
                             // notifyDataSetChanged()
    }

    @Override
    protected void appendCachedData() {
    }
  }

  interface IItemsReadyListener {
    public void onItemsReady(ArrayList<Integer> data);
  }

  class FetchDataTask extends AsyncTask<Void, Void, ArrayList<Integer>> {
    IItemsReadyListener listener;

    /*
     * The point from where to start counting. In a real
     * life scenario this could be a pagination number
     */
    int startPoint;

    protected FetchDataTask(IItemsReadyListener listener, int startPoint) {
      this.listener=listener;
      this.startPoint=startPoint;
    }

    @Override
    protected ArrayList<Integer> doInBackground(Void... params) {
      ArrayList<Integer> result=new ArrayList<Integer>();

      SystemClock.sleep(3000); // pretend to do work
      for (int i=startPoint; i < startPoint + 25; i++) {
        result.add(i);
      }

      return(result);
    }

    @Override
    protected void onPostExecute(ArrayList<Integer> result) {
      listener.onItemsReady(result);
    }
  }
}
