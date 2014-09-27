
/**
 * Represents a video stream
 */

@interface YQVideoStream : NSObject {
  NSString * mUrl;
}

- (id) initWithPStreamStr:(NSString *)pStreamStr;
- (NSString *) getmUrl;
- (void) setmUrl:(NSString *)mUrl;
@end
