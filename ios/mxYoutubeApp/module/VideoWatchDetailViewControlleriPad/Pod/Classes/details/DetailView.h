//
//  DetailView.h
//  WatchAutoLayout
//
//  Created by djzhang on 10/5/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import <UIKit/UIKit.h>


@class WHTopTabBarController;


@interface DetailView : UIView


@property(nonatomic, strong) UIViewController * leftTopController;
@property(nonatomic, strong) UIViewController * leftBottomController;
@property(nonatomic, strong) WHTopTabBarController * rightController;

@property(nonatomic, strong) UIView * leftAndTopPanel;

@property(nonatomic, strong) UIView * detailPanel;

- (void)initWithLeftTopController:(UIViewController *)leftTopController withLeftBottomController:(UIViewController *)leftBottomController withRightController:(UIViewController *)rightController;
@end
