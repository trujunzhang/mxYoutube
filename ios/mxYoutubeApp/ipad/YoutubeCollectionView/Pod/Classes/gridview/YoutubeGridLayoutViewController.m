//
//  YoutubeGridLayoutViewController.m
//  YoutubePlayApp
//
//  Created by djzhang on 10/15/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "YoutubeGridLayoutViewController.h"

#import "AAPullToRefresh.h"

#import "KRLCollectionViewGridLayout.h"
#import "IpadGridViewCell.h"
#import "GYSearch.h"
#import "SearchImplementation.h"
#import "UIScrollView+AAPullToRefresh.h"


static NSString * const identifier = @"CELL";


@interface YoutubeGridLayoutViewController ()
@property(nonatomic, strong) UIView * thresholdView;
@property(nonatomic, strong) UIScrollView * scrollView;
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

   [self setupScrollView];
   [self setTopRefresh:self.scrollView];
   [self setupCollectionView:self.scrollView];
}


- (void)setTopRefresh:(UIScrollView *)scrollView {
// top
   void (^actionHandler)(AAPullToRefresh *) = ^(AAPullToRefresh * v) {
       NSLog(@"fire from top");
       [v performSelector:@selector(stopIndicatorAnimation)
               withObject:nil
               afterDelay:1.0f];
   };
   AAPullToRefresh * tv = [scrollView addPullToRefreshPosition:AAPullToRefreshPositionTop
                                                 actionHandler:actionHandler];
   tv.imageIcon = [UIImage imageNamed:@"launchpad"];
   tv.borderColor = [UIColor whiteColor];
}


- (void)setupScrollView {
   self.scrollView = [[UIScrollView alloc] init];

//   self.scrollView.frame = self.view.bounds;

   self.scrollView.delegate = self;
   self.scrollView.maximumZoomScale = 2.0f;
//   self.scrollView.contentSize = self.view.bounds.size;
   self.scrollView.alwaysBounceHorizontal = NO;
   self.scrollView.alwaysBounceVertical = YES;
//   self.scrollView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
   self.scrollView.backgroundColor = [UIColor clearColor];
   [self.view addSubview:self.scrollView];

   CGRect rect = self.scrollView.bounds;
   rect.size.height = self.scrollView.contentSize.height;
   self.thresholdView = [[UIView alloc] initWithFrame:rect];
   self.thresholdView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
   self.thresholdView.userInteractionEnabled = NO;
   self.thresholdView.backgroundColor = [UIColor clearColor];
   [self.scrollView addSubview:self.thresholdView];
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

//   cell.layer.masksToBounds = NO;
//   cell.layer.borderColor = [UIColor whiteColor].CGColor;
//   cell.layer.borderWidth = 0.8f;
//   cell.layer.contentsScale = [UIScreen mainScreen].scale;
//   cell.layer.shadowOpacity = 1.0f;
////   cell.layer.shadowRadius = 1.0f;
//   cell.layer.shadowOffset = CGSizeZero;

//   cell.layer.shadowPath = [UIBezierPath bezierPathWithRect:cell.bounds].CGPath;
//   cell.layer.shouldRasterize = YES;

   [cell bind:[self.videoList objectAtIndex:indexPath.row]];

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

   self.scrollView.frame = self.view.bounds;
   self.scrollView.contentSize = self.view.bounds.size;
   [self updateLayout:[UIApplication sharedApplication].statusBarOrientation];
}


- (void)updateLayout:(UIInterfaceOrientation)toInterfaceOrientation {
   BOOL isPortrait = (toInterfaceOrientation == UIInterfaceOrientationPortrait) || (toInterfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);

   NSAssert(self.numbersPerLineArray, @"Please initialize numbersPerLineArray first.");

   self.layout.numberOfItemsPerLine = [(self.numbersPerLineArray[isPortrait ? 0 : 1]) intValue];
}


- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView {
   return self.thresholdView;
}

@end
