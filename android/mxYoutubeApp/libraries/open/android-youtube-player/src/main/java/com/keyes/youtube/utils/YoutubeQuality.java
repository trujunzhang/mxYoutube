package com.keyes.youtube.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class YoutubeQuality {

	public static String getYoutubeFmtQuality(Activity activity) {
		String lYouTubeFmtQuality = "17";
		WifiManager lWifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		TelephonyManager lTelephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

		// //////////////////////////
		// if we have a fast connection (wifi or 3g), then we'll get a high quality YouTube video
		if ((lWifiManager.isWifiEnabled() && lWifiManager.getConnectionInfo() != null && lWifiManager.getConnectionInfo().getIpAddress() != 0)
				|| ((lTelephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS ||

				/* icky... using literals to make backwards compatible with 1.5 and 1.6 */
				lTelephonyManager.getNetworkType() == 9 /* HSUPA */
						|| lTelephonyManager.getNetworkType() == 10 /* HSPA */
						|| lTelephonyManager.getNetworkType() == 8 /* HSDPA */
						|| lTelephonyManager.getNetworkType() == 5 /* EVDO_0 */
				|| lTelephonyManager.getNetworkType() == 6)/* EVDO A */

				&& lTelephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED)) {
			// lYouTubeFmtQuality = "18";
		}
		return lYouTubeFmtQuality;
	}
}
