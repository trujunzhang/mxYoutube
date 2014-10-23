//
//  MainViewControlleriPad.h
//  JBTabBarController
//
//  Created by wanghaogithub720 on 08/18/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JBTabBarController.h"


@interface MainViewControlleriPad : JBTabBarController

@property(nonatomic, strong) UIImage * horizontalImage;
@property(nonatomic, strong) UIImage * verticalImage;

- (void)buildTabBarController:(BOOL)portrait;
@end
