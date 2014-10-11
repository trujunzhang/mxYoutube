//
//  DetailView.m
//  WatchAutoLayout
//
//  Created by djzhang on 10/5/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "DetailView.h"
#import "AutoLayoutKit.h"


#import "VideoTabsViewController.h"


NSString * const kLKPSimpleViewWidth = @"width";
NSString * const kLKPSimpleViewHeight = @"height";


BOOL isDebug1 = NO;
//BOOL isDebug1 = YES;


@implementation DetailView {


}

- (void)initWithLeftTopController:(UIViewController *)leftTopController
         withLeftBottomController:(UIViewController *)leftBottomController
              withRightController:(UIViewController *)rightController {
   self.leftTopController = leftTopController;
   self.leftBottomController = leftBottomController;
   self.rightController = rightController;

   [self setup];
}


- (id)initWithFrame:(CGRect)frame {
   self = [super initWithFrame:frame];
   if (self) {
   }
   return self;
}


- (void)setup {
   // view setup
   self.backgroundColor = [UIColor whiteColor];

   // subviews
   [self setupSubviews];
   [self setupConstraint];
   [self setupTopConstraint];
   if (isDebug1 == YES) {
//      [self setupHorizontal];
//      [self addDetailView];
//      [self setupLeftHorizontal];

      [self setupVertical];
   }

}


- (void)setupConstraint {
   UIView * topView = self.leftAndTopPanel;
   UIView * bottomView = [self getRightView];
   UIView * parentView = self;

   [ALKConstraints layout:topView do:^(ALKConstraints * c) {
       [c make:ALKLeft equalTo:parentView s:ALKLeft];
       [c make:ALKTop equalTo:parentView s:ALKTop];

       [c set:ALKWidth to:1 name:kLKPSimpleViewWidth];
       [c set:ALKHeight to:1 name:kLKPSimpleViewHeight];
   }];

   [ALKConstraints layout:bottomView do:^(ALKConstraints * c) {
       [c make:ALKRight equalTo:parentView s:ALKRight];
       [c make:ALKBottom equalTo:parentView s:ALKBottom];

       [c set:ALKWidth to:1 name:kLKPSimpleViewWidth];
       [c set:ALKHeight to:1 name:kLKPSimpleViewHeight];
   }];
}


- (void)setupTopConstraint {
   UIView * topView = [self getTopView];
   UIView * bottomView = [self getDetailPanel];
   UIView * parentView = self.leftAndTopPanel;

   CGFloat aWidth = parentView.frame.size.width;
   CGFloat aHeight = parentView.frame.size.height;

   [ALKConstraints layout:topView do:^(ALKConstraints * c) {
       [c make:ALKLeft equalTo:parentView s:ALKLeft];
       [c make:ALKTop equalTo:parentView s:ALKTop];

       [c set:ALKWidth to:1 name:kLKPSimpleViewWidth];
       [c set:ALKHeight to:1 name:kLKPSimpleViewHeight];
   }];

   [ALKConstraints layout:bottomView do:^(ALKConstraints * c) {
       [c make:ALKLeft equalTo:parentView s:ALKLeft];
       [c make:ALKRight equalTo:parentView s:ALKRight];
       [c make:ALKBottom equalTo:parentView s:ALKBottom];

       [c set:ALKHeight to:1 name:kLKPSimpleViewHeight];
   }];
}


#pragma mark - Setup (internal)


- (void)addDetailView:(CGSize)size {
   self.getDetailControllerView.frame = CGRectMake(0, 0, size.width, size.height);
   [[self getDetailPanel] addSubview:[self getDetailControllerView]];

   [self.rightController removeInfoViewFromController:self.leftBottomController];
}


- (void)removeDetailView {
   for (UIView * view in [[self getDetailPanel] subviews]) {
      [view removeFromSuperview];
   }
   [self.rightController addInfoViewToController:self.leftBottomController];
}


- (UIView *)getTopView {
   return self.leftTopController.view;
}


- (UIView *)getDetailPanel {
   return self.detailPanel;
}


- (UIView *)getDetailControllerView {
   return self.leftBottomController.view;
}


- (UIView *)getRightView {
   return self.rightController.view;
}


- (void)setupSubviews {
   //1
   self.leftAndTopPanel = [[UIView alloc] init];
   [self.leftAndTopPanel setBackgroundColor:[UIColor clearColor]];

   [self.leftAndTopPanel addSubview:[self getTopView]];
   [self addSubview:self.leftAndTopPanel];

   // 2
   self.detailPanel = [[UIView alloc] init];
   [self.detailPanel setBackgroundColor:[UIColor clearColor]];

//   [self.detailPanel addSubview:[self getDetailControllerView]];
   [self addSubview:self.detailPanel];

   //3
   [self addSubview:[self getRightView]];
}


- (CGSize)setupHorizontal {
   CGFloat aWidth = self.frame.size.width;
   CGFloat aHeight = self.frame.size.height;

   UIView * leftView = self.leftAndTopPanel;
   UIView * rightView = [self getRightView];

   NSLayoutConstraint * cWidth = [leftView alk_constraintWithName:kLKPSimpleViewWidth];
   NSLayoutConstraint * cHeight = [leftView alk_constraintWithName:kLKPSimpleViewHeight];

   cWidth.constant = aWidth / 2;
   cHeight.constant = aHeight;
   [leftView setNeedsUpdateConstraints];

   cWidth = [rightView alk_constraintWithName:kLKPSimpleViewWidth];
   cHeight = [rightView alk_constraintWithName:kLKPSimpleViewHeight];

   cWidth.constant = aWidth / 2;
   cHeight.constant = aHeight;
   [rightView setNeedsUpdateConstraints];

   return CGSizeMake(aWidth / 2, aHeight);
}


- (CGSize)setupLeftHorizontal:(CGSize)size {
   CGFloat constHeight = 284;

   UIView * topView = [self getTopView];
   UIView * bottomView = [self getDetailPanel];

   CGFloat aWidth = size.width;
   CGFloat aHeight = size.height;

   NSLayoutConstraint * cWidth = [topView alk_constraintWithName:kLKPSimpleViewWidth];
   NSLayoutConstraint * cHeight = [topView alk_constraintWithName:kLKPSimpleViewHeight];

   cWidth.constant = aWidth;
   cHeight.constant = constHeight;
   [topView setNeedsUpdateConstraints];


   cHeight = [bottomView alk_constraintWithName:kLKPSimpleViewHeight];

   cHeight.constant = aHeight - constHeight;
   [bottomView setNeedsUpdateConstraints];

   return CGSizeMake(aWidth, aHeight - constHeight);
}


- (void)setupVertical {
   CGFloat constHeight = 434;

   UIView * topView = self.leftAndTopPanel;
   UIView * bottomView = [self getRightView];
   UIView * parentView = self;

   CGFloat aWidth = parentView.frame.size.width;
   CGFloat aHeight = parentView.frame.size.height;

   NSLayoutConstraint * cWidth = [topView alk_constraintWithName:kLKPSimpleViewWidth];
   NSLayoutConstraint * cHeight = [topView alk_constraintWithName:kLKPSimpleViewHeight];

   cWidth.constant = aWidth;
   cHeight.constant = constHeight;
   [topView setNeedsUpdateConstraints];

   cWidth = [bottomView alk_constraintWithName:kLKPSimpleViewWidth];
   cHeight = [bottomView alk_constraintWithName:kLKPSimpleViewHeight];

   cWidth.constant = aWidth;
   cHeight.constant = aHeight - constHeight;
   [bottomView setNeedsUpdateConstraints];
}


- (void)setupTopVertical {
   UIView * topView = [self getTopView];
   UIView * parentView = self.leftAndTopPanel;

   CGFloat aWidth = parentView.frame.size.width;
   CGFloat aHeight = parentView.frame.size.height;

   NSLayoutConstraint * cWidth = [topView alk_constraintWithName:kLKPSimpleViewWidth];
   NSLayoutConstraint * cHeight = [topView alk_constraintWithName:kLKPSimpleViewHeight];

   cWidth.constant = aWidth;
   cHeight.constant = aHeight;
   [topView setNeedsUpdateConstraints];
}


- (void)layoutSubviews {
   [super layoutSubviews];

//   NSUInteger integer = [self subviews].count;
//   NSLog(@"count = %d", integer);

   if (isDebug1 == NO) {
      [self updateLayout:[UIApplication sharedApplication].statusBarOrientation];
   }
}


- (void)updateLayout:(UIInterfaceOrientation)toInterfaceOrientation {
   BOOL isPortrait = (toInterfaceOrientation == UIInterfaceOrientationPortrait) || (toInterfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);

   if (isPortrait) {
      [self setupVertical];
      [self setupTopVertical];
      [self removeDetailView];
   } else {
      CGSize size = [self setupHorizontal];
      size = [self setupLeftHorizontal:size];
      [self addDetailView:size];
   }
}


@end
