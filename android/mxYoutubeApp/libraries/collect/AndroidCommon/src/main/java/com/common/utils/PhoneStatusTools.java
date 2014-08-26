package com.common.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import java.io.IOException;

public class PhoneStatusTools {
	public static NetworkInfo getNetWorkInfo(Context paramContext) {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
		if (localConnectivityManager != null)
			return localConnectivityManager.getActiveNetworkInfo();
		return null;
	}

	public static boolean isCallIdle(Context paramContext) {
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext.getSystemService("phone");
		return (localTelephonyManager != null) && (localTelephonyManager.getCallState() == 0);
	}

	public static boolean isRingEqualsZero(Context paramContext) {
		AudioManager localAudioManager = (AudioManager) paramContext.getSystemService("audio");
		return (localAudioManager != null) && (localAudioManager.getStreamVolume(2) == 0);
	}

	public static boolean isRingerSilent(Context paramContext) {
		AudioManager localAudioManager = (AudioManager) paramContext.getSystemService("audio");
		return (localAudioManager != null) && (localAudioManager.getRingerMode() == 0);
	}

	public static boolean isRingerVibrate(Context paramContext) {
		AudioManager localAudioManager = (AudioManager) paramContext.getSystemService("audio");
		return (localAudioManager != null) && (localAudioManager.getRingerMode() == 1);
	}

	public static boolean isWifiEnv(Context paramContext) {
		NetworkInfo localNetworkInfo = getNetWorkInfo(paramContext);
		if (localNetworkInfo != null)
			return localNetworkInfo.getType() == 1;
		return false;
	}

	public static void checkAndPlayRingerVibrate(Context paramContext) {
		if (PhoneStatusTools.isRingerVibrate(paramContext)) {// 开启震动
			PhoneStatusTools.playVibrate(paramContext);
		}
	}

	private static void playVibrate(Context paramContext) {
		// Get instance of Vibrator from current Context
		Vibrator v = (Vibrator) paramContext.getSystemService(Context.VIBRATOR_SERVICE);

		// Vibrate for 300 milliseconds
		v.vibrate(600);
	}

	public static void checkAndPlayDefaultRingtone(Context paramContext) {
		if (PhoneStatusTools.isRingerSilent(paramContext) == false
				&& PhoneStatusTools.isRingEqualsZero(paramContext) == false) {// 非静音
			playDefaultRingtone(paramContext);
		}
	}

	public static void playDefaultRingtone(Context paramContext) {
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(paramContext, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			mp.prepare();
			mp.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*
 * Location: /Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/apktool1.5.2/classes_dex2jar.jar
 * Qualified Name: com.tencent.av.utils.PhoneStatusTools JD-Core Version: 0.6.0
 */