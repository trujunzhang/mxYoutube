
/**
 * In order to imbue the {@link com.google.android.libraries.mediaframework.layeredvideo.layer.PlaybackControlLayer}
 * with the ability make the player fullscreen, a {@link VideoInfoTaskCallback} must be assigned to it. The
 * {@link VideoInfoTaskCallback} implementation is responsible for hiding/showing the other views on the screen when
 * the player enters/leaves fullscreen mode.
 */

@protocol YQVideoInfoTaskCallback <NSObject>
- (void) startYoutubeTask:(NSString *)videoUrl;


@end
