#import "YQYouTubeId.h"

@implementation YQYouTubeId

@synthesize id;

- (id) initWithPId:(NSString *)pId {
  if ((self = [super init])) {
    mId = pId;
  }
  return self;
}

@end
