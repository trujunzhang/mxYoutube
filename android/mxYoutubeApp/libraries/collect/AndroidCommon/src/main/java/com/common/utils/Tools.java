package com.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: djzhang Date: 5/6/13 Time: 1:46 PM To change this template use File | Settings |
 * File Templates.
 */
public final class Tools {

	private static final String TAG = Tools.class.getSimpleName();

	public static boolean checkActivity(Context paramContext) {
		List localList = ((ActivityManager) paramContext.getSystemService("activity")).getRunningTasks(10);
		if (localList.size() > 0) {
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext())
				if (((ActivityManager.RunningTaskInfo) localIterator.next()).topActivity.getPackageName().equals(
						"com.myzaker.ZAKER_Phone"))
					return true;
		}
		return false;
	}

	public static int[] getScreenSize(Context paramContext) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) paramContext).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		return new int[] { localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels };
	}

	public static int getScreenWidth(Context paramContext) {
		return getScreenSize(paramContext)[0];
	}

	public static int getScreenHeight(Context paramContext) {
		return getScreenSize(paramContext)[1];
	}

	public static float getScreenTextSize(Context context) {
		int width = getScreenWidth(context);

		if (width <= 480) {
			return 20f;
		}
		return 24f;
	}

	public static void showOrHideInput(Context context) {
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		// 显示或者隐藏输入法
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
