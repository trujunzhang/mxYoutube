//
//  HomeViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "HomeViewController.h"
#import "JBTabBarController.h"

#import "AppDelegate.h"
#import "SearchViewController.h"


@implementation HomeViewController

- (id)init {
   self = [super init];
   if (self) {

      [self addTextView];
   }
   return self;
}


- (void)addTextView {

   UITextView * textView = [[UITextView alloc] initWithFrame:CGRectMake(50, 300, 100, 40)];
   [[self view] addSubview:textView];

   UIButton * button = [[UIButton alloc] initWithFrame:CGRectMake(50, 160, 100, 40)];
   button.backgroundColor = [UIColor redColor];
   [button setTitle:@"Play" forState:UIControlStateNormal];
   [button addTarget:self action:@selector(BtnClick:) forControlEvents:UIControlEventTouchUpInside];

   [[self view] addSubview:button];

}


- (IBAction)BtnClick:(id)sender {
   SearchViewController * firstController = [[SearchViewController alloc] init];
   [[self navigationController] pushViewController:firstController animated:NO];

   NSLog(@"BtnClick");
}


- (void)viewDidLoad {
   [super viewDidLoad];

}

@end
