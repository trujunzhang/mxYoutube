//
//  VideoDetailViewController.m
//  WatchAutoLayout
//
//  Created by djzhang on 10/5/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "VideoDetailViewController.h"
#import "VideoDetailPanel.h"


@interface VideoDetailViewController ()

@end


@implementation VideoDetailViewController

- (void)viewDidLoad {
   [super viewDidLoad];
   // Do any additional setup after loading the view from its nib.
   self.title = @"Info";

//   self.view.backgroundColor = [UIColor redColor];

//   self.view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   [self setupUI];
}


#pragma mark - View Lifecycle


- (void)setupUI {
   VideoDetailPanel * view = [[VideoDetailPanel alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
//   view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   self.view = view;
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


- (void)viewDidLayoutSubviews {
   [super viewDidLayoutSubviews];

   NSString * debug = @"debug";

   CGFloat x = self.view.frame.origin.x;
   CGFloat y = self.view.frame.origin.y;
   CGFloat w = self.view.frame.size.width;
   CGFloat h = self.view.frame.size.height;

   NSLog(@"    VideoDetailViewController     ");
   NSLog(@"x = %f", x);
   NSLog(@"y = %f", y);
   NSLog(@"w = %f", w);
   NSLog(@"h = %f", h);

}


@end
