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
      self.url = @"wanghao";

      NSArray * lArgs = [pStreamStr componentsSeparatedByString:@"&"];
      NSMutableDictionary * lArgMap = [[NSMutableDictionary alloc] init];

//    for (int i = 0; i < [lArgs count]; i++) {
//      NSArray * lArgValStrArr = [lArgs[i] split:@"="];
//      if (lArgValStrArr != nil) {
//        if ([lArgValStrArr count] >= 2) {
////          [lArgMap setObject:lArgValStrArr[0] param1:lArgValStrArr[1]];
//        }
//      }
//    }

//    mUrl = [[lArgMap objectForKey:@"url"] stringByAppendingString:@"&signature="] + [lArgMap objectForKey:@"sig"];
   }
   return self;
}

@end
