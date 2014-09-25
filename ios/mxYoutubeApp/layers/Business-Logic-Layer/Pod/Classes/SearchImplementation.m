//
//  SearchImplementation.m
//  IOSTemplate
//
//  Created by djzhang on 9/25/14.
//  Copyright (c) 2014 djzhang. All rights reserved.
//

#import "SearchImplementation.h"
#import "GYSearch.h"

SearchImplementation * instance;


@implementation SearchImplementation

+ (SearchImplementation *)getInstance {
   @synchronized (self) {
      if (instance == nil) {
         NSLog(@"initializing");
         instance = [[self alloc] init];
      }
      NSLog(@"Address: %p", instance);
   }
   return (instance);
}


- (void)searchByQueryWithQueryTerm:(NSString *)queryTerm completionHandler:(YoutubeResponseBlock)responseHandler errorHandler:(ErrorResponseBlock)errorHandler {
   [[GYSearch getInstance] searchByQueryWithQueryTerm:queryTerm
                                    completionHandler:responseHandler
                                         errorHandler:errorHandler];
}


@end
