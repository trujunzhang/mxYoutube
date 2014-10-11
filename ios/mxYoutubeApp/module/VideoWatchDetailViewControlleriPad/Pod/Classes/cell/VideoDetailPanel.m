//
//  VideoDetailPanel.m
//  YoutubeDetailRotateTabbarsApp
//
//  Created by djzhang on 10/9/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "VideoDetailPanel.h"


@implementation VideoDetailPanel


- (id)initWithFrame:(CGRect)frame {
   self = [super initWithFrame:frame];
   if (self) {
      // 初始化时加载collectionCell.xib文件
      NSArray * arrayOfViews = [[NSBundle mainBundle] loadNibNamed:@"VideoDetailPanel"
                                                             owner:self
                                                           options:nil];

      // 如果路径不存在，return nil
      if (arrayOfViews.count < 1) {
         return nil;
      }
      // 如果xib中view不属于UICollectionViewCell类，return nil
      if (![[arrayOfViews objectAtIndex:0] isKindOfClass:[UIView class]]) {
         return nil;
      }
      // 加载nib
//      self = [arrayOfViews objectAtIndex:0];
      self = [arrayOfViews objectAtIndex:0];
   }
   return self;
}


@end
