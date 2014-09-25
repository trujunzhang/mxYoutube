//
//  Search.h
//  IOSTemplate
//
//  Created by djzhang on 9/25/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "GTLUtilities.h"
#import "GTMHTTPUploadFetcher.h"
#import "GTMHTTPFetcherLogging.h"
#import "GTLServiceYouTube.h"
#import "GTLQueryYouTube.h"
#import "YoutubeConstants.h"
#import "GTLYouTubeSearchListResponse.h"
#import "GTLYouTubeChannel.h"
#import "GTLYouTubeChannelContentDetails.h"

@class GTLServiceYouTube;
@class GTLYouTubePlaylistItemListResponse;

typedef void (^YoutubeResponseBlock)(NSArray *  array);
typedef void (^ErrorResponseBlock)(NSError *  error);

@interface GYSearch : NSObject {
   GTLYouTubeChannelContentDetailsRelatedPlaylists * _myPlaylists;
   GTLServiceTicket * _searchListTicket;
   NSError * _channelListFetchError;

   GTLYouTubePlaylistItemListResponse * _playlistItemList;
   GTLServiceTicket * _playlistItemListTicket;
   NSError * _playlistFetchError;

   GTLServiceTicket * _uploadFileTicket;
   NSURL * _uploadLocationURL;  // URL for restarting an upload.

}
// Accessor for the app's single instance of the service object.
@property(nonatomic, strong) GTLServiceYouTube * youTubeService;


+(GYSearch *) getInstance;

- (NSArray *)searchByQueryWithQueryTerm:(NSString *)queryTerm completionHandler:(YoutubeResponseBlock)responseHandler errorHandler:(ErrorResponseBlock)errorHandler;

@end
