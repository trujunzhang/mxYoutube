
@interface YQYouTubeUtility : NSObject {
}

+ (YQYoutubeQuality *)getFinalUri:(NSString *)string;
+ (NSString *)getUrlByQuality:(YQYoutubeQuality *)youtubeQuality pFallback:(BOOL)pFallback pYouTubeFmtQuality:(NSString *)pYouTubeFmtQuality;
@end
