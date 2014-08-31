package com.keyes.youtube.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import com.keyes.youtube.*;
import com.keyes.youtube.beans.FileId;
import com.keyes.youtube.beans.PlaylistId;
import com.keyes.youtube.beans.VideoId;
import com.keyes.youtube.beans.YouTubeId;
import com.keyes.youtube.ui.OpenYouTubePlayerActivity;
import com.keyes.youtube.utils.YouTubeUtility;

/**
 * Task to figure out details by calling out to YouTube GData API.  We only use public methods that
 * don't require authentication.
 */
public class QueryYouTubeTask extends AsyncTask<YouTubeId, ProgressUpdateInfo, Uri> {

    private OpenYouTubePlayerActivity openYouTubePlayerActivity;
    private boolean mShowedError = false;

    public QueryYouTubeTask(OpenYouTubePlayerActivity openYouTubePlayerActivity) {
        this.openYouTubePlayerActivity = openYouTubePlayerActivity;
    }

    @Override
    protected Uri doInBackground(YouTubeId... pParams) {
        if (pParams[0] instanceof FileId) {
            return Uri.parse(pParams[0].getId());
        } else {
            String lUriStr = null;
            String lYouTubeFmtQuality = "17";   // 3gpp medium quality, which should be fast enough to view over EDGE connection
            String lYouTubeVideoId = null;

            if (isCancelled())
                return null;

            try {

                publishProgress(new ProgressUpdateInfo(openYouTubePlayerActivity.mMsgDetect));

                WifiManager lWifiManager = (WifiManager) openYouTubePlayerActivity.getSystemService(Context.WIFI_SERVICE);
                TelephonyManager lTelephonyManager = (TelephonyManager) openYouTubePlayerActivity.getSystemService(Context.TELEPHONY_SERVICE);

                ////////////////////////////
                // if we have a fast connection (wifi or 3g), then we'll get a high quality YouTube video
                if ((lWifiManager.isWifiEnabled() && lWifiManager.getConnectionInfo() != null && lWifiManager.getConnectionInfo().getIpAddress() != 0) ||
                        ((lTelephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS ||

                   /* icky... using literals to make backwards compatible with 1.5 and 1.6 */
                                lTelephonyManager.getNetworkType() == 9 /*HSUPA*/ ||
                                lTelephonyManager.getNetworkType() == 10 /*HSPA*/ ||
                                lTelephonyManager.getNetworkType() == 8 /*HSDPA*/ ||
                                lTelephonyManager.getNetworkType() == 5 /*EVDO_0*/ ||
                                lTelephonyManager.getNetworkType() == 6 /*EVDO A*/)

                                && lTelephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED)
                        ) {
                    lYouTubeFmtQuality = "18";
                }


                ///////////////////////////////////
                // if the intent is to show a playlist, get the latest video id from the playlist, otherwise the video
                // id was explicitly declared.
                if (pParams[0] instanceof PlaylistId) {
                    publishProgress(new ProgressUpdateInfo(openYouTubePlayerActivity.mMsgPlaylist));
                    lYouTubeVideoId = YouTubeUtility.queryLatestPlaylistVideo((PlaylistId) pParams[0]);
                } else if (pParams[0] instanceof VideoId) {
                    lYouTubeVideoId = pParams[0].getId();
                }

                openYouTubePlayerActivity.mVideoId = lYouTubeVideoId;

                publishProgress(new ProgressUpdateInfo(openYouTubePlayerActivity.mMsgToken));

                if (isCancelled())
                    return null;

                ////////////////////////////////////
                // calculate the actual URL of the video, encoded with proper YouTube token
                lUriStr = YouTubeUtility.calculateYouTubeUrl(lYouTubeFmtQuality, true, lYouTubeVideoId);

                if (isCancelled())
                    return null;

                if (lYouTubeFmtQuality.equals("17")) {
                    publishProgress(new ProgressUpdateInfo(openYouTubePlayerActivity.mMsgLowBand));
                } else {
                    publishProgress(new ProgressUpdateInfo(openYouTubePlayerActivity.mMsgHiBand));
                }

            } catch (Exception e) {
                Log.e(this.getClass().getSimpleName(), "Error occurred while retrieving information from YouTube.", e);
            }

            if (lUriStr != null) {
                return Uri.parse(lUriStr);
            } else {
                return null;
            }
        }
    }


    @Override
    protected void onPostExecute(Uri pResult) {
        super.onPostExecute(pResult);

        try {
            if (isCancelled())
                return;

            if (pResult == null) {
                throw new RuntimeException("Invalid NULL Url.");
            }

            openYouTubePlayerActivity.mVideoView.setVideoURI(pResult);

            if (isCancelled())
                return;

            // TODO:  add listeners for finish of video
            openYouTubePlayerActivity.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer pMp) {
                    if (isCancelled())
                        return;
                    openYouTubePlayerActivity.finish();
                }

            });

            if (isCancelled())
                return;

            final MediaController lMediaController = new MediaController(openYouTubePlayerActivity);
            openYouTubePlayerActivity.mVideoView.setMediaController(lMediaController);

            Bundle bundle = openYouTubePlayerActivity.getIntent().getExtras();

            boolean showControllerOnStartup = false;

            if (!(bundle == null))
                showControllerOnStartup = bundle.getBoolean("show_controller_on_startup", false);

            if (showControllerOnStartup) lMediaController.show(0);

            //mVideoView.setKeepScreenOn(true);
            openYouTubePlayerActivity.mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer pMp) {
                    if (isCancelled())
                        return;
                    openYouTubePlayerActivity.mProgressBar.setVisibility(View.GONE);
                    openYouTubePlayerActivity.mProgressMessage.setVisibility(View.GONE);
                }

            });

            if (isCancelled())
                return;

            openYouTubePlayerActivity.mVideoView.requestFocus();
            openYouTubePlayerActivity.mVideoView.start();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error playing video!", e);

            if (!mShowedError) {
                showErrorAlert();
            }
        }
    }

    private void showErrorAlert() {

        try {
            AlertDialog.Builder lBuilder = new AlertDialog.Builder(openYouTubePlayerActivity);
            lBuilder.setTitle(openYouTubePlayerActivity.mMsgErrorTitle);
            lBuilder.setCancelable(false);
            lBuilder.setMessage(openYouTubePlayerActivity.mMsgError);

            lBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface pDialog, int pWhich) {
                    openYouTubePlayerActivity.finish();
                }

            });

            AlertDialog lDialog = lBuilder.create();
            lDialog.show();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Problem showing error dialog.", e);
        }
    }

    @Override
    protected void onProgressUpdate(ProgressUpdateInfo... pValues) {
        super.onProgressUpdate(pValues);

        openYouTubePlayerActivity.updateProgress(pValues[0].mMsg);
    }


}
