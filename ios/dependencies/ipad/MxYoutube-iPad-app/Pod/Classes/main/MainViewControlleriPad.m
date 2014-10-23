//
//  MainViewControlleriPad.m
//  JBself
//
//  Created by wanghaogithub720 on 08/18/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import "MainViewControlleriPad.h"

#import "HomeViewController.h"
#import "SearchViewController.h"
#import "HistoryViewController.h"

#import "AppResolutionHelper.h"
#import "UIDevice+Resolutions.h"


@interface MainViewControlleriPad ()

@end


@implementation MainViewControlleriPad

- (instancetype)init {
   self = [super init];
   if (self) {
      [self setBackground];

      self.horizontalImage = [UIImage imageNamed:[AppResolutionHelper resolutionNameByType:[UIDevice resolution]
                                                                                isPortrait:YES]];
      self.verticalImage = [UIImage imageNamed:[AppResolutionHelper resolutionNameByType:[UIDevice resolution]
                                                                              isPortrait:NO]];
   }
   return self;
}


- (void)setBackground {
//   self.background = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
}


- (void)viewDidLoad {
   [super viewDidLoad];


}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


- (void)buildTabBarController:(BOOL)portrait {
   JBTabBarLayoutStrategy layoutStrategy = JBTabBarLayoutStrategyLeftJustified;
   NSMutableArray * controllers = [[NSMutableArray alloc] init];

   NSArray * viewControllers = [NSArray arrayWithObjects:
    [[HistoryViewController alloc] init],
    [[HomeViewController alloc] init],
    [[SearchViewController alloc] init],
    [[SearchViewController alloc] init],
    [[SearchViewController alloc] init],
     nil];

   NSArray * viewTabBars = [NSArray arrayWithObjects:
    [NSArray arrayWithObjects:@"Subscriptions", @"tab_home", @"tab_home", nil],
    [NSArray arrayWithObjects:@"", @"tab_search", @"tab_search", nil],
    [NSArray arrayWithObjects:@"History", @"tab_history", @"tab_history", nil],
    [NSArray arrayWithObjects:@"Folders", @"tab_cache", @"tab_cache", nil],
    [NSArray arrayWithObjects:@"Folders", @"tab_more", @"tab_more", nil],
     nil];


   int i = 0;
//   NSString * name = [AppResolutionHelper resolutionNameByType:[UIDevice resolution] isPortrait:portrait];
//   UIImage * image = [UIImage imageNamed:name];
   for (MainViewControlleriPad * controller in viewControllers) {
      NSArray * tabBarInfo = viewTabBars[i++];
      controller.title = tabBarInfo[0];
      controller.tabBarItem.image = [UIImage imageNamed:tabBarInfo[1]];
      controller.tabBarItem.selectedImage = [[UIImage imageNamed:tabBarInfo[2]] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];

      UINavigationController * object = [[UINavigationController alloc] initWithRootViewController:controller];
//      [[object navigationBar] setBackgroundImage:image forBarMetrics:UIBarMetricsDefault];
      [controllers addObject:object];
   }

//   self.viewControllers = controllers;

   self.tabBar.maximumTabWidth = 64.0f;
   self.tabBar.layoutStrategy = layoutStrategy;
}


#pragma mark -
#pragma mark Rotation stuff


- (BOOL)shouldAutorotate {
   return YES;
}


- (NSUInteger)supportedInterfaceOrientations {
   return UIInterfaceOrientationMaskAll;
}


- (UIInterfaceOrientation)preferredInterfaceOrientationForPresentation {
   return UIInterfaceOrientationMaskAll;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)orientation {
   return YES;
}


- (void)viewDidLayoutSubviews {
   [super viewDidLayoutSubviews];

   [self updateLayout:[UIApplication sharedApplication].statusBarOrientation];
}


- (void)updateLayout:(UIInterfaceOrientation)toInterfaceOrientation {
   BOOL isPortrait = (toInterfaceOrientation == UIInterfaceOrientationPortrait) || (toInterfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);
   self.background.image = [self getBackgroundImage:isPortrait];
}


- (UIImage *)getBackgroundImage:(BOOL)isPortrait {
   UIImage * image = nil;
   if (isPortrait) {
      if (self.horizontalImage == nil) {
         self.horizontalImage = [UIImage imageNamed:[AppResolutionHelper resolutionNameByType:[UIDevice resolution]
                                                                                   isPortrait:isPortrait]];
      }
      image = self.horizontalImage;
   } else {
      if (self.verticalImage == nil) {
         self.verticalImage = [UIImage imageNamed:[AppResolutionHelper resolutionNameByType:[UIDevice resolution]
                                                                                 isPortrait:isPortrait]];
      }
      image = self.verticalImage;
   }
   return image;
}


@end
