package com.common.widget;

import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.utils.StringUtils;

/**
 * Created by djzhang on 6/8/13.
 */
public class TextView_Utils {
	/**
	 * template
	 */
	// new TextView_Utils((TextView)
	// findViewById(R.id.)).setText().setVisibility().setTypeface(GlobalFontFaceUtils.getCustomFont(this));

	private TextView textView;
	private LinearLayout linearLayout;

	public TextView_Utils(View view, int viewId) {
		this.textView = (TextView) view.findViewById(viewId);
	}

	public TextView_Utils(View view, int viewId, int linearId) {
		this.textView = (TextView) view.findViewById(viewId);
		this.linearLayout = (LinearLayout) view.findViewById(linearId);
	}

	public TextView_Utils(TextView textView) {
		this.textView = textView;
	}

	public TextView_Utils setText(String text) {
		this.textView.setText(text);
		return this;
	}

	public TextView_Utils setTextSize(Float textSize) {
		this.textView.setTextSize(textSize);
		return this;
	}

	public TextView_Utils setVisibility(int visibility) {
		this.textView.setVisibility(visibility);
		return this;
	}

	/**
	 * TextView为隐藏时，父布局也一起隐藏
	 * 
	 * @param textArray
	 * @return
	 */
	public TextView_Utils setVisibility(String... textArray) {
		// 父布局是否需要隐藏
		boolean isVisibilty = false;
		for (String text : textArray) {
			if (StringUtils.isEmpty(text) == false) {
				isVisibilty = true;
			}
		}
		if (isVisibilty == false) {
			if (linearLayout != null) {
				linearLayout.setVisibility(View.GONE);
			}
		}
		// 目前TextView是否需要隐藏
		int visibility = View.VISIBLE;
		String current = textArray[0];
		if (StringUtils.isEmpty(current)) {
			visibility = View.GONE;
		}

		return setVisibility(visibility);
	}

	public TextView_Utils setTypeface(Typeface typeface) {
		this.textView.setTypeface(typeface);
		return this;
	}

	public TextView_Utils setOnClickListener(View.OnClickListener listener) {
		this.textView.setOnClickListener(listener);
		return this;
	}

}
