
/**
 * Represents a video stream
 */

@interface YQVideoStream : NSObject {
  NSString * mUrl;
}


@property(nonatomic, retain, readonly) NSString * url;
@property(nonatomic, copy) NSString * url;

- (id) initWithPStreamStr:(NSString *)pStreamStr;
@end
