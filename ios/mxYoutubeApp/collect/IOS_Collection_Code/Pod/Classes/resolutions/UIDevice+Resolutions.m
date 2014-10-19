/*******************************************************************************
* This file is part of the UIDevice+Resolutions project.
*
* Copyright (c) 2012 C4M PROD.
*
* UIDevice+Resolutions is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* UIDevice+Resolutions is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with UIDevice+Resolutions. If not, see <http://www.gnu.org/licenses/lgpl.html>.
*
* Contributors:
* C4M PROD
******************************************************************************/



#import "UIDevice+Resolutions.h"


@implementation UIDevice (Resolutions)


+ (UIDeviceResolution)resolution {
   UIDeviceResolution resolution = UIDeviceResolution_Unknown;
   UIScreen * mainScreen = [UIScreen mainScreen];
   CGFloat scale = ([mainScreen respondsToSelector:@selector(scale)] ? mainScreen.scale : 1.0f);
   CGFloat pixelWidth = (CGRectGetWidth(mainScreen.bounds) * scale);
   CGFloat pixelHeight = (CGRectGetHeight(mainScreen.bounds) * scale);

   if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
      if (scale == 2.0f) {
         if ([self checkScreenWithWidth:pixelWidth withHeight:pixelHeight value1:640.0f value2:960.0f]) // 640 x 960
            resolution = UIDeviceResolution_iPhoneRetina35;
         else if ([self checkScreenWithWidth:pixelWidth
                                  withHeight:pixelHeight
                                      value1:640.0f
                                      value2:1136.0f]) //640 x 1,136
            resolution = UIDeviceResolution_iPhoneRetina4;

      } else if (scale == 1.0f && pixelWidth == 480.0f)
         resolution = UIDeviceResolution_iPhoneStandard;

   } else {
      if (scale == 2.0f && [self checkScreenWithWidth:pixelWidth
                                           withHeight:pixelHeight
                                               value1:1536.0f
                                               value2:2048.0f]) { //1,536 x 2,048
         resolution = UIDeviceResolution_iPadRetina;

      } else if (scale == 1.0f && [self checkScreenWithWidth:pixelWidth
                                                  withHeight:pixelHeight
                                                      value1:768.0f
                                                      value2:1024.0f]) { //768 x 1,024
         resolution = UIDeviceResolution_iPadStandard;
      }
   }

   return resolution;
}


+ (BOOL)checkScreenWithWidth:(CGFloat)pixelWidth withHeight:(CGFloat)pixelHeight value1:(float)value1 value2:(double)value2 {
   if (pixelWidth == value1 && pixelHeight == value2) {
      return YES;
   }
   else if (pixelWidth == value2 && pixelHeight == value1) {
      return YES;
   }
   return NO;
}


//@"Default", // 640 x 960
////[3]
//@"Default-568h", //640 x 1,136
//@"Default-Portrait~ipad",// 768 x 1,024
//@"Default-Portrait~ipad",// 1,536 x 2,048
////    @"Default-Landscape~ipad",//  1,024 x 768
////    @"Default-Landscape~ipad",//2,048 x 1,536


@end
