#import "YQVideoStream.h"
#import "YQFormat.h"


@interface YQYoutubeQuality : NSObject {
  NSMutableArray * lFormats;
  NSMutableArray * lStreams;
}

- (NSMutableArray *) getlFormats;
- (void) setlFormats:(NSMutableArray *)lFormats;
- (NSMutableArray *) getlStreams;
- (void) setlStreams:(NSMutableArray *)lStreams;
@end
