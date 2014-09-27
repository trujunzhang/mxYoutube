#import "YQVideoStream.h"


@implementation YQVideoStream


/**
* Construct a video stream from one of the strings obtained
* from the "url_encoded_fmt_stream_map" parameter if the video_info
*
* @param pStreamStr - one of the strings from "url_encoded_fmt_stream_map"
*/
- (id)initWithPStreamStr:(NSString *)pStreamStr {
   if ((self = [super init])) {
      NSArray * lArgs = [pStreamStr componentsSeparatedByString:@"&"];
      NSMutableDictionary * lArgMap = [[NSMutableDictionary alloc] init];

      for (int i = 0; i < [lArgs count]; i++) {
         NSArray * lArgValStrArr = [lArgs[i] componentsSeparatedByString:@"="];
         if (lArgValStrArr != nil) {
            if ([lArgValStrArr count] >= 2) {
               [lArgMap setValue:lArgValStrArr[1] forKey:lArgValStrArr[0]];
            }
         }
      }

      NSString * url = [lArgMap objectForKey:@"url"];
      NSString * sig = [lArgMap objectForKey:@"sig"];
      self.mUrl = [NSString stringWithFormat:@"%@%@%@", url, @"&signature=", sig];
   }
   return self;
}


@end
