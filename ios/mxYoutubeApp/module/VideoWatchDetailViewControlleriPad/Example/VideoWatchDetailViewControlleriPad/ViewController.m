//
//  ViewController.m
//  WatchAutoLayout
//
//  Created by djzhang on 10/5/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "ViewController.h"
#import "DetailView.h"


@interface ViewController ()

@end


@implementation ViewController

- (instancetype)initWithLeftTopController:(UIViewController *)leftTopController
                 withLeftBottomController:(UIViewController *)leftBottomController
                      withRightController:(UIViewController *)rightController {
   self = [super init];
   if (self) {
      self.leftTopController = leftTopController;
      self.leftBottomController = leftBottomController;
      self.rightController = rightController;
   }

   return self;
}


- (void)viewDidLoad {
   [super viewDidLoad];
   // Do any additional setup after loading the view, typically from a nib.
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


#pragma mark - View Lifecycle


- (void)loadView {
   DetailView * view = [[DetailView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
   view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   [view initWithLeftTopController:self.leftTopController
          withLeftBottomController:self.leftBottomController
               withRightController:self.rightController
   ];

   self.view = view;
}

@end
