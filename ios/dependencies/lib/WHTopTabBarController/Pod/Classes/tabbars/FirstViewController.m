//
//  FirstViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "FirstViewController.h"

@implementation FirstViewController

- (id)init {
   self = [super init];
   if (self) {
      self.title = @"Comments";
   }
   return self;
}


- (void)viewDidLoad {
   [super viewDidLoad];

   self.view.backgroundColor = [UIColor whiteColor];

   self.label = [[UILabel alloc] init];

   self.label.text = @"First";
   self.label.textColor = [UIColor whiteColor];
   self.label.textAlignment = NSTextAlignmentCenter;
   self.label.font = [self.label.font fontWithSize:145];
   self.label.backgroundColor=[UIColor redColor];
   [self.view addSubview:self.label];
}


- (void)viewDidLayoutSubviews {
   [super viewDidLayoutSubviews];

//   self.label.frame = self.view.frame;
   self.label.frame = CGRectMake(20, 20, self.view.frame.size.width-40, 145);
}


@end
