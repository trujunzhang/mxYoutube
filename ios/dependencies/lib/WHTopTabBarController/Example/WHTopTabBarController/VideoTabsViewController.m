#import "VideoTabsViewController.h"

#import "FirstViewController.h"
#import "SecondViewController.h"
#import "ThirdViewController.h"


@interface VideoTabsViewController ()

@end


@implementation VideoTabsViewController

- (instancetype)init {
   self = [super init];
   if (self) {
      // Do any additional setup after loading the view.
      self.verticalControllers = nil;

      [self setupUI];
//      self.horizontalControllers = [self.defaultController copy];

      [self removeInfoViewController:nil];
//      self.selectedIndex = 2;
   }
   return self;
}


- (void)setupUI {
   self.defaultController = [[NSMutableArray alloc] init];

   [self.defaultController addObject:[[FirstViewController alloc] init]];
   [self.defaultController addObject:[[SecondViewController alloc] init]];
   [self.defaultController addObject:[[ThirdViewController alloc] init]];

//   self.viewControllers = self.defaultController;

//   self.tabBar.maximumTabWidth = 64.0f;
}


- (void)addInfoViewController:(UIViewController *)controller {
   controller.view.frame = [self getContainControllerRect];
   self.horizontalControllers = nil;
   if (self.verticalControllers)
      return;


   self.verticalControllers = [NSMutableArray arrayWithArray:self.viewControllers];
   [self.verticalControllers insertObject:controller atIndex:0];

   self.oldSelectedIndex = self.selectedIndex;
   self.viewControllers = self.verticalControllers;

   self.selectedIndex = self.oldSelectedIndex + 1;
}


- (void)removeInfoViewController:(UIViewController *)controller {
   self.verticalControllers = nil;
   if (self.horizontalControllers)
      return;

   self.horizontalControllers = [self.defaultController copy];

   self.oldSelectedIndex = self.selectedIndex;
   self.viewControllers = self.horizontalControllers;

   if (self.oldSelectedIndex == 1) {
      self.selectedIndex = 2;
   }
   if (self.oldSelectedIndex == 0) {
      self.selectedIndex = 2;
   } else {
      self.selectedIndex = self.oldSelectedIndex - 1;
   }
}


@end
