//
//  HistoryViewController.m
//  JBTabBarControllerExample
//
//  Created by Jin Budelmann on 3/02/12.
//  Copyright (c) 2012 BitCrank. All rights reserved.
//

#import "HistoryViewController.h"
#import "IpadGridViewCell.h"


@implementation HistoryViewController


- (id)init {
   self = [super init];
   if (self) {

   }
   return self;
}


- (void)viewDidLoad {
   [super viewDidLoad];
   // Do any additional setup after loading the view, typically from a nib.
   UICollectionViewFlowLayout * flowLayout = [[UICollectionViewFlowLayout alloc] init];
   self.collectionView = [[UICollectionView alloc] initWithFrame:self.view.frame
                                            collectionViewLayout:flowLayout];

   [self.collectionView setAutoresizesSubviews:YES];
   [self.collectionView setAutoresizingMask:
    UIViewAutoresizingFlexibleWidth |
     UIViewAutoresizingFlexibleHeight];

   [self.collectionView registerNib:[UINib nibWithNibName:@"IpadGridViewCell" bundle:nil]
         forCellWithReuseIdentifier:identifier];

   self.collectionView.dataSource = self;
   self.collectionView.delegate = self;
   self.collectionView.backgroundColor = [UIColor clearColor];

   [self.view addSubview:self.collectionView];

//   [self.collectionView reloadData];
}


#pragma mark -- UICollectionViewDataSource


//定义展示的UICollectionViewCell的个数
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
   return 3;
}


//定义展示的Section的个数
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
   return 1;
}


//每个UICollectionView展示的内容
- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
   IpadGridViewCell * cell = [collectionView dequeueReusableCellWithReuseIdentifier:identifier forIndexPath:indexPath];
//   cell.cellLabel.text = [NSString stringWithFormat:@"w %i", x];
   return cell;
}


#pragma mark --UICollectionViewDelegateFlowLayout


//定义每个UICollectionView 的大小
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
   return CGSizeMake(250, 246);
}


//定义每个UICollectionView 的 margin
- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout insetForSectionAtIndex:(NSInteger)section {
   return UIEdgeInsetsMake(5, 5, 5, 5);
}


#pragma mark --UICollectionViewDelegate


//UICollectionView被选中时调用的方法
- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
   UICollectionViewCell * cell = (UICollectionViewCell *) [collectionView cellForItemAtIndexPath:indexPath];
   cell.backgroundColor = [UIColor whiteColor];
}


//返回这个UICollectionView是否可以被选择
- (BOOL)collectionView:(UICollectionView *)collectionView shouldSelectItemAtIndexPath:(NSIndexPath *)indexPath {
   return YES;
}


@end
