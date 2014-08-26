package com.common.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xinma.common.R;

/**
 * 新数据Toast提示控件(带音乐播放)
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-8-30
 */
public class NewDataToast extends Toast {

	// private MediaPlayer mPlayer;
	private boolean isSound;

	public NewDataToast(Context context) {
		this(context, false);
	}

	public NewDataToast(Context context, boolean isSound) {
		super(context);

		this.isSound = isSound;
	}

	@Override
	public void show() {
		super.show();
	}

	/**
	 * 设置是否播放声音
	 */
	public void setIsSound(boolean isSound) {
		this.isSound = isSound;
	}

	/**
	 * 获取控件实例
	 * 
	 * @param context
	 * @param text
	 *            提示消息
	 * @return
	 */
	public static NewDataToast makeText(Context context, CharSequence text) {
		NewDataToast result = new NewDataToast(context, false);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		DisplayMetrics dm = context.getResources().getDisplayMetrics();

		View v = inflate.inflate(R.layout.new_data_toast, null);
		v.setMinimumWidth(dm.widthPixels);// 设置控件最小宽度为手机屏幕宽度

		TextView tv = (TextView) v.findViewById(R.id.new_data_toast_message);
		tv.setText(text);

		result.setView(v);
		result.setDuration(600);
		result.setGravity(Gravity.TOP, 0, (int) (dm.density * 75));

		return result;
	}

}
