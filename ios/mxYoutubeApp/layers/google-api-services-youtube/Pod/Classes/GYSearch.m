//
//  Search.m
//  IOSTemplate
//
//  Created by djzhang on 9/25/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "GYSearch.h"


@implementation GYSearch

+ (NSArray *)searchByQueryWithQueryTerm:(NSString *)queryTerm {
   GYSearch * search = [[GYSearch alloc] init];

   YoutubeResponseBlock completion = ^(NSArray * array) {
       NSString * debug = @"debug";
   };
   ErrorResponseBlock error = ^(NSError * error) {
       NSString * debug = @"debug";
   };
   [search fetchSearchListWithQueryTerm:queryTerm completionHandler:completion errorHandler:error];

   return nil;
}


- (void)fetchSearchListWithQueryTerm:(NSString *)queryTerm
                   completionHandler:(YoutubeResponseBlock)completion
                        errorHandler:(ErrorResponseBlock)errorBlock {
   GTLServiceYouTube * service = [[GTLServiceYouTube alloc] init];

   service.APIKey = youtube_apikey;

   GTLQueryYouTube * query = [GTLQueryYouTube queryForSearchListWithPart:@"id,snippet"];
   query.q = queryTerm;
   query.type = @"video";

   // maxResults specifies the number of results per page.  Since we earlier
   // specified shouldFetchNextPages=YES, all results should be fetched,
   // though specifying a larger maxResults will reduce the number of fetches
   // needed to retrieve all pages.
   query.maxResults = search_maxResults; // NUMBER_OF_VIDEOS_RETURNED

   // We can specify the fields we want here to reduce the network
   // bandwidth and memory needed for the fetched collection.
   //
   // For example, leave query.fields as nil during development.
   // When ready to test and optimize your app, specify just the fields needed.
   // For example, this sample app might use
   //
   query.fields = @"items(id/videoId)";

   _searchListTicket = [service executeQuery:query
                            completionHandler:^(GTLServiceTicket * ticket,
                             GTLYouTubeSearchListResponse * resultList,
                             NSError * error) {
                                // Callback

                                // The contentDetails of the response has the playlists available for
                                // "my channel".
                                if ([[resultList items] count] > 0) {
                                   completion([resultList items]);
                                }
                                errorBlock(error);
                                _searchListTicket = nil;
                            }];
}


@end
