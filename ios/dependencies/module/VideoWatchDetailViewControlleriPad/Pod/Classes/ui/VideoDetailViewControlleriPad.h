//
//  VideoDetailViewControlleriPad.h
//  YoutubePlayApp
//
//  Created by djzhang on 10/20/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//


#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

#import <MediaPlayer/MediaPlayer.h>

@class WHTopTabBarController;

@class YoutubeGridLayoutViewController;
@protocol IpadGridViewCellDelegate;
@class GTLYouTubeVideo;


@interface VideoDetailViewControlleriPad : UIViewController

@property(nonatomic, assign) id<IpadGridViewCellDelegate> delegate;
@property(nonatomic, strong) GTLYouTubeVideo * video;

@property(nonatomic, strong) MPMoviePlayerController * moviePlayer;

@property(nonatomic, strong) UIViewController * videoDetailController;
@property(nonatomic, strong) WHTopTabBarController * videoTabBarController;

@property(nonatomic, strong) UIViewController * firstViewController;
@property(nonatomic, strong) UIViewController * secondViewController;
@property(nonatomic, strong) YoutubeGridLayoutViewController * thirdViewController;

@property(nonatomic, strong) NSArray * defaultTableControllers;

@property(nonatomic, strong) id youTubeVideo;
- (instancetype)initWithDelegate:(id<IpadGridViewCellDelegate>)delegate video:(GTLYouTubeVideo *)video;

@end


