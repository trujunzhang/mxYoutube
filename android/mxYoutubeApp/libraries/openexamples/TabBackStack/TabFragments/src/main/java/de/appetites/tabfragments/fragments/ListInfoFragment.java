package de.appetites.tabfragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import de.appetites.tabfragments.R;

public class ListInfoFragment extends SherlockFragment {

	protected TextView mLabelNested;

	private static int step = 1;

	private static final String[] strs = new String[] { "first", "second", "third", "fourth", "fifth",//
			"sixth", "seventh", "eighth", "ninth", "ten",//
			"eleven", "twelve", "thirteen", "fourteen", "fifteen", };
	private ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
		mLabelNested = (TextView) fragmentView.findViewById(R.id.info_text);
		setText(String.valueOf(step++));

		this.lv = (ListView) fragmentView.findViewById(R.id.list_frame);// 得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs));

		return fragmentView;
	}

	public void setText(String text) {
		this.mLabelNested.setText(text);
	}

}
