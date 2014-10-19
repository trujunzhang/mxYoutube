//
//  IpadGridViewCell.m
//  app
//
//  Created by djzhang on 9/16/14.
//  Copyright (c) 2014 xinma. All rights reserved.
//

#import "IpadGridViewCell.h"

#import "GTLYouTubeVideo.h"
#import "GTLYouTubeVideoSnippet.h"
#import "GTLYouTubeThumbnailDetails.h"
#import "GTLYouTubeVideoContentDetails.h"
#import "GTLYouTubeVideoStatistics.h"
#import "GTLYouTubeThumbnail.h"

#import "UIImageView+Cache.h"


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


- (void)prepareView {
//   self.title.lineBreakMode = NSLineBreakByWordWrapping;
//   [self.title setNumberOfLines:2];

//   [self.title setFont:[UIFont fontWithName:@"Courier" size:14]];
}


- (void)bind:(GTLYouTubeVideo *)video {
   [self prepareView];

   // Confirm that the result represents a video. Otherwise, the
   // item will not contain a video ID.
   GTLYouTubeThumbnail * thumbnail = video.snippet.thumbnails.high;
   NSString * _thumbnailUrl = thumbnail.url;

   NSString * _titleValue = video.snippet.title;
   NSString * _durationValue = video.contentDetails.duration;
   NSNumber * _viewCountValue = video.statistics.viewCount;
   NSNumber * _likeCountValue = video.statistics.likeCount;
   NSNumber * _dislikeCountValue = video.statistics.dislikeCount;

   NSString * _userNameValue = video.snippet.channelTitle;

   [self.title setText:_titleValue];
   [self.rating setText:[NSString stringWithFormat:@"%@", _likeCountValue]];
   [self.viewCount setText:[NSString stringWithFormat:@"%@", _viewCountValue]];

   [self.userName setText:_userNameValue];

   [self.thumbnails setImageWithURL:[NSURL URLWithString:_thumbnailUrl]];// used

//   NSString * imageName = video.snippet.channelId;
//   UIImage * image = [UIImage imageNamed:imageName];
//   self.thumbnails.image = image;// test
}
@end
