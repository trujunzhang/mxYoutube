//
//  FirstViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "FirstViewController.h"
#import "JBTabBarController.h"

#import "AppDelegate.h"

@implementation FirstViewController

- (id)init {
    self = [super init];
    if (self) {
        self.title = @"wanghao";
        self.tabBarItem.image = [UIImage imageNamed:@"tab_home"];
        self.tabBarItem.selectedImage = [[UIImage imageNamed:@"tab_home"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];

        [self addTextView];
    }
    return self;
}

- (void)addTextView {

    UITextView *textView = [[UITextView alloc] initWithFrame:CGRectMake(50, 300, 100, 40)];
    [[self view] addSubview:textView];

    UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(50, 160, 100, 40)];
    button.backgroundColor = [UIColor redColor];
    [[self view] addSubview:button];

}

- (void)viewDidLoad {
    [super viewDidLoad];

}

@end
