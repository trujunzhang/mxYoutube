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
