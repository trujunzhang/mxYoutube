#import "YQYoutubeTaskInfo.h"

NSString * const MSG_INIT = @"com.keyes.video.msg.init";
NSString * const MSG_DETECT = @"com.keyes.video.msg.detect";
NSString * const MSG_PLAYLIST = @"com.keyes.video.msg.playlist";
NSString * const MSG_TOKEN = @"com.keyes.video.msg.token";
NSString * const MSG_LO_BAND = @"com.keyes.video.msg.loband";
NSString * const MSG_HI_BAND = @"com.keyes.video.msg.hiband";
NSString * const MSG_ERROR_TITLE = @"com.keyes.video.msg.error.title";
NSString * const MSG_ERROR_MSG = @"com.keyes.video.msg.error.msg";

@implementation YQYoutubeTaskInfo

- (id) init {
  if (self = [super init]) {
    self.showControllerOnStartup = NO;
    self.lYouTubeFmtQuality = @"17";
    mMsgInit = @"Initializing";
    mMsgDetect = @"Detecting Bandwidth";
    mMsgPlaylist = @"Determining Latest Video in YouTube Playlist";
    mMsgToken = @"Retrieving YouTube Video Token";
    mMsgLowBand = @"Buffering Low-bandwidth Video";
    mMsgHiBand = @"Buffering High-bandwidth Video";
    mMsgErrorTitle = @"Communications Error";
    mMsgError = @"An error occurred during the retrieval of the video.  This could be due to network issues or YouTube protocols.  Please try again later.";
  }
  return self;
}

@end
