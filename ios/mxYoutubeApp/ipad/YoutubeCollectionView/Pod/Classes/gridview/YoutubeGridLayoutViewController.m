//
//  YoutubeGridLayoutViewController.m
//  YoutubePlayApp
//
//  Created by djzhang on 10/15/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "YoutubeGridLayoutViewController.h"

#import "KRLCollectionViewGridLayout.h"
#import "IpadGridViewCell.h"
#import "GYSearch.h"
#import "SearchImplementation.h"


static NSString * const identifier = @"GridViewCellIdentifier";


@interface YoutubeGridLayoutViewController ()
@end


@implementation YoutubeGridLayoutViewController

- (instancetype)initWithVideoList:(NSArray *)videoList {
   self = [super init];
   if (self) {
      self.videoList = videoList;
      [[self collectionView] reloadData];
   }

   return self;
}


- (id)init {
   self = [super init];
   if (self) {
      self.videoList = [[NSArray alloc] init];
      YoutubeResponseBlock completion = ^(NSArray * array) {
          self.videoList = array;
          [[self collectionView] reloadData];
      };
      ErrorResponseBlock error = ^(NSError * error) {
          NSString * debug = @"debug";
      };
      [[SearchImplementation getInstance] searchByQueryWithQueryTerm:@"sketch3"
                                                   completionHandler:completion
                                                        errorHandler:error];
   }
   return self;
}


- (void)viewDidLoad {
   [super viewDidLoad];

   // Do any additional setup after loading the view.
   self.view.backgroundColor = [UIColor clearColor];

   self.placeHoderImage = [UIImage imageNamed:@"mt_cell_cover_placeholder"];

   [self setupCollectionView:self.view];
}


- (void)setupCollectionView:(UIScrollView *)scrollView {
   self.collectionViewGridLayout = [[KRLCollectionViewGridLayout alloc] init];
   self.collectionView = [[UICollectionView alloc] initWithFrame:scrollView.frame
                                            collectionViewLayout:self.collectionViewGridLayout];

   [self.collectionView setAutoresizesSubviews:YES];
   [self.collectionView setAutoresizingMask:UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight];

   [self.collectionView registerNib:[UINib nibWithNibName:@"IpadGridViewCell" bundle:nil]
         forCellWithReuseIdentifier:identifier];

   self.collectionView.dataSource = self;
   self.collectionView.delegate = self;
   self.collectionView.backgroundColor = [UIColor clearColor];

   [scrollView addSubview:self.collectionView];

   self.layout.aspectRatio = 1;
   self.layout.sectionInset = UIEdgeInsetsMake(10, 10, 10, 10);
   self.layout.interitemSpacing = 10;
   self.layout.lineSpacing = 10;
}


- (KRLCollectionViewGridLayout *)layout {
   return (id) self.collectionView.collectionViewLayout;
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}


- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
   return 1;
}


- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
   return self.videoList.count;
}


- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
   IpadGridViewCell * cell = [collectionView dequeueReusableCellWithReuseIdentifier:identifier forIndexPath:indexPath];
   cell.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;


   [cell    bind:[self.videoList objectAtIndex:indexPath.row]
placeholderImage:self.placeHoderImage
        delegate:self.delegate];

   return cell;
}


- (void)viewDidLayoutSubviews {
   [super viewDidLayoutSubviews];

//   CGFloat x = self.view.frame.origin.x;
//   CGFloat y = self.view.frame.origin.y;
//   CGFloat w = self.view.frame.size.width;
//   CGFloat h = self.view.frame.size.height;
//
//   NSLog(@"    YoutubeGridLayoutViewController     ");
//   NSLog(@"x = %f", x);
//   NSLog(@"y = %f", y);
//   NSLog(@"w = %f", w);
//   NSLog(@"h = %f", h);

   [self updateLayout:[UIApplication sharedApplication].statusBarOrientation];
}


- (void)updateLayout:(UIInterfaceOrientation)toInterfaceOrientation {
   BOOL isPortrait = (toInterfaceOrientation == UIInterfaceOrientationPortrait) || (toInterfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);

   NSAssert(self.numbersPerLineArray, @"Please initialize numbersPerLineArray first.");

   self.layout.numberOfItemsPerLine = [(self.numbersPerLineArray[isPortrait ? 0 : 1]) intValue];
}


@end
