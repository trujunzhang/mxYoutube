//
//  VimeoVideo.h
//  YKMediaHelper
//
//  Created by Yas Kuraishi on 3/13/14.
//  Copyright (c) 2014 Yas Kuraishi. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "YKVideo.h"

@interface YKVimeoVideo : NSObject<YKVideo>

/**
    Vimeo video url
 */
@property (nonatomic, strong) NSURL *contentURL;

/**
    Videos found for above mentioned content url. 
    Available after parseWithCompletion is executed.
 */
@property (nonatomic, strong) NSDictionary *videos;

/**
    Thumbnails found for this video. 
    Available after parseWithCompletion is executed.
 */
@property (nonatomic, strong) NSDictionary *thumbs;

@end
