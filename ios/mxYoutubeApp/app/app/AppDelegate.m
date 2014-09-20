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


@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
   // Override point for customization after application launch.
   [self ShareDeviceResolution];

   self.window.tintColor = [UIColor colorWithHexString:@"#d23241"];

//    self.window.backgroundColor=[UIColor orangeColor];

   self.window.backgroundColor = [self getColorByResolution];

   [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(handleDidChangeStatusBarOrientationNotification:)
                                                name:UIApplicationDidChangeStatusBarOrientationNotification
                                              object:nil];

   return YES;
}


- (void)ShareDeviceResolution {
   _deviceResolution = [UIDevice resolution:[self isPortrait]];
}


- (UIColor *)getColorByResolution {
   return [AppResolutionHelper resolutionByType:_deviceResolution isPortrait:[self isPortrait]];
}


- (BOOL)isPortrait {
   UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
   BOOL isPortrait = (orientation == UIInterfaceOrientationPortrait) || (orientation == UIInterfaceOrientationPortraitUpsideDown);
   return isPortrait;
}


- (void)handleDidChangeStatusBarOrientationNotification:(NSNotification *)notification; {
   // Do something interesting
   self.window.backgroundColor = [self getColorByResolution];
   NSLog(@"The orientation is %@", [notification.userInfo objectForKey:UIApplicationStatusBarOrientationUserInfoKey]);
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
