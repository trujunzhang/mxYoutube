package com.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.xinma.common.R;

public class MediaPlayerLinearLayout extends LinearLayout {

	public static final int DEF_WIDTH = 480;
	public static final int DEF_HEIGHT = 320;

	private int defaultWidth = DEF_WIDTH;
	private int defaultHeight = DEF_HEIGHT;

	public MediaPlayerLinearLayout(Context context) {
		super(context);
	}

	public MediaPlayerLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		loadStateFromAttrs(attrs);
	}

	public MediaPlayerLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		loadStateFromAttrs(attrs);
	}

	private void loadStateFromAttrs(AttributeSet attributeSet) {
		if (attributeSet == null) {
			return; // quick exit
		}

		TypedArray a = null;
		try {
			a = getContext().obtainStyledAttributes(attributeSet, R.styleable.SquareImageView);
			defaultWidth = a.getInt(R.styleable.SquareImageView_default_width, DEF_WIDTH);
			defaultHeight = a.getInt(R.styleable.SquareImageView_default_height, DEF_HEIGHT);
		} finally {
			if (a != null) {
				a.recycle(); // ensure this is always called
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int resizedHeightMeasureSpec = (defaultHeight * widthMeasureSpec) / defaultWidth;
		// super.onMeasure(widthMeasureSpec, resizedHeightMeasureSpec);

		setMeasuredDimension(widthMeasureSpec, resizedHeightMeasureSpec);
	}

}