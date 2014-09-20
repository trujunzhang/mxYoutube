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



#import "AppResolutionHelper.h"


@implementation AppResolutionHelper


+ (NSString *)resolutionNameByType:(NSUInteger)type isPortrait:(BOOL)portrait {
   if (portrait == NO) {
      type += 2;
   }
   NSArray * array = [AppResolutionHelper getResolutionImageNameArray];
   return array[type];
}

+ (UIColor *)resolutionByType:(NSUInteger)type isPortrait:(BOOL)portrait {
   if (portrait == NO) {
      type += 2;
   }
   NSArray * array = [AppResolutionHelper getResolutionImageNameArray];
   return [UIColor colorWithPatternImage:[UIImage imageNamed:array[type]]];
//   return [UIColor colorWithPatternImage:[UIImage imageNamed:@"Default-Landscape~ipad"]];
//   return [UIColor colorWithPatternImage:[UIImage imageNamed:@"Default-Portrait~ipad"]];

}


//enum {
//   UIDeviceResolution_Unknown = 0,
//   UIDeviceResolution_iPhoneStandard = 1,    // iPhone 1,3,3GS Standard Display  (320x480px)
//   UIDeviceResolution_iPhoneRetina35 = 2,    // iPhone 4,4S Retina Display 3.5"  (640x960px)
//   UIDeviceResolution_iPhoneRetina4 = 3,    // iPhone 5 Retina Display 4"       (640x1136px)
//   UIDeviceResolution_iPadStandard = 4,    // iPad 1,2 Standard Display        (1024x768px)
//   UIDeviceResolution_iPadRetina = 5     // iPad 3 Retina Display            (2048x1536px)
//};


+ (NSArray *)getResolutionImageNameArray {
   return [NSArray arrayWithObjects:
    @"",// {UIDeviceResolution_Unknown}
    @"",// {iPhone 1,3,3GS Standard Display  (320x480px)}
    @"Default", // 640 x 960
    //[3]
    @"Default-568h", //640 x 1,136
    @"Default-Portrait~ipad",// 768 x 1,024
    @"Default-Portrait~ipad",// 1,536 x 2,048
    @"Default-Landscape~ipad",//  1,024 x 768
    @"Default-Landscape~ipad",//2,048 x 1,536
     nil];

}


@end
