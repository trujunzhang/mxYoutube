//
//  IpadGridViewCell.m
//  app
//
//  Created by djzhang on 9/16/14.
//  Copyright (c) 2014 xinma. All rights reserved.
//

#import "IpadGridViewCell.h"


@implementation IpadGridViewCell


- (id)initWithFrame:(CGRect)frame {
   self = [super initWithFrame:frame];
   if (self) {
      // 初始化时加载collectionCell.xib文件
      NSArray * arrayOfViews = [[NSBundle mainBundle] loadNibNamed:@"IpadGridViewCell" owner:self options:nil];

      // 如果路径不存在，return nil
      if (arrayOfViews.count < 1) {
         return nil;
      }
      // 如果xib中view不属于UICollectionViewCell类，return nil
      if (![[arrayOfViews objectAtIndex:0] isKindOfClass:[UICollectionViewCell class]]) {
         return nil;
      }
      // 加载nib
      self = [arrayOfViews objectAtIndex:0];
   }
   return self;
}

@end
