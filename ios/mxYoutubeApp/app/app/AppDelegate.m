//
//  AppDelegate.m
//  JBTabBarController
//
//  Created by CocoaPods on 08/18/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//


#import "AppDelegate.h"
#import "UIColor+HexString.h"
#import "AppResolutionHelper.h"
#import "ViewController.h"


@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
   // Override point for customization after application launch.

   self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];

   self.window.tintColor = [UIColor colorWithHexString:@"#d23241"];
//   self.window.backgroundColor = [self getColorByResolution];
   self.window.backgroundColor = [UIColor blueColor];

   self.controller = [[ViewController alloc] init];

//   [self.window addSubview:self.controller.view];
   self.window.rootViewController = self.controller;

   [self.window makeKeyAndVisible];

//   self.controller.view.backgroundColor = [UIColor clearColor];
//   self.controller.view.backgroundColor = [self getColorByResolution];

   //   [self.controller.view bringSubviewToFront:self.background];

   [self.controller changeBackground:[self isPortrait]];


   [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(handleDidChangeStatusBarOrientationNotification:)
                                                name:UIApplicationDidChangeStatusBarOrientationNotification
                                              object:nil];

   return YES;
}




- (void)handleDidChangeStatusBarOrientationNotification:(NSNotification *)notification; {
   // Do something interesting
//   self.controller.view.backgroundColor =[self getColorByResolution];
//   self.controller.view.backgroundColor = [UIColor redColor];
   self.window.backgroundColor = [UIColor redColor];
//   self.window.backgroundColor = [self getColorByResolution];
//   NSLog(@"The orientation is %@", [notification.userInfo objectForKey:UIApplicationStatusBarOrientationUserInfoKey]);
}


- (void)application:(UIApplication *)application didChangeStatusBarOrientation:(UIInterfaceOrientation)oldStatusBarOrientation {
//   self.controller.view.backgroundColor = [self getColorByResolution];
//   self.window.backgroundColor = [self getColorByResolution];
//   [self changeBackground];
   [self.controller changeBackground:[self isPortrait]];
}


- (UIColor *)getColorByResolution {
   return [AppResolutionHelper resolutionByType:[UIDevice resolution] isPortrait:[self isPortrait]];
}


- (BOOL)isPortrait {
   UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
   return (orientation == UIInterfaceOrientationPortrait) || (orientation == UIInterfaceOrientationPortraitUpsideDown);
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
