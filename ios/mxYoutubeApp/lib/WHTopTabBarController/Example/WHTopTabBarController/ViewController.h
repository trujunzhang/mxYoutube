//
//  ViewController.h
//  WatchAutoLayout
//
//  Created by djzhang on 10/5/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface ViewController : UIViewController


@property(nonatomic, strong) UIViewController * leftTopController;
@property(nonatomic, strong) UIViewController * leftBottomController;
@property(nonatomic, strong) UIViewController * rightController;

- (id)initWithLeftTopController:(UIViewController *)leftTopController
       withLeftBottomController:(UIViewController *)leftBottomController
            withRightController:(UIViewController *)rightController;
@end

