//
//  IpadGridViewCell.h
//  app
//
//  Created by djzhang on 9/16/14.
//  Copyright (c) 2014 xinma. All rights reserved.
//

#import <UIKit/UIKit.h>
@class GTLYouTubeVideo;


@protocol IpadGridViewCellDelegate<NSObject>

@optional

- (void)gridViewCellTap:(GTLYouTubeVideo *)video sender:(id)sender;

@end


@interface IpadGridViewCell : UICollectionViewCell

@property(strong, nonatomic) IBOutlet UIImageView * thumbnails;

@property(strong, nonatomic) IBOutlet UILabel * title;

@property(strong, nonatomic) IBOutlet UIImageView * userHeader;
@property(strong, nonatomic) IBOutlet UILabel * userName;

@property(strong, nonatomic) IBOutlet UIView * infoView;

@property(nonatomic, assign) id<IpadGridViewCellDelegate> delegate;
@property(nonatomic, strong) GTLYouTubeVideo * video;

- (void)bind:(GTLYouTubeVideo *)video placeholderImage:(UIImage *)image delegate:(id<IpadGridViewCellDelegate>)delegate;
@end
