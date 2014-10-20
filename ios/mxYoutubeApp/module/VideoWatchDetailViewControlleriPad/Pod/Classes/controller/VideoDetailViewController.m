//
//  VideoDetailViewController.m
//  YoutubePlayApp
//
//  Created by djzhang on 10/14/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "VideoDetailViewController.h"
#import "VideoDetailPanel.h"


@interface VideoDetailViewController ()

@end


@implementation VideoDetailViewController

- (void)viewDidLoad {
   [super viewDidLoad];
   // Do any additional setup after loading the view.
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


- (void)loadView {
   VideoDetailPanel * view = [[VideoDetailPanel alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
   view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;

   self.view = view;
}


@end
