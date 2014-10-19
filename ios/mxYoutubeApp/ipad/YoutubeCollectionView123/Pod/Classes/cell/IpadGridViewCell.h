//
//  IpadGridViewCell.h
//  app
//
//  Created by djzhang on 9/16/14.
//  Copyright (c) 2014 xinma. All rights reserved.
//

#import <UIKit/UIKit.h>
@class GTLYouTubeVideo;


@interface IpadGridViewCell : UICollectionViewCell

@property (strong, nonatomic) IBOutlet UILabel *title;
@property (strong, nonatomic) IBOutlet UILabel *rating;
@property (strong, nonatomic) IBOutlet UIImageView *thumbnails;
@property (strong, nonatomic) IBOutlet UILabel *viewCount;
@property (strong, nonatomic) IBOutlet UIImageView *userHeader;
@property (strong, nonatomic) IBOutlet UILabel *userName;



- (void)bind:(GTLYouTubeVideo *)video;
@end
