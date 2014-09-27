#import "YQYouTubePlayerHelper.h"
#import "YQYoutube_URL.h"
#import "YQYouTubeId.h"
#import "YQPlaylistId.h"
#import "YQVideoId.h"
#import "YQFileId.h"


@implementation YQYouTubePlayerHelper

- (id)init {
   if (self = [super init]) {
      mVideoId = nil;
   }
   return self;
}


/**
* "http://www.youtube.com/get_video_info?&video_id=izA_Xgbj7II"
*/
- (void)makeAndExecuteYoutubeTask:(NSString *)uri {
   NSString * url = [NSString stringWithFormat:@"%@%@", YOUTUBE_VIDEO_INFORMATION_URL, [self getYouTubeId:uri].mId];
   [self prepareAndPlay:url];
}


- (void)prepareAndPlay:(NSString *)url {

}


/**
* format:  @"ytv://izA_Xgbj7II"
*/
- (YQYouTubeId *)getYouTubeId:(NSString *)uri {
   NSArray * array = [uri componentsSeparatedByString:@"://"];

   if (array.count == 1) {
      return NULL;
   }
   NSString * scheme = array[0];
   NSString * lVideoIdStr = array[1];

   return [self getYouTubeId:scheme with:lVideoIdStr];
}


- (YQYouTubeId *)getYouTubeId:(NSString *)scheme with:(NSString *)videoId {
   YQYouTubeId * lYouTubeId = nil;

   if ([scheme isEqualToString:SCHEME_YOUTUBE_PLAYLIST]) {
      lYouTubeId = [[YQPlaylistId alloc] initWithPId:videoId];
   }
   else if ([scheme isEqualToString:SCHEME_YOUTUBE_VIDEO]) {
      lYouTubeId = [[YQVideoId alloc] initWithPId:videoId];
   }
   else if ([scheme isEqualToString:SCHEME_FILE]) {
      lYouTubeId = [[YQFileId alloc] initWithPId:videoId];
   }
   return lYouTubeId;
}


@end
