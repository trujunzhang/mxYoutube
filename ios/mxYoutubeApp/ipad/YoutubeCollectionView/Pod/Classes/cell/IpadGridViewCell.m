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


- (void)bind:(GTLYouTubeVideo *)video placeholderImage:(UIImage *)image delegate:(id<IpadGridViewCellDelegate>)delegate {
   self.video = video;
   self.delegate = delegate;

   // Confirm that the result represents a video. Otherwise, the
   // item will not contain a video ID.

   // 1
   [self.thumbnails setImageWithURL:[NSURL URLWithString:video.snippet.thumbnails.high.url]
                   placeholderImage:image];// used
   // UIImageView Touch event
   UITapGestureRecognizer * singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                                action:@selector(tapDetected)];
   singleTap.numberOfTapsRequired = 1;
   [self.thumbnails setUserInteractionEnabled:YES];
   [self.thumbnails addGestureRecognizer:singleTap];

//   self.thumbnails.image = [UIImage imageNamed:video.snippet.channelId];// test

   // 2
   [self.title setText:video.snippet.title];
   // 3
   [self setupVideoStatistics:video];
   // UIView Touch event
   UITapGestureRecognizer * singleTapPanel = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                                action:@selector(tapDetected)];
   singleTapPanel.numberOfTapsRequired = 1;
   [self.infoView setUserInteractionEnabled:YES];
   [self.infoView addGestureRecognizer:singleTapPanel];

   // 4
   [self.userName setText:video.snippet.channelTitle];
}


- (void)tapDetected {
   NSLog(@"single Tap on imageview");

   if (self.delegate) {
      [self.delegate gridViewCellTap:self.video];
   }
}


// #69 Creating a Non-Floating TableView Section Header
- (void)setupVideoStatistics:(GTLYouTubeVideo *)video {
   NSString * _durationValue = video.contentDetails.duration;
   NSNumber * _viewCountValue = video.statistics.viewCount;
   NSNumber * _likeCountValue = video.statistics.likeCount;
   NSNumber * _dislikeCountValue = video.statistics.dislikeCount;

   //   mt_video_rating_count.png
   // 1
   UIImageView * ratingView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"mt_video_rating_count"]];
   ratingView.frame = CGRectMake(9, 50, 14, 12);
   [self.infoView addSubview:ratingView];

   // 2
   UILabel * ratingLabel = [[UILabel alloc] init];
   ratingLabel.frame = CGRectMake(31, 50, 35, 11);
   [self.infoView addSubview:ratingLabel];
   [ratingLabel setText:@"88%"];
//   [ratingLabel setText:[NSString stringWithFormat:@"%@", _likeCountValue]];

   ratingLabel.textColor = [UIColor lightGrayColor];
   [ratingLabel setFont:[UIFont fontWithName:@"Helvetica-Light" size:12]];

   // 3
   UIImageView * viewCountView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"mt_video_view_count"]];
   viewCountView.frame = CGRectMake(115 - 7, 54 - 7, 15, 15);
   [self.infoView addSubview:viewCountView];

   // 4
   UILabel * viewCountLabel = [[UILabel alloc] init];
   viewCountLabel.frame = CGRectMake(130, 50, 35, 11);
   [self.infoView addSubview:viewCountLabel];
//   [viewCountLabel setText:@"117"];
   [viewCountLabel setText:[NSString stringWithFormat:@"%@", _viewCountValue]];

   viewCountLabel.textColor = [UIColor lightGrayColor];
   [viewCountLabel setFont:[UIFont fontWithName:@"Helvetica-Light" size:12]];
}
@end
