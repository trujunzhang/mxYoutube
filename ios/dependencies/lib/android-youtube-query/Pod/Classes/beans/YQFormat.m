#import "YQFormat.h"


@implementation YQFormat


/**
* Construct this object from one of the strings in the "fmt_list" parameter
*
* @param pFormatString one of the comma separated strings in the "fmt_list" parameter
*/
- (id)initWithPFormatString:(NSString *)pFormatString {
   if ((self = [super init])) {
      NSArray * lFormatVars =
       [pFormatString componentsSeparatedByString:@"/"];

      self.mId = [lFormatVars[0] intValue];
   }
   return self;
}


/**
* Construct this object using a format id
*
* @param pId id of this format
*/
- (id)initWithPId:(int)pId {
   if ((self = [super init])) {
      self.mId = pId;
   }
   return self;
}


- (BOOL) isEqualTo:(NSObject *)pObject {
  return ((YQFormat *)pObject).mId == self.mId;
}

@end
