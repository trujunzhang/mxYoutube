package com.keyes.youtube.beans;

public class YoutubeTaskInfo {

	public final static String MSG_INIT = "com.keyes.video.msg.init";
	public String mMsgInit = "Initializing";

	public final static String MSG_DETECT = "com.keyes.video.msg.detect";
	public String mMsgDetect = "Detecting Bandwidth";

	public final static String MSG_PLAYLIST = "com.keyes.video.msg.playlist";
	public String mMsgPlaylist = "Determining Latest Video in YouTube Playlist";

	public final static String MSG_TOKEN = "com.keyes.video.msg.token";
	public String mMsgToken = "Retrieving YouTube Video Token";

	public final static String MSG_LO_BAND = "com.keyes.video.msg.loband";
	public String mMsgLowBand = "Buffering Low-bandwidth Video";

	public final static String MSG_HI_BAND = "com.keyes.video.msg.hiband";
	public String mMsgHiBand = "Buffering High-bandwidth Video";

	public final static String MSG_ERROR_TITLE = "com.keyes.video.msg.error.title";
	public String mMsgErrorTitle = "Communications Error";

	public final static String MSG_ERROR_MSG = "com.keyes.video.msg.error.msg";
	public String mMsgError = "An error occurred during the retrieval of the video.  This could be due to network issues or YouTube protocols.  Please try again later.";

}
