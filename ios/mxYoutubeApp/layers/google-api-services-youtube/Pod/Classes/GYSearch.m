//
//  Search.m
//  IOSTemplate
//
//  Created by djzhang on 9/25/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "GYSearch.h"
#import "GTLYouTubeSearchResult.h"
#import "GTLYouTubeResourceId.h"
#import "GTLYouTubeVideo.h"

static GYSearch * instance = nil;


@implementation GYSearch

+ (GYSearch *)getInstance {
   @synchronized (self) {
      if (instance == nil) {
         NSLog(@"initializing");
         instance = [[self alloc] init];
      }
      NSLog(@"Address: %p", instance);
   }
   return (instance);
}


- (instancetype)init {
   self = [super init];
   if (self) {
      self.youTubeService = [[GTLServiceYouTube alloc] init];
      self.youTubeService.APIKey = youtube_apikey;
   }

   return self;
}


- (NSArray *)searchByQueryWithQueryTerm:(NSString *)queryTerm completionHandler:(YoutubeResponseBlock)responseHandler errorHandler:(ErrorResponseBlock)errorHandler {
   YoutubeResponseBlock completion = ^(NSArray * array) {
       // 02 Search Videos by videoIds
       [self searchVideoByVideoIds:array completionHandler:(YoutubeResponseBlock) responseHandler];
   };
   ErrorResponseBlock error = ^(NSError * error) {
       if(error){
          errorHandler(error);
       }
   };
   // 01: Search videoIds by queryTerm
   [self fetchSearchListWithQueryTerm:queryTerm completionHandler:completion errorHandler:error];

   return nil;
}


- (void)searchVideoByVideoIds:(NSArray *)searchResultList completionHandler:(YoutubeResponseBlock)responseHandler errorHandler:(ErrorResponseBlock)errorHandler {
   NSMutableArray * videoIds = [[NSMutableArray alloc] init];

   if (searchResultList) {
      // Merge video IDs
      for (GTLYouTubeSearchResult * searchResult in searchResultList) {
         [videoIds addObject:searchResult.identifier.videoId];
      }
      [self fetchVideoListWithVideoId:[videoIds componentsJoinedByString:@","]
                    completionHandler:responseHandler
                         errorHandler:errorHandler];
   }
}


- (void)fetchSearchListWithQueryTerm:(NSString *)queryTerm
                   completionHandler:(YoutubeResponseBlock)completion
                        errorHandler:(ErrorResponseBlock)errorBlock {
   GTLServiceYouTube * service = self.youTubeService;

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
                               // The contentDetails of the response has the playlists available for "my channel".
                               if ([[resultList items] count] > 0) {
                                  completion([resultList items]);
                               }
                               errorBlock(error);
                               _searchListTicket = nil;
                           }];
}


- (void)fetchVideoListWithVideoId:(NSString *)videoId
                completionHandler:(YoutubeResponseBlock)completion
                     errorHandler:(ErrorResponseBlock)errorBlock {
   GTLServiceYouTube * service = self.youTubeService;

   GTLQueryYouTube * query = [GTLQueryYouTube queryForVideosListWithPart:@"snippet,contentDetails, statistics"];
   query.identifier = videoId;

   _searchListTicket = [service executeQuery:query
                           completionHandler:^(GTLServiceTicket * ticket,
                            GTLYouTubeSearchListResponse * resultList,
                            NSError * error) {
                               // The contentDetails of the response has the playlists available for "my channel".
                               if ([[resultList items] count] > 0) {
                                  completion([resultList items]);
                               }
                               errorBlock(error);
                               _searchListTicket = nil;
                           }];
}


@end
