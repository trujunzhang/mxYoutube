//
//  WDAppDelegate.m
//  VideoWatchDetailViewControlleriPad
//
//  Created by CocoaPods on 10/10/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import "WDAppDelegate.h"


#import "VideoPlayerViewController.h"
#import "VideoDetailViewController.h"
#import "VideoTabsViewController.h"
#import "ViewController.h"


@implementation WDAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
   // Override point for customization after application launch.
   self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
   // Override point for customization after application launch.

   UIViewController * controller = [self getController];

   controller.view.backgroundColor = [UIColor redColor];

   self.window.rootViewController = controller;

   [self.window makeKeyAndVisible];
   return YES;
}


- (UIViewController *)getController234 {
//   VideoPlayerViewController * controller = [[VideoPlayerViewController alloc] init];
//   VideoDetailViewController * controller = [[VideoDetailViewController alloc] init];
   VideoTabsViewController * controller = [[VideoTabsViewController alloc] init];
   [controller testUI];
   return controller;
}


- (UIViewController *)getController {
   VideoPlayerViewController * leftTopController = [[VideoPlayerViewController alloc] init];
   VideoDetailViewController * leftBottomController = [[VideoDetailViewController alloc] init];
   VideoTabsViewController * rightController = [[VideoTabsViewController alloc] init];


   ViewController * controller = [[ViewController alloc] initWithLeftTopController:leftTopController
                                                          withLeftBottomController:leftBottomController
                                                               withRightController:rightController];
   return controller;
}


- (void)applicationWillResignActive:(UIApplication *)application {
   // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
   // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
   // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
   // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
   // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
   // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}


- (void)applicationWillTerminate:(UIApplication *)application {
   // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
