#import <android-youtube-query/YQYoutubeQuality.h>
#import "YQYouTubeUtility.h"


@implementation YQYouTubeUtility

+ (NSString *)URLDecode:(NSString *)value {
   NSString * result = [value stringByReplacingOccurrencesOfString:@"+" withString:@" "];
   result = [result stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
   return result;
}


+ (YQYoutubeQuality *)getFinalUri:(NSString *)lInfoStr {
   YQYoutubeQuality * youtubeQuality = nil;
   NSArray * lArgs = [lInfoStr componentsSeparatedByString:@"&"];
   NSMutableDictionary * lArgMap = [[NSMutableDictionary alloc] init];
   for (int i = 0; i < lArgs.count; i++) {
      NSArray * lArgValStrArr = [lArgs[i] componentsSeparatedByString:@"="];
      if (lArgValStrArr != nil) {
         if (lArgValStrArr.count >= 2) {
            [lArgMap setValue:[self URLDecode:lArgValStrArr[1]] forKey:lArgValStrArr[0]];
         }
      }
   }

   // Find out the URI NSString *  from the parameters
   // Populate the list of formats for the video
   NSString * lFmtList =
    [self URLDecode:[lArgMap objectForKey:@"fmt_list"]];
   NSMutableArray * lFormats = [[NSMutableArray alloc] init];
   if (nil != lFmtList) {
      NSArray * lFormatStrs = [lFmtList componentsSeparatedByString:@","];
      for (NSString * lFormatStr in lFormatStrs) {
         YQFormat * lFormat = [[YQFormat alloc] initWithPFormatString:lFormatStr];

         [lFormats addObject:lFormat];
      }
   }

   // Populate the list of streams for the video
   NSString * lStreamList = [lArgMap objectForKey:@"url_encoded_fmt_stream_map"];
   if (nil != lStreamList) {
      NSArray * lStreamStrs = [lStreamList componentsSeparatedByString:@","];
      NSMutableArray * lStreams = [[NSMutableArray alloc] init];
      for (NSString * lStreamStr in lStreamStrs) {
         YQVideoStream *
          lStream = [[YQVideoStream alloc] initWithPStreamStr:lStreamStr];
         [lStreams addObject:lStream];
      }

      youtubeQuality = [[YQYoutubeQuality alloc] init];

      youtubeQuality.lFormats = lFormats;
      youtubeQuality.lStreams = lStreams;
   }
   return youtubeQuality;
}
@end
