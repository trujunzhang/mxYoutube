//
//  UIViewController+Rotation.h
//  ViewControllerRotation
//
//  Created by Hossam Ghareeb on 4/15/14.
//  Copyright (c) 2014 Hossam. All rights reserved.
//

#import <UIKit/UIKit.h>

#define DEFAULT_ORIENTATION UIInterfaceOrientationMaskPortrait

#define VIEW_CONTROLLERS_PORTRAIN_ONLY @[@"ViewController1"]
#define VIEW_CONTROLLERS_LANDSCAPE_ONLY @[]
#define VIEW_CONTROLLERS_LANDSCAPE_LEFT_ONLY @[@"ViewController2"]
#define VIEW_CONTROLLERS_LANDSCAPE_RIGHT_ONLY @[]
#define VIEW_CONTROLLERS_PORTRAIN_UPSIDE_DOWN_ONLY @[@"ViewController2"]
#define VIEW_CONTROLLERS_ALL @[]
#define VIEW_CONTROLLERS_ALL_BUT_UPSIDE_DOWN @[@"", @""]

@interface UIViewController (Rotation)

@end
