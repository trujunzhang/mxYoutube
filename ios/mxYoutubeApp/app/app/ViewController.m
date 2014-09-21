//
//  ViewController.m
//  JBself
//
//  Created by wanghaogithub720 on 08/18/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import "ViewController.h"
#import "HomeViewController.h"
#import "SearchViewController.h"
#import "HistoryViewController.h"
#import "UIDevice+Resolutions.h"
#import "AppResolutionHelper.h"


@interface ViewController ()

@end


@implementation ViewController

- (instancetype)init {
   self = [super init];
   if (self) {
      [self setBackground];

   }

   return self;
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
     nil];

   NSArray * viewTabBars = [NSArray arrayWithObjects:
    [NSArray arrayWithObjects:@"Subscriptions", @"tab_home", @"tab_home", nil],
    [NSArray arrayWithObjects:@"", @"tab_search", @"tab_search", nil],
    [NSArray arrayWithObjects:@"History", @"tab_history", @"tab_history", nil],
     nil];


   int i = 0;
   NSString * name = [AppResolutionHelper resolutionNameByType:[UIDevice resolution] isPortrait:portrait];
   for (ViewController * controller in viewControllers) {
      NSArray * tabBarInfo = viewTabBars[i++];
      controller.title = tabBarInfo[0];
      controller.tabBarItem.image = [UIImage imageNamed:tabBarInfo[1]];
      controller.tabBarItem.selectedImage = [[UIImage imageNamed:tabBarInfo[2]] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];

      UINavigationController * object = [[UINavigationController alloc] initWithRootViewController:controller];
//      [[object navigationBar] setBackgroundImage:[UIImage new] forBarMetrics:UIBarMetricsCompact];
//      [[object navigationBar] setBackgroundImage:[UIImage imageNamed:name]
//                                   forBarMetrics:UIBarMetricsDefault];
      [controllers addObject:object];
   }


   self.viewControllers = controllers;

   self.tabBar.maximumTabWidth = 64.0f;
   self.tabBar.layoutStrategy = layoutStrategy;
}


#pragma mark -
#pragma mark Rotation stuff


- (BOOL)shouldAutorotate {
   return YES;
}


- (NSUInteger)supportedInterfaceOrientations {
   if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
      return UIInterfaceOrientationMaskPortrait;
   }
   return UIInterfaceOrientationMaskAll;
}


- (UIInterfaceOrientation)preferredInterfaceOrientationForPresentation {
   return UIInterfaceOrientationLandscapeLeft;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)orientation {
   if ((orientation == UIInterfaceOrientationLandscapeRight) || (orientation == UIInterfaceOrientationLandscapeLeft)) {
      return YES;
   }

   return NO;
}


- (void)setBackground {
   self.background = [[UIImageView alloc] init];
//   self.background.frame = CGRectMake(0, 0, 1024, 768);

//   [self.view addSubview:self.background];
}


- (void)changeBackground:(BOOL)portrait {
   NSString * name = [AppResolutionHelper resolutionNameByType:[UIDevice resolution] isPortrait:portrait];
   if (portrait) {
      self.background.frame = CGRectMake(0, 0, 768, 1024);
   } else {
      self.background.frame = CGRectMake(0, 0, 1024, 768);
   }
//   self.background.image = [UIImage imageNamed:@"Default-568h@2x.png"];
   self.background.image = [UIImage imageNamed:name];
}

@end
