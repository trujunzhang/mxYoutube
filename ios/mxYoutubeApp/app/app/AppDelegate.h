//
//  AppDelegate.h
//  JBTabBarController
//
//  Created by CocoaPods on 08/18/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <IOS_Collection_Code/UIDevice+Resolutions.h>
@class ViewController;


@interface AppDelegate : UIResponder<UIApplicationDelegate> {
}

@property(strong, nonatomic) UIWindow * window;

@property(nonatomic, strong) ViewController * controller;
@property(nonatomic, strong) UIImageView * background;
@end
