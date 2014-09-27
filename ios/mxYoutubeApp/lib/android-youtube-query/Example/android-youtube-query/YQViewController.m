//
//  YQViewController.m
//  android-youtube-query
//
//  Created by wanghaogithub720 on 09/26/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import <android-youtube-query/YQYouTubePlayerHelper.h>
#import "YQViewController.h"
#import "YQYouTubePlayerHelper.h"


@interface YQViewController ()

@end


@implementation YQViewController

- (void)viewDidLoad {
   [super viewDidLoad];
   // Do any additional setup after loading the view, typically from a nib.

   self.playerHelper = [[YQYouTubePlayerHelper alloc] init];
// determine the messages to be displayed as the view loads the video
   self.playerHelper.taskInfo = [self getExtractMessages ];
}


- (YQYoutubeTaskInfo *)getExtractMessages {

   YQYoutubeTaskInfo * _taskInfo = [[YQYoutubeTaskInfo alloc] init];

   _taskInfo.lYouTubeFmtQuality = "18";
   _taskInfo.showControllerOnStartup = false;

   return _taskInfo;
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}

@end
