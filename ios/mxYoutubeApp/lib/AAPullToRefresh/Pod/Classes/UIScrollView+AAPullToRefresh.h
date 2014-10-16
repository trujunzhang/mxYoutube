//
// Created by djzhang on 10/15/14.
// Copyright (c) 2014 djzhang. All rights reserved.
//


#include "AAPullToRefresh.h"
#ifndef __UIScrollView_H_
#define __UIScrollView_H_


@interface UIScrollView (AAPullToRefresh)
- (AAPullToRefresh *)addPullToRefreshPosition:(AAPullToRefreshPosition)position actionHandler:(void (^)(AAPullToRefresh *v))handler;
@end


#endif //__UIScrollView_H_
