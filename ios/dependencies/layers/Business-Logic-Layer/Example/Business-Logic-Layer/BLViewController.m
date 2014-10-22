//
//  BLViewController.m
//  Business-Logic-Layer
//
//  Created by wanghaogithub720 on 09/17/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import "BLViewController.h"
#import "GYSearch.h"
#import "SearchImplementation.h"


@interface BLViewController ()

@end

@implementation BLViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.

   self.view.backgroundColor = [UIColor clearColor]; // = transparent


   YoutubeResponseBlock completion = ^(NSArray * array) {
       NSString * debug = @"debug";
   };
   ErrorResponseBlock error = ^(NSError * error) {
       NSString * debug = @"debug";
   };
   [[SearchImplementation getInstance] searchByQueryWithQueryTerm:@"sketch3" completionHandler:completion  errorHandler:error];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
