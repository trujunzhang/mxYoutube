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


+ (UIColor *)resolutionByType:(NSUInteger)type {
   NSArray * array = [AppResolutionHelper getResolutionImageNameArray];
   return nil;
}


+ (NSArray *)getResolutionImageNameArray {
   return [NSArray arrayWithObjects:
    @"Default@2x", // 640 x 960
    @"Default-568h@2x", //640 x 1,136
    @"Default-Landscape@2x~ipad",//1,024 x 768
    @"Default-Landscape~ipad",//  2,048 x 1,536
    @"Default-Portrait@2x~ipad",// 1,024 x 768
    @"Default-Portrait~ipad",// 2,048 x 1,536
     nil];

}


@end
