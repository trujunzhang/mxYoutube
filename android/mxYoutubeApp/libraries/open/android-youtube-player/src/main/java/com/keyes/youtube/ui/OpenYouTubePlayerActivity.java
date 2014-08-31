package com.keyes.youtube.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.LinearLayout.LayoutParams;
import com.keyes.youtube.beans.FileId;
import com.keyes.youtube.beans.PlaylistId;
import com.keyes.youtube.beans.VideoId;
import com.keyes.youtube.task.QueryYouTubeTask;
import com.keyes.youtube.beans.YouTubeId;
import com.keyes.youtube.utils.YouTubeUtility;

/**
 * <p>Activity that will play a video from YouTube.  A specific video or the latest video in a YouTube playlist
 * can be specified in the intent used to invoke this activity.  The data of the intent can be set to a
 * specific video by using an Intent data URL of the form:</p>
 * <p/>
 * <pre>
 *     ytv://videoid
 * </pre>
 * <p/>
 * <p>where <pre>videoid</pre> is the ID of the YouTube video to be played.</p>
 * <p/>
 * <p>If the user wishes to play the latest video in a YouTube playlist, the Intent data URL should be of the
 * form:</p>
 * <p/>
 * <pre>
 *     ytpl://playlistid
 * </pre>
 * <p/>
 * <p>where <pre>playlistid</pre> is the ID of the YouTube playlist from which the latest video is to be played.</p>
 * <p/>
 * <p>Code used to invoke this intent should look something like the following:</p>
 * <p/>
 * <pre>
 *      Intent lVideoIntent = new Intent(null, Uri.parse("ytpl://"+YOUTUBE_PLAYLIST_ID), this, OpenYouTubePlayerActivity.class);
 *      startActivity(lVideoIntent);
 * </pre>
 * <p/>
 * <p>There are several messages that are displayed to the user during various phases of the video load process.  If
 * you wish to supply text other than the default english messages (e.g., internationalization, etc.), you can pass
 * the text to be used via the Intent's extended data.  The messages that can be customized include:
 * <p/>
 * <ul>
 * <li>com.keyes.video.msg.init        - activity is initializing.</li>
 * <li>com.keyes.video.msg.detect      - detecting the bandwidth available to download video.</li>
 * <li>com.keyes.video.msg.playlist    - getting latest video from playlist.</li>
 * <li>com.keyes.video.msg.token       - retrieving token from YouTube.</li>
 * <li>com.keyes.video.msg.loband      - buffering low-bandwidth.</li>
 * <li>com.keyes.video.msg.hiband      - buffering hi-bandwidth.</li>
 * <li>com.keyes.video.msg.error.title - dialog title displayed if anything goes wrong.</li>
 * <li>com.keyes.video.msg.error.msg   - message displayed if anything goes wrong.</li>
 * </ul>
 * <p/>
 * <p>For example:</p>
 * <p/>
 * <pre>
 *      Intent lVideoIntent = new Intent(null, Uri.parse("ytpl://"+YOUTUBE_PLAYLIST_ID), this, OpenYouTubePlayerActivity.class);
 *      lVideoIntent.putExtra("com.keyes.video.msg.init", getString("str_video_intro"));
 *      lVideoIntent.putExtra("com.keyes.video.msg.detect", getString("str_video_detecting_bandwidth"));
 *      ...
 *      startActivity(lVideoIntent);
 * </pre>
 *
 * @author David Keyes
 */
public class OpenYouTubePlayerActivity extends Activity {

    public static final String SCHEME_YOUTUBE_VIDEO = "ytv";
    public static final String SCHEME_YOUTUBE_PLAYLIST = "ytpl";
    public static final String SCHEME_FILE = "file";

    protected ProgressBar mProgressBar;
    protected TextView mProgressMessage;
    protected VideoView mVideoView;

    public final static String MSG_INIT = "com.keyes.video.msg.init";
    protected String mMsgInit = "Initializing";

    public final static String MSG_DETECT = "com.keyes.video.msg.detect";
    protected String mMsgDetect = "Detecting Bandwidth";

    public final static String MSG_PLAYLIST = "com.keyes.video.msg.playlist";
    protected String mMsgPlaylist = "Determining Latest Video in YouTube Playlist";

    public final static String MSG_TOKEN = "com.keyes.video.msg.token";
    protected String mMsgToken = "Retrieving YouTube Video Token";

    public final static String MSG_LO_BAND = "com.keyes.video.msg.loband";
    protected String mMsgLowBand = "Buffering Low-bandwidth Video";

    public final static String MSG_HI_BAND = "com.keyes.video.msg.hiband";
    protected String mMsgHiBand = "Buffering High-bandwidth Video";

    public final static String MSG_ERROR_TITLE = "com.keyes.video.msg.error.title";
    protected String mMsgErrorTitle = "Communications Error";

    public final static String MSG_ERROR_MSG = "com.keyes.video.msg.error.msg";
    protected String mMsgError = "An error occurred during the retrieval of the video.  This could be due to network issues or YouTube protocols.  Please try again later.";

    /**
     * Background task on which all of the interaction with YouTube is done
     */
    protected QueryYouTubeTask mQueryYouTubeTask;

    protected String mVideoId = null;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // create the layout of the view
        setupView();

        // determine the messages to be displayed as the view loads the video
        extractMessages();

        // set the flag to keep the screen ON so that the video can play without the screen being turned off
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mProgressBar.bringToFront();
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressMessage.setText(mMsgInit);

        // extract the playlist or video id from the intent that started this video

        Uri lVideoIdUri = this.getIntent().getData();

        if (lVideoIdUri == null) {
            Log.i(this.getClass().getSimpleName(), "No video ID was specified in the intent.  Closing video activity.");
            finish();
        }
        String lVideoSchemeStr = lVideoIdUri.getScheme();
        String lVideoIdStr = lVideoIdUri.getEncodedSchemeSpecificPart();
        if (lVideoIdStr == null) {
            Log.i(this.getClass().getSimpleName(), "No video ID was specified in the intent.  Closing video activity.");
            finish();
        }
        if (lVideoIdStr.startsWith("//")) {
            if (lVideoIdStr.length() > 2) {
                lVideoIdStr = lVideoIdStr.substring(2);
            } else {
                Log.i(this.getClass().getSimpleName(), "No video ID was specified in the intent.  Closing video activity.");
                finish();
            }
        }

        ///////////////////
        // extract either a video id or a playlist id, depending on the uri scheme
        YouTubeId lYouTubeId = null;
        if (lVideoSchemeStr != null && lVideoSchemeStr.equalsIgnoreCase(SCHEME_YOUTUBE_PLAYLIST)) {
            lYouTubeId = new PlaylistId(lVideoIdStr);
        } else if (lVideoSchemeStr != null && lVideoSchemeStr.equalsIgnoreCase(SCHEME_YOUTUBE_VIDEO)) {
            lYouTubeId = new VideoId(lVideoIdStr);
        } else if (lVideoSchemeStr != null && lVideoSchemeStr.equalsIgnoreCase(SCHEME_FILE)) {
            lYouTubeId = new FileId(lVideoIdStr);
        }

        if (lYouTubeId == null) {
            Log.i(this.getClass().getSimpleName(), "Unable to extract video ID from the intent.  Closing video activity.");
            finish();
        }

        mQueryYouTubeTask = (QueryYouTubeTask) new QueryYouTubeTask(this).execute(lYouTubeId);
    }

    /**
     * Determine the messages to display during video load and initialization.
     */
    protected void extractMessages() {
        Intent lInvokingIntent = getIntent();
        String lMsgInit = lInvokingIntent.getStringExtra(MSG_INIT);
        if (lMsgInit != null) {
            mMsgInit = lMsgInit;
        }
        String lMsgDetect = lInvokingIntent.getStringExtra(MSG_DETECT);
        if (lMsgDetect != null) {
            mMsgDetect = lMsgDetect;
        }
        String lMsgPlaylist = lInvokingIntent.getStringExtra(MSG_PLAYLIST);
        if (lMsgPlaylist != null) {
            mMsgPlaylist = lMsgPlaylist;
        }
        String lMsgToken = lInvokingIntent.getStringExtra(MSG_TOKEN);
        if (lMsgToken != null) {
            mMsgToken = lMsgToken;
        }
        String lMsgLoBand = lInvokingIntent.getStringExtra(MSG_LO_BAND);
        if (lMsgLoBand != null) {
            mMsgLowBand = lMsgLoBand;
        }
        String lMsgHiBand = lInvokingIntent.getStringExtra(MSG_HI_BAND);
        if (lMsgHiBand != null) {
            mMsgHiBand = lMsgHiBand;
        }
        String lMsgErrTitle = lInvokingIntent.getStringExtra(MSG_ERROR_TITLE);
        if (lMsgErrTitle != null) {
            mMsgErrorTitle = lMsgErrTitle;
        }
        String lMsgErrMsg = lInvokingIntent.getStringExtra(MSG_ERROR_MSG);
        if (lMsgErrMsg != null) {
            mMsgError = lMsgErrMsg;
        }
    }

    /**
     * Create the view in which the video will be rendered.
     */
    protected void setupView() {
        LinearLayout lLinLayout = new LinearLayout(this);
        lLinLayout.setId(1);
        lLinLayout.setOrientation(LinearLayout.VERTICAL);
        lLinLayout.setGravity(Gravity.CENTER);
        lLinLayout.setBackgroundColor(Color.BLACK);

        LayoutParams lLinLayoutParms = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        lLinLayout.setLayoutParams(lLinLayoutParms);

        this.setContentView(lLinLayout);


        RelativeLayout lRelLayout = new RelativeLayout(this);
        lRelLayout.setId(2);
        lRelLayout.setGravity(Gravity.CENTER);
        lRelLayout.setBackgroundColor(Color.BLACK);
        android.widget.RelativeLayout.LayoutParams lRelLayoutParms = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lRelLayout.setLayoutParams(lRelLayoutParms);
        lLinLayout.addView(lRelLayout);

        mVideoView = new VideoView(this);
        mVideoView.setId(3);
        android.widget.RelativeLayout.LayoutParams lVidViewLayoutParams = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lVidViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mVideoView.setLayoutParams(lVidViewLayoutParams);
        lRelLayout.addView(mVideoView);

        mProgressBar = new ProgressBar(this);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setEnabled(true);
        mProgressBar.setId(4);
        android.widget.RelativeLayout.LayoutParams lProgressBarLayoutParms = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lProgressBarLayoutParms.addRule(RelativeLayout.CENTER_IN_PARENT);
        mProgressBar.setLayoutParams(lProgressBarLayoutParms);
        lRelLayout.addView(mProgressBar);

        mProgressMessage = new TextView(this);
        mProgressMessage.setId(5);
        android.widget.RelativeLayout.LayoutParams lProgressMsgLayoutParms = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lProgressMsgLayoutParms.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lProgressMsgLayoutParms.addRule(RelativeLayout.BELOW, 4);
        mProgressMessage.setLayoutParams(lProgressMsgLayoutParms);
        mProgressMessage.setTextColor(Color.LTGRAY);
        mProgressMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mProgressMessage.setText("...");
        lRelLayout.addView(mProgressMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        YouTubeUtility.markVideoAsViewed(this, mVideoId);

        if (mQueryYouTubeTask != null) {
            mQueryYouTubeTask.cancel(true);
        }

        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }

        // clear the flag that keeps the screen ON
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.mQueryYouTubeTask = null;
        this.mVideoView = null;

    }

    public void updateProgress(String pProgressMsg) {
        try {
            mProgressMessage.setText(pProgressMsg);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error updating video status!", e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}