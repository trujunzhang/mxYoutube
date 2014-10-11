//
//    WHTopTabBarController
//
//    This code is distributed under the terms and conditions of the MIT license.
//
//    Copyright (c) 2012 Jin Budelmann.
//    http://www.bitcrank.com
//
//    Permission is hereby granted, free of charge, to any person obtaining a copy of this 
//    software and associated documentation files (the "Software"), to deal in the Software 
//    without restriction, including without limitation the rights to use, copy, modify, merge, 
//    publish, distribute, sublicense, and/or sell copies of the Software, and to permit 
//    persons to whom the Software is furnished to do so, subject to the following conditions:
//
//    The above copyright notice and this permission notice shall be included in all copies or 
//    substantial portions of the Software.
//
//    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
//    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
//    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
//    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
//    OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
//    DEALINGS IN THE SOFTWARE.
//

#import <UIKit/UIKit.h>

#import "WHTopTabBar.h"
#import "WHTopTab.h"
#import "UITabBarItem+WHTarbarAdditions.h"

@class WHTopTabBar;
@protocol JBTopTabBarDelegate;
@protocol JBTopTabBarControllerDelegate;


@interface WHTopTabBarController : UIViewController<JBTopTabBarDelegate> {
   __unsafe_unretained id<JBTopTabBarControllerDelegate> _delegate;

   NSArray * _viewControllers;
   __unsafe_unretained UIViewController * _selectedViewController;
}

@property(nonatomic, readonly, strong) WHTopTabBar * tabBar;
@property(nonatomic, strong) NSArray * viewControllers;

@property(nonatomic, unsafe_unretained) UIViewController * selectedViewController;
@property(nonatomic) NSUInteger selectedIndex;

@property(nonatomic, unsafe_unretained) id<JBTopTabBarControllerDelegate> delegate;

- (CGRect)getContainControllerRect;

// These are add or remove controller dynamic.
- (void)addInfoViewToController:(UIViewController *)controller;
- (void)removeInfoViewFromController:(UIViewController *)controller;
@end


@protocol JBTopTabBarControllerDelegate<NSObject>
@optional
// These are aimed to behave the same as the equivalent iOS 3.0+ UITabBarControllerDelegate methods
- (BOOL)tabBarController:(WHTopTabBarController *)tabBarController shouldSelectViewController:(UIViewController *)viewController;
- (void)tabBarController:(WHTopTabBarController *)tabBarController didSelectViewController:(UIViewController *)viewController;

// These are not called yet by the WHTopTabBarController
- (void)tabBarController:(WHTopTabBarController *)tabBarController willBeginCustomizingViewControllers:(NSArray *)viewControllers;
- (void)tabBarController:(WHTopTabBarController *)tabBarController willEndCustomizingViewControllers:(NSArray *)viewControllers changed:(BOOL)changed;
- (void)tabBarController:(WHTopTabBarController *)tabBarController didEndCustomizingViewControllers:(NSArray *)viewControllers changed:(BOOL)changed;

@end
