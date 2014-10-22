
/**
 * Represents a video stream
 */

@interface YQVideoStream : NSObject {

}

@property(nonatomic, copy) NSString * mUrl;
- (id) initWithPStreamStr:(NSString *)pStreamStr;

@end
