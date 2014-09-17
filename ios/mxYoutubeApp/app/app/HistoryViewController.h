//
//  HistoryViewController.h
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import <UIKit/UIKit.h>


static NSString * const identifier = @"CELL";


@interface HistoryViewController : UIViewController<UICollectionViewDataSource, UICollectionViewDelegate>

//@property(weak, nonatomic) IBOutlet UICollectionView * collectionView;
@property(nonatomic, strong) UICollectionView * collectionView;

@end
