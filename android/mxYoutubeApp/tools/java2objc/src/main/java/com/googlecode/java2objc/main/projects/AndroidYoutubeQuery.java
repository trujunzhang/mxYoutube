package com.googlecode.java2objc.main.projects;

public class AndroidYoutubeQuery {

	// /Volumes/Home/Developing/SketchProjects/mxYoutube/android/mxYoutubeApp/ios/mxYoutubeApp/lib/android-youtube-query/Pod/Classes/YQVideoInfoTaskCallback.h
	// /Volumes/Home/Developing/SketchProjects/mxYoutube/ios/mxYoutubeApp/lib/android-youtube-query/Pod/Classes/YQVideoInfoTaskCallback.h
	// /Volumes/Home/Developing/SketchProjects/mxYoutube/callbackios/mxYoutubeApp/lib/android-youtube-query/Pod/Classes/YQVideoInfoTaskCallback.h
	// /Volumes/Home/Developing/SketchProjects/mxYoutube/ios/mxYoutubeApp/lib/android-youtube-query/Pod/Classes/callback/YQVideoInfoTaskCallback.h

	public static final String PROJECT_ROOT = "/Volumes/Home/Developing/SketchProjects/mxYoutube/";
	public static final String PACKAGE_ROOT = "android/mxYoutubeApp/libraries/open/android-youtube-query/src/main/java/com/keyes/youtube/";

	public static final String IOS_DIR = "ios/mxYoutubeApp/lib/android-youtube-query/Pod/Classes/";
	public static final String OUTPUTDIR = "--outputdir=";
	public static final String PREFIX_NAME = "--prefix=YQ";

	public static String[] getArgs() {
		String[] dirs = {// convert dir
		"beans",// 0
		// "callback",// 1
		// "ui",// 2
		// "utils",// 3
		};
		String[] files = {// convert files
		"",// default
				"/VideoStream",// test
		};

		String[] args = new String[3];
		int s = 0;
		args[s++] = PROJECT_ROOT + PACKAGE_ROOT + dirs[0] + files[0];
		args[s++] = OUTPUTDIR + PROJECT_ROOT + IOS_DIR + dirs[0] + files[0];
		args[s++] = PREFIX_NAME;
		return args;
	}
}
