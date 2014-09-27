#import "YQYouTubePlayerHelper.h"
#import "YQYoutube_URL.h"
#import "YQYouTubeId.h"
#import "YQPlaylistId.h"
#import "YQVideoId.h"
#import "YQFileId.h"
#import "MKNetworkOperation.h"
#import "MKNetworkEngine.h"


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
//   NSString * url = [NSString stringWithFormat:@"%@%@", YOUTUBE_VIDEO_INFORMATION_URL, [self getYouTubeId:uri].mId];
   [self prepareAndPlay:[self getYouTubeId:uri].mId];
}


- (void)prepareAndPlay:(NSString *)videoId {
   NSMutableDictionary * dic = [[NSMutableDictionary alloc] init];
   [dic setValue:videoId forKey:@"video_id"];

   MKNetworkEngine * engine = [[MKNetworkEngine alloc] initWithHostName:YOUTUBE_URL customHeaderFields:nil];

   MKNetworkOperation * op = [[MKNetworkOperation alloc] initWithURLString:YOUTUBE_VIDEO_INFORMATION_URL
                                                                    params:dic
                                                                httpMethod:@"GET"];


   void (^completionHandler)(MKNetworkOperation *) = ^(MKNetworkOperation * completedOperation) {
       // the completionBlock will be called twice.
       // if you are interested only in new values, move that code within the else block
       NSString * string = [completedOperation responseString];

   };
   void (^errorHandler)(MKNetworkOperation *, NSError *) = ^(MKNetworkOperation * errorOp, NSError * error) {

   };
   [op addCompletionHandler:completionHandler errorHandler:errorHandler];

   [engine enqueueOperation:op];
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
