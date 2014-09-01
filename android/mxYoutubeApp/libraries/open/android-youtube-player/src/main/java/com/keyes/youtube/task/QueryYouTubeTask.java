package com.keyes.youtube.task;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import com.keyes.youtube.beans.FileId;
import com.keyes.youtube.beans.PlaylistId;
import com.keyes.youtube.beans.VideoId;
import com.keyes.youtube.beans.YouTubeId;
import com.keyes.youtube.ui.YouTubePlayerHelper;
import com.keyes.youtube.utils.YouTubeUtility;

/**
 * Task to figure out details by calling out to YouTube GData API. We only use public methods that don't require
 * authentication.
 */
public class QueryYouTubeTask extends AsyncTask<YouTubeId, ProgressUpdateInfo, Uri> {

	private YouTubePlayerHelper playerHelper = new YouTubePlayerHelper();
	private boolean mShowedError = false;
	private Context mContext;

	public QueryYouTubeTask(Context context, YouTubePlayerHelper playerHelper) {
		this.mContext = context;
		this.playerHelper = playerHelper;
	}

	@Override
	protected Uri doInBackground(YouTubeId... pParams) {
		if (pParams[0] instanceof FileId) {
			return Uri.parse(pParams[0].getId());
		} else {
			String lUriStr = null;

			String lYouTubeVideoId = null;

			if (isCancelled())
				return null;

			try {
				publishProgress(new ProgressUpdateInfo(playerHelper.taskInfo.mMsgDetect));

				// /////////////////////////////////
				// if the intent is to show a playlist, get the latest video id from the playlist, otherwise the video
				// id was explicitly declared.
				if (pParams[0] instanceof PlaylistId) {
					publishProgress(new ProgressUpdateInfo(playerHelper.taskInfo.mMsgPlaylist));
					lYouTubeVideoId = YouTubeUtility.queryLatestPlaylistVideo((PlaylistId) pParams[0]);
				} else if (pParams[0] instanceof VideoId) {
					lYouTubeVideoId = pParams[0].getId();
				}

				playerHelper.mVideoId = lYouTubeVideoId;

				publishProgress(new ProgressUpdateInfo(playerHelper.taskInfo.mMsgToken));

				if (isCancelled())
					return null;

				// //////////////////////////////////
				// calculate the actual URL of the video, encoded with proper YouTube token
				lUriStr = YouTubeUtility.calculateYouTubeUrl(playerHelper.taskInfo.lYouTubeFmtQuality, true,
						lYouTubeVideoId);

				if (isCancelled())
					return null;

				if (playerHelper.taskInfo.lYouTubeFmtQuality.equals("17")) {
					publishProgress(new ProgressUpdateInfo(playerHelper.taskInfo.mMsgLowBand));
				} else {
					publishProgress(new ProgressUpdateInfo(playerHelper.taskInfo.mMsgHiBand));
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
			// http%3A%2F%2Fr7---sn-i3b7snl7.googlevideo.com%2Fvideoplayback%3Finitcwndbps%3D210375%26source%3Dyoutube%26expire%3D1409580367%26ipbits%3D0%26key%3Dyt5%26ms%3Dau%26upn%3DG_8WcfW6ibo%26mv%3Dm%26mt%3D1409558662%26fexp%3D902408%252C916600%252C916630%252C918015%252C923345%252C927622%252C931983%252C932404%252C932625%252C934024%252C934030%252C946010%252C946506%252C949501%252C953801%26id%3Do-ACP0TRiYWc7qkIrOsVu0oqPQv08yryrLy80WpqGVpVUu%26signature%3D42EA1C0406FDEEC1E65C0B4FA20558FE2931975B.A47599A424514B7A78D726A5DECCABAFA470B462%26sver%3D3%26sparams%3Did%252Cinitcwndbps%252Cip%252Cipbits%252Citag%252Cmm%252Cms%252Cmv%252Csource%252Cupn%252Cexpire%26itag%3D17%26mm%3D31%26ip%3D121.127.250.133&signature=null
			playerHelper.mVideoView.setVideoURI(pResult);

			if (isCancelled())
				return;

			// TODO: add listeners for finish of video
			playerHelper.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

				public void onCompletion(MediaPlayer pMp) {
					if (isCancelled())
						return;
					// openYouTubePlayerActivity.finish(); // TODO [USED]
				}

			});

			if (isCancelled())
				return;

			final MediaController lMediaController = new MediaController(mContext);
			playerHelper.mVideoView.setMediaController(lMediaController);

			if (playerHelper.taskInfo.showControllerOnStartup)
				lMediaController.show(0);

			// mVideoView.setKeepScreenOn(true);
			playerHelper.mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

				public void onPrepared(MediaPlayer pMp) {
					if (isCancelled())
						return;
					playerHelper.mProgressBar.setVisibility(View.GONE);
					playerHelper.mProgressMessage.setVisibility(View.GONE);
				}

			});

			if (isCancelled())
				return;

			playerHelper.mVideoView.requestFocus();
			playerHelper.mVideoView.start();
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "Error playing video!", e);

			if (!mShowedError) {
				showErrorAlert();
			}
		}
	}

	private void showErrorAlert() {// TODO [USED]
		// try {
		// AlertDialog.Builder lBuilder = new AlertDialog.Builder(openYouTubePlayerActivity);
		// lBuilder.setTitle(playerHelper.taskInfo.mMsgErrorTitle);
		// lBuilder.setCancelable(false);
		// lBuilder.setMessage(playerHelper.taskInfo.mMsgError);
		//
		// lBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface pDialog, int pWhich) {
		// openYouTubePlayerActivity.finish();
		// }
		//
		// });
		//
		// AlertDialog lDialog = lBuilder.create();
		// lDialog.show();
		// } catch (Exception e) {
		// Log.e(this.getClass().getSimpleName(), "Problem showing error dialog.", e);
		// }
	}

	@Override
	protected void onProgressUpdate(ProgressUpdateInfo... pValues) {
		super.onProgressUpdate(pValues);

		playerHelper.updateProgress(pValues[0].mMsg);
	}

}
