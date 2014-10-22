//
//  SecondViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "SecondViewController.h"

@implementation SecondViewController

- (id)init
{
    self = [super init];
    if (self) {
        self.title = @"More From";
    }
    return self;
}

- (void)viewDidLoad {
   [super viewDidLoad];

   self.view.backgroundColor = [UIColor whiteColor];

   self.label = [[UILabel alloc] init];

   self.label.text = @"Second";
   self.label.textColor = [UIColor redColor];
   self.label.textAlignment = NSTextAlignmentCenter;
   self.label.font = [self.label.font fontWithSize:105];
   [self.view addSubview:self.label];
}


- (void)viewDidLayoutSubviews {
   [super viewDidLayoutSubviews];

   self.label.frame = self.view.frame;
}


@end
