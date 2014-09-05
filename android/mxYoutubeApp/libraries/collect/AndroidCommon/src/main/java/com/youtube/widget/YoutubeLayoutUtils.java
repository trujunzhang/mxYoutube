package com.youtube.widget;

public class YoutubeLayoutUtils {

	public static int getVideoViewHeight(int widthMeasureSpec, int defaultWidth, int defaultHeight) {
		int resizedHeightMeasureSpec = (defaultHeight * widthMeasureSpec) / defaultWidth;
		return resizedHeightMeasureSpec;
	}
}
