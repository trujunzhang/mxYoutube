//
//  SecondViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "SecondViewController.h"


@implementation SecondViewController

- (id)init {
   self = [super init];
   if (self) {
      self.title = @"Search";
      self.tabBarItem.image = [UIImage imageNamed:@"tab_search"];
//      self.tabBarItem.selectedImage = [[UIImage imageNamed:@"tab_search"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
      self.tabBarItem.selectedImage = [UIImage imageNamed:@"tab_search"];
   }
   return self;
}


- (void)viewDidLoad {
//    self.view.backgroundColor = [UIColor blueColor];
}


@end
