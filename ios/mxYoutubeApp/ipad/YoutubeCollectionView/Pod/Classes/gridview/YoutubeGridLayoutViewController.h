//
//  YoutubeGridLayoutViewController.h
//  YoutubePlayApp
//
//  Created by djzhang on 10/15/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import <UIKit/UIKit.h>

@class KRLCollectionViewGridLayout;


@interface YoutubeGridLayoutViewController : UIViewController<UICollectionViewDataSource, UICollectionViewDelegate, UIScrollViewDelegate>

@property(nonatomic, strong) UICollectionView * collectionView;

@property(nonatomic, strong) KRLCollectionViewGridLayout * collectionViewGridLayout;
@property(nonatomic, strong) NSMutableArray * videoList;

@property(nonatomic, strong) NSArray * numbersPerLineArray;
- (id)initWithVideoList:(NSMutableArray *)array;


@end
