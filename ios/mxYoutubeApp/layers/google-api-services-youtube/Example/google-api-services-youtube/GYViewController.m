//
//  GYViewController.m
//  google-api-services-youtube
//
//  Created by wanghaogithub720 on 09/25/2014.
//  Copyright (c) 2014 wanghaogithub720. All rights reserved.
//

#import "GYViewController.h"
#import "GYSearch.h"


@interface GYViewController ()

@end

@implementation GYViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.

   [GYSearch searchByQueryWithQueryTerm:@"sketch3"];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
