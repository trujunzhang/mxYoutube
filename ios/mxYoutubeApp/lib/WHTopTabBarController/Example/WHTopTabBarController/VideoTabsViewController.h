#import <UIKit/UIKit.h>
#import "WHTopTabBarController.h"


@class VideoDetailViewController;


@interface VideoTabsViewController : WHTopTabBarController

@property(nonatomic, strong) NSMutableArray * defaultController;
@property(nonatomic, strong) NSMutableArray * horizontalControllers;
@property(nonatomic, strong) NSMutableArray * verticalControllers;

@property(nonatomic) NSUInteger oldSelectedIndex;

@end
