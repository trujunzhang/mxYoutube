//
//  HistoryViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "HistoryViewController.h"
#import "GTLYouTubeVideo.h"
#import "VideoDetailViewControlleriPad.h"


@implementation HistoryViewController


- (id)init {
   self = [super init];
   if (self) {
      self.numbersPerLineArray = [NSArray arrayWithObjects:@"3", @"4", nil];

      UIButton * btn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 26, 19)];
      [btn addTarget:self action:@selector(popMenu:) forControlEvents:UIControlEventTouchUpInside];
      [btn setImage:[UIImage imageNamed:@"mt_side_tab_button"] forState:UIControlStateNormal];

      UIBarButtonItem * btnSearch = [[UIBarButtonItem alloc] initWithCustomView:btn];
      self.navigationItem.leftBarButtonItem = btnSearch;

      self.delegate = self;
   }
   return self;
}


- (void)popMenu:(id)send {

}


#pragma mark -
#pragma mark - IpadGridViewCellDelegate


- (void)gridViewCellTap:(GTLYouTubeVideo *)video sender:(id)sender {
   NSString * debug = @"debug";


   VideoDetailViewControlleriPad * controller = [[VideoDetailViewControlleriPad alloc] initWithDelegate:self
                                                                                                  video:video];

   [self.navigationItem setBackBarButtonItem:[[UIBarButtonItem alloc] initWithTitle:@"back"
                                                                              style:UIBarButtonItemStyleBordered
                                                                             target:nil
                                                                             action:nil]];
   [[self navigationController] pushViewController:controller animated:YES];
}


@end
