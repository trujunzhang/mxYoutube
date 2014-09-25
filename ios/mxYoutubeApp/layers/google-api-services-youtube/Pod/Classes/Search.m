//
//  Search.m
//  IOSTemplate
//
//  Created by djzhang on 9/25/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "Search.h"
#import "GTLYouTubePlaylistItemListResponse.h"


@implementation Search

+ (NSArray *)searchByQueryWithQueryTerm:(NSString *)queryTerm {
   Search * search= [[Search alloc] init];
   [search fetchSearchListWithQueryTerm:queryTerm];

   return nil;
}


- (void)fetchSearchListWithQueryTerm:(NSString *)queryTerm {
   _myPlaylists = nil;
   _channelListFetchError = nil;

   GTLServiceYouTube * service = self.youTubeService;

   service.APIKey = youtube_apikey;

//   GTLQueryYouTube * query = [GTLQueryYouTube queryForChannelsListWithPart:@"contentDetails"];
   GTLQueryYouTube * query = [GTLQueryYouTube queryForSearchListWithPart:@"id,snippet"];
   query.q = queryTerm;
   query.type = @"video";
   query.fields = @"items(id/videoId)";

//   query.mine = YES;

   // maxResults specifies the number of results per page.  Since we earlier
   // specified shouldFetchNextPages=YES, all results should be fetched,
   // though specifying a larger maxResults will reduce the number of fetches
   // needed to retrieve all pages.
   query.maxResults = 15;

   // We can specify the fields we want here to reduce the network
   // bandwidth and memory needed for the fetched collection.
   //
   // For example, leave query.fields as nil during development.
   // When ready to test and optimize your app, specify just the fields needed.
   // For example, this sample app might use
   //
   // query.fields = @"kind,etag,items(id,etag,kind,contentDetails)";

   _channelListTicket = [service executeQuery:query
                            completionHandler:^(GTLServiceTicket * ticket,
                             GTLYouTubeSearchListResponse * channelList,
                             NSError * error) {
                                // Callback

                                // The contentDetails of the response has the playlists available for
                                // "my channel".
                                if ([[channelList items] count] > 0) {
                                   GTLYouTubeChannel * channel = channelList[0];
                                   _myPlaylists = channel.contentDetails.relatedPlaylists;
                                }
                                _channelListFetchError = error;
                                _channelListTicket = nil;
                            }];
}


@end
