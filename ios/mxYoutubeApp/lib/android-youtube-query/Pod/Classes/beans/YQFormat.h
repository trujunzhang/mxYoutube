
/**
 * Represents a format in the "fmt_list" parameter
 * Currently, only id is used
 */

@interface YQFormat : NSObject {
  int mId;
}

@property(nonatomic, readonly) int id;
- (id) initWithPFormatString:(NSString *)pFormatString;
- (id) initWithPId:(int)pId;
- (BOOL) isEqualTo:(NSObject *)pObject;
@end
