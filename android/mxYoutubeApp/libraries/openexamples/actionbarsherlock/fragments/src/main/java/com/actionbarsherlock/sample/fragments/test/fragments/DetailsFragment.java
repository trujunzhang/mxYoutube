package com.actionbarsherlock.sample.fragments.test.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.sample.fragments.R;
import com.actionbarsherlock.sample.fragments.test.FragmentStackSupport;

public class DetailsFragment extends SherlockFragment {

	protected TextView mLabelNested;

	private static final String[] strs = new String[] { "1", "2", "3", "4", "5",//
			"6", "7", "8", "9", "10",//
			"11", "12", "13", "14", "15", };
	private ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_details, container, false);
		mLabelNested = (TextView) fragmentView.findViewById(R.id.info_text);
		setText(String.valueOf(FragmentStackSupport.mStackLevel));

		this.lv = (ListView) fragmentView.findViewById(R.id.list_frame);// 得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs));

		return fragmentView;
	}

	public void setText(String text) {
		this.mLabelNested.setText(text);
	}

}
