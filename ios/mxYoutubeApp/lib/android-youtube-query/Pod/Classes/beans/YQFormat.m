#import "YQFormat.h"

@implementation YQFormat

@synthesize id;


/**
 * Construct this object from one of the strings in the "fmt_list" parameter
 * 
 * @param pFormatString one of the comma separated strings in the "fmt_list" parameter
 */
- (id) initWithPFormatString:(NSString *)pFormatString {
  if ((self = [super init])) {
//    NSString * lFormatVars[] = [pFormatString componentsSeparatedByString:@"/"];
//    mId = [YQInteger parseInt:lFormatVars[0]];
  }
  return self;
}


/**
 * Construct this object using a format id
 * 
 * @param pId id of this format
 */
- (id) initWithPId:(int)pId {
  if ((self = [super init])) {
    mId = pId;
  }
  return self;
}

//- (BOOL) isEqualTo:(NSObject *)pObject {
//  if (!([pObject conformsToProtocol:@protocol(YQFormat)])) {
//    return NO;
//  }
//  return ((YQFormat *)pObject).mId == mId;
//}

@end
