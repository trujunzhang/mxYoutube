//
//  HistoryViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "HistoryViewController.h"


@implementation HistoryViewController


- (id)init {
   self = [super init];
   if (self) {
      self.numbersPerLineArray = [NSArray arrayWithObjects:@"3", @"4", nil];
   }
   return self;
}


@end
