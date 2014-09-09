package com.actionbarsherlock.sample.fragments.test.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.sample.fragments.R;
import com.actionbarsherlock.sample.fragments.test.FragmentStackSupport;

public class ListInfoFragment extends SherlockFragment {

	protected TextView mLabelNested;

	private static final String[] strs = new String[] { "first", "second", "third", "fourth", "fifth",//
			"sixth", "seventh", "eighth", "ninth", "ten",//
			"eleven", "twelve", "thirteen", "fourteen", "fifteen", };
	private ListView lv;
	private ScrollView mScrollView;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
		mLabelNested = (TextView) fragmentView.findViewById(R.id.info_text);
		setText(String.valueOf(FragmentStackSupport.mStackLevel));
		mScrollView = (ScrollView) fragmentView.findViewById(R.id.scroll_view);

		this.lv = (ListView) fragmentView.findViewById(R.id.list_frame);// 得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs));
		if (savedInstanceState != null) {
			int mStackLevel = savedInstanceState.getInt("level");
			this.mScrollView.setScrollY(mStackLevel);
		}

		return fragmentView;
	}

	public void setText(String text) {
		this.mLabelNested.setText(text);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int mStackLevel = this.mScrollView.getScrollY();
		outState.putInt("level", mStackLevel);
	}
}
