#import "YQVideoInfoTaskCallback.h"
#import "YQYoutubeQuality.h"
#import "YQYoutubeTaskInfo.h"


/**
* <p>
* Activity that will play a video from YouTube. A specific video or the latest video in a YouTube playlist can be
* specified in the intent used to invoke this activity. The data of the intent can be set to a specific video by using
* an Intent data URL of the form:
* </p>
* <p/>
*
* <pre>
* ytv://videoid
* </pre>
* <p/>
* <p>
* where
*
* <pre>
* videoid
* </pre>
*
* is the ID of the YouTube video to be played.
* </p>
* <p/>
* <p>
* If the user wishes to play the latest video in a YouTube playlist, the Intent data URL should be of the form:
* </p>
* <p/>
*
* <pre>
* ytpl://playlistid
* </pre>
* <p/>
* <p>
* where
*
* <pre>
* playlistid
* </pre>
*
* is the ID of the YouTube playlist from which the latest video is to be played.
* </p>
* <p/>
* <p>
* Code used to invoke this intent should look something like the following:
* </p>
* <p/>
*
* <pre>
* Intent lVideoIntent = new Intent(null, Uri.parse(&quot;ytpl://&quot; + YOUTUBE_PLAYLIST_ID), this,
* OpenYouTubePlayerActivity.class);
* startActivity(lVideoIntent);
* </pre>
* <p/>
* <p>
* There are several messages that are displayed to the user during various phases of the video load process. If you
* wish to supply text other than the default english messages (e.g., internationalization, etc.), you can pass the text
* to be used via the Intent's extended data. The messages that can be customized include:
* <p/>
* <ul>
* <li>com.keyes.video.msg.init - activity is initializing.</li>
* <li>com.keyes.video.msg.detect - detecting the bandwidth available to download video.</li>
* <li>com.keyes.video.msg.playlist - getting latest video from playlist.</li>
* <li>com.keyes.video.msg.token - retrieving token from YouTube.</li>
* <li>com.keyes.video.msg.loband - buffering low-bandwidth.</li>
* <li>com.keyes.video.msg.hiband - buffering hi-bandwidth.</li>
* <li>com.keyes.video.msg.error.title - dialog title displayed if anything goes wrong.</li>
* <li>com.keyes.video.msg.error.msg - message displayed if anything goes wrong.</li>
* </ul>
* <p/>
* <p>
* For example:
* </p>
* <p/>
*
* <pre>
* Intent lVideoIntent = new Intent(null, Uri.parse("ytpl://"+YOUTUBE_PLAYLIST_ID), this, OpenYouTubePlayerActivity.class);
* lVideoIntent.putExtra("com.keyes.video.msg.init", getString("str_video_intro"));
* lVideoIntent.putExtra("com.keyes.video.msg.detect", getString("str_video_detecting_bandwidth"));
* ...
* startActivity(lVideoIntent);
* </pre>
*
* @author David Keyes
*/

@interface YQYouTubePlayerHelper : NSObject {
   NSString * mVideoId;
}

- (id)init;
@property(nonatomic, assign) id<YQVideoInfoTaskCallback> videoInfoTaskCallback;
- (void)makeAndExecuteYoutubeTask:(NSString *)uri;
@property(nonatomic, strong) YQYoutubeTaskInfo * taskInfo;
@property(nonatomic, strong) YQYoutubeQuality * youtubeQuality;
@end
