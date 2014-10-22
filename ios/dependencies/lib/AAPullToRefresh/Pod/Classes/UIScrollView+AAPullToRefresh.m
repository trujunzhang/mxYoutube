//
// Created by djzhang on 10/15/14.
// Copyright (c) 2014 djzhang. All rights reserved.
//

#include "UIScrollView+AAPullToRefresh.h"


@implementation UIScrollView (AAPullToRefresh)

- (AAPullToRefresh *)addPullToRefreshPosition:(AAPullToRefreshPosition)position actionHandler:(void (^)(AAPullToRefresh * v))handler {
   AAPullToRefresh * view = [[AAPullToRefresh alloc] initWithImage:[UIImage imageNamed:@"centerIcon"]
                                                          position:position];
   switch (view.position) {
      case AAPullToRefreshPositionTop:
      case AAPullToRefreshPositionBottom:
         view.frame = CGRectMake((self.bounds.size.width - view.bounds.size.width) / 2,
          -view.bounds.size.height, view.bounds.size.width, view.bounds.size.height);
         break;
      case AAPullToRefreshPositionLeft:
         view.frame = CGRectMake(-view.bounds.size.width, self.bounds.size.height / 2.0f, view.bounds.size.width, view.bounds.size.height);
         break;
      case AAPullToRefreshPositionRight:
         view.frame = CGRectMake(self.bounds.size.width, self.bounds.size.height / 2.0f, view.bounds.size.width, view.bounds.size.height);
         break;
      default:
         break;
   }

   view.pullToRefreshHandler = handler;
   view.scrollView = self;
   view.originalInsetTop = self.contentInset.top;
   view.originalInsetBottom = self.contentInset.bottom;
   view.showPullToRefresh = YES;
   [self addSubview:view];

   return view;
}
@end

