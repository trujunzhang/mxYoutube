//
//  ViewController.m
//  JBself
//
//  Created by wanghaogithub720 on 08/18/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import "ViewController.h"
#import "HomeViewController.h"
#import "SecondViewController.h"
#import "ThirdViewController.h"


@interface ViewController ()

@end


@implementation ViewController

- (void)viewDidLoad {
   [super viewDidLoad];

   self.view.backgroundColor = [UIColor clearColor]; // = transparent

   // Do any additional setup after loading the view, typically from a nib.
   [self buildTabBarControllerWithLayoutStrategy:JBTabBarLayoutStrategyBlockBased numberOfTabs:3];
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


- (void)buildTabBarControllerWithLayoutStrategy:(JBTabBarLayoutStrategy)layoutStrategy numberOfTabs:(NSUInteger)numberOfTabs {
   NSMutableArray * controllers = [[NSMutableArray alloc] init];

   NSArray * viewControllers = [NSArray arrayWithObjects:[[HomeViewController alloc] init],
                                                         [[SecondViewController alloc] init],
                                                         [[ThirdViewController alloc] init],
     nil];

   for (ViewController * controller in viewControllers) {
      UINavigationController * object = [[UINavigationController alloc] initWithRootViewController:controller];
      [[object navigationBar] setBackgroundImage:[UIImage new] forBarMetrics:UIBarMetricsDefault];
      [controllers addObject:object];
   }


   self.viewControllers = controllers;

   self.tabBar.maximumTabWidth = 64.0f;
   self.tabBar.layoutStrategy = layoutStrategy;
   if (layoutStrategy == JBTabBarLayoutStrategyBlockBased) {
      self.tabBar.layoutBlock = ^(JBTab * tab, NSUInteger index, NSUInteger numberOfTabs) {
          if (self.tabBar.bounds.size.width / numberOfTabs < self.tabBar.bounds.size.height) {
             CGFloat tabWidth = self.tabBar.bounds.size.width / numberOfTabs;
             tab.frame = CGRectMake(tabWidth * index, (self.tabBar.bounds.size.height - tabWidth) / 2, tabWidth, tabWidth);
          } else {
             CGFloat tabHeight = self.tabBar.bounds.size.height;
             CGFloat horizontalOffset = (self.tabBar.bounds.size.width - numberOfTabs * tabHeight) / 2;
             tab.frame = CGRectMake(horizontalOffset + tabHeight * index, 0.0, tabHeight, tabHeight);
          }
      };
   }
}


@end
