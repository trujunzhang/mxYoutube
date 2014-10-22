#import "YQYouTubeId.h"

@implementation YQYouTubeId


- (id) initWithPId:(NSString *)pId {
  if ((self = [super init])) {
    self.mId = pId;
  }
  return self;
}

@end
