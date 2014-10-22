//
//  YQViewController.h
//  android-youtube-query
//
//  Created by wanghaogithub720 on 09/26/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import <UIKit/UIKit.h>
@class YQYouTubePlayerHelper;


@interface YQViewController : UIViewController

@property(nonatomic, strong) YQYouTubePlayerHelper * playerHelper;

@property(nonatomic, copy) NSString * gameName;

@end
