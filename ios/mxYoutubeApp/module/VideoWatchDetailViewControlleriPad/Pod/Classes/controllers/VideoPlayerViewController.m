//
//  VideoPlayerViewController.m
//  WatchAutoLayout
//
//  Created by djzhang on 10/5/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "VideoPlayerViewController.h"


@interface VideoPlayerViewController ()

@end


@implementation VideoPlayerViewController

- (instancetype)init {
   self = [super init];
   if (self) {

   }

   return self;
}


- (void)viewDidLoad {
   [super viewDidLoad];
   // Do any additional setup after loading the view from its nib.

   self.mImageView = [[UIImageView alloc] initWithFrame:self.view.frame];
   [self.view addSubview:self.mImageView];

   self.mImageView.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
}




- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


- (void)viewWillLayoutSubviews {
   [super viewWillLayoutSubviews];
   [self updateTabBarController:[UIApplication sharedApplication].statusBarOrientation];
}


- (void)updateTabBarController:(UIInterfaceOrientation)toInterfaceOrientation {
   UIImage * image = nil;

//   CGFloat x = self.view.frame.origin.x;
//   CGFloat y = self.view.frame.origin.y;
//   CGFloat w = self.view.frame.size.width;
//   CGFloat h = self.view.frame.size.height;
//
//   NSLog(@"    VideoPlayerViewController     ");
//   NSLog(@"x = %f",x);
//   NSLog(@"y = %f",y);
//   NSLog(@"w = %f",w);
//   NSLog(@"h = %f",h);

   BOOL isPortrait = (toInterfaceOrientation == UIInterfaceOrientationPortrait) || (toInterfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);
   if (isPortrait) {
      image = [UIImage imageNamed:@"ver.png"];
   } else {
      image = [UIImage imageNamed:@"hor.png"];
   }
//   self.mImageView.frame = self.view.frame;
   self.mImageView.image = image;

}

@end
