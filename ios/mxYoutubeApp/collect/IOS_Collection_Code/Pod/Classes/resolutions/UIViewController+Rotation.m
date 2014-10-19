//
//  UIViewController+Rotation.m
//  ViewControllerRotation
//
//  Created by Hossam Ghareeb on 4/15/14.
//  Copyright (c) 2014 Hossam. All rights reserved.
//

#import "UIViewController+Rotation.h"

@implementation UIViewController (Rotation)


-(UIViewController *)testedViewController
{
    UIViewController *toBeTested = self;
    if ([self isKindOfClass:[UINavigationController class]]) {
        toBeTested = ((UINavigationController *)self).topViewController;
    }
    if ([self isKindOfClass:[UITabBarController class]]) {
        toBeTested = ((UITabBarController *)self).selectedViewController;
    }
    return toBeTested;
}

-(BOOL)shouldAutorotate{
    return YES;
}


-(NSUInteger)supportedInterfaceOrientations{
    
    UIViewController *toBeTested = [self testedViewController];
    NSString *class = NSStringFromClass(toBeTested.class);
    
    UIInterfaceOrientationMask mask = DEFAULT_ORIENTATION;
    
    if ([VIEW_CONTROLLERS_PORTRAIN_ONLY containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskPortrait;
        return UIInterfaceOrientationMaskPortrait;
    }
    
    if ([VIEW_CONTROLLERS_PORTRAIN_UPSIDE_DOWN_ONLY containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskPortraitUpsideDown;
        return UIInterfaceOrientationMaskPortraitUpsideDown;
    }

    
    if ([VIEW_CONTROLLERS_LANDSCAPE_ONLY containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskLandscape;
        return UIInterfaceOrientationMaskLandscape;
    }
    
    if ([VIEW_CONTROLLERS_LANDSCAPE_LEFT_ONLY containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskLandscapeLeft;
        return UIInterfaceOrientationMaskLandscapeLeft;
    }
    
    if ([VIEW_CONTROLLERS_LANDSCAPE_RIGHT_ONLY containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskLandscapeRight;
        return UIInterfaceOrientationMaskLandscapeRight;
    }
    
    if ([VIEW_CONTROLLERS_ALL containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskAll;
        return UIInterfaceOrientationMaskAll;
    }
    if ([VIEW_CONTROLLERS_ALL_BUT_UPSIDE_DOWN containsObject:class]) {
        mask = mask | UIInterfaceOrientationMaskAllButUpsideDown;
        return UIInterfaceOrientationMaskAllButUpsideDown;
    }
    
    if (mask != 0) {
        return mask;
    }
    return DEFAULT_ORIENTATION;
}
@end
