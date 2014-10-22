
extern NSString * const MSG_INIT;
extern NSString * const MSG_DETECT;
extern NSString * const MSG_PLAYLIST;
extern NSString * const MSG_TOKEN;
extern NSString * const MSG_LO_BAND;
extern NSString * const MSG_HI_BAND;
extern NSString * const MSG_ERROR_TITLE;
extern NSString * const MSG_ERROR_MSG;

@interface YQYoutubeTaskInfo : NSObject {
  NSString * mMsgInit;
  NSString * mMsgDetect;
  NSString * mMsgPlaylist;
  NSString * mMsgToken;
  NSString * mMsgLowBand;
  NSString * mMsgHiBand;
  NSString * mMsgErrorTitle;
  NSString * mMsgError;
}

@property(nonatomic, copy) NSString * lYouTubeFmtQuality;
@property(nonatomic) BOOL showControllerOnStartup;

- (id) init;
@end
