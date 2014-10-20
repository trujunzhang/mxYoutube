#import "VideoDetailViewControlleriPad.h"

#import "VideoDetailPanel.h"

#import "WHTopTabBarController.h"

#import "YoutubeGridLayoutViewController.h"
#import "IpadGridViewCell.h"
#import "GTLYouTubeVideo.h"
#import "GTLYouTubeVideoSnippet.h"


@interface VideoDetailViewControlleriPad ()

@property(strong, nonatomic) IBOutlet UIView * videoPlayView;
@property(strong, nonatomic) IBOutlet UIView * detailView;

@property(strong, nonatomic) IBOutlet UIView * tabbarView;
@end


@implementation VideoDetailViewControlleriPad

#pragma mark -
#pragma mark - UIView cycle


- (instancetype)initWithDelegate:(id<IpadGridViewCellDelegate>)delegate video:(GTLYouTubeVideo *)video {
   self = [super init];
   if (self) {
      self.delegate = delegate;
      self.video = video;
   }

   return self;
}


- (void)viewDidLoad {
   [super viewDidLoad];

   // Do any additional setup after loading the view, typically from a nib.
   [self initViewControllers];
   [self setupPlayer:self.videoPlayView];

   self.title = self.video.snippet.title;
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.

}


#pragma mark -
#pragma mark - setup UIView


- (void)initViewControllers {
   // 1
   self.firstViewController = [[UIViewController alloc] init];
   self.secondViewController = [[UIViewController alloc] init];
//   self.thirdViewController = [[YoutubeGridLayoutViewController alloc] initWithVideoList:[YoutubeDataHelper getVideoList]];
   self.thirdViewController = [[YoutubeGridLayoutViewController alloc] init];
   self.thirdViewController.delegate = self.delegate;
   self.thirdViewController.numbersPerLineArray = [NSArray arrayWithObjects:@"3", @"2", nil];
   self.thirdViewController.title = @"Suggestions";

   // 2
   self.videoDetailController = [[UIViewController alloc] init];
   self.videoDetailController.title = @"Info";

   VideoDetailPanel * videoDetailPanel = [[VideoDetailPanel alloc] init];

   self.videoDetailController.view = videoDetailPanel;
}


- (void)setupTabbarPanelInHorizontal:(UIView *)view {
   self.videoTabBarController = [[WHTopTabBarController alloc] init];

   NSMutableArray * array = [[NSMutableArray alloc] init];

   [array addObject:self.firstViewController];
   [array addObject:self.secondViewController];
   [array addObject:self.thirdViewController];

   self.videoTabBarController.viewControllers = array;
   self.videoTabBarController.selectedIndex = 2;

   self.videoTabBarController.view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   self.videoTabBarController.view.frame = view.bounds;

   [view addSubview:self.videoTabBarController.view];
}


- (void)setupTabbarPanelInVertical:(UIView *)view {
   self.videoTabBarController = [[WHTopTabBarController alloc] init];

   NSMutableArray * array = [[NSMutableArray alloc] init];

   [array addObject:self.videoDetailController];

   [array addObject:self.firstViewController];
   [array addObject:self.secondViewController];
   [array addObject:self.thirdViewController];

   self.videoTabBarController.viewControllers = array;
   self.videoTabBarController.selectedIndex = 3;

   self.videoTabBarController.view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   self.videoTabBarController.view.frame = view.bounds;

   [view addSubview:self.videoTabBarController.view];
}


- (void)setupDetailPanel:(UIView *)view {
   self.videoDetailController.view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   self.videoDetailController.view.frame = view.bounds;

   [view addSubview:self.videoDetailController.view];
}


- (void)setupPlayer:(UIView *)view {
// Make a URL
   NSURL * url = [NSURL URLWithString:@"http://www.ebookfrenzy.com/ios_book/movie/movie.mov"];

// NSURL *  url = [NSURL URLWithString:@"http://r10---sn-a5m7ln7d.googlevideo.com/videoplayback?yms=y9cO_gSkL8Q&id=o-AKC3f3MLPfGD_uD3m_UX-5X_62BXiJdbtiGrCBR47MYF&upn=jsjWp7OEYn0&mm=31&fexp=907257%2C909721%2C912124%2C914072%2C914951%2C916638%2C916941%2C927622%2C930666%2C931348%2C931983%2C932404%2C934030%2C945258%2C946013%2C947209%2C952302%2C953801&ms=au&mv=m&mt=1413168650&dnc=1&itag=36&key=yt5&ip=67.229.65.158&el=watch&initcwndbps=13998750&signature=4C5F042695ACBC63627CE80DEEC7C847E392F359.03F05EF5547EC01AC5C32406283E002931C19AEC&sver=3&app=youtube_mobile&expire=1413190338&source=youtube&ipbits=0&ratebypass=yes&sparams=id%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmm%2Cms%2Cmv%2Cratebypass%2Csource%2Cupn%2Cexpire"];

   self.moviePlayer = [[MPMoviePlayerController alloc] initWithContentURL:url];

   self.moviePlayer.view.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
   self.moviePlayer.view.frame = view.bounds;

//   [self.moviePlayer play];
//   [view addSubview:self.moviePlayer.view];
}


#pragma mark -
#pragma mark Rotation stuff


- (BOOL)shouldAutorotate {
   return YES;
}


- (NSUInteger)supportedInterfaceOrientations {
   if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
      return UIInterfaceOrientationMaskPortrait;
   }
   return UIInterfaceOrientationMaskAll;
}


- (UIInterfaceOrientation)preferredInterfaceOrientationForPresentation {
   return UIInterfaceOrientationLandscapeLeft;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)orientation {
   if ((orientation == UIInterfaceOrientationLandscapeRight) || (orientation == UIInterfaceOrientationLandscapeLeft)) {
      return YES;
   }

   return NO;
}


- (void)viewDidLayoutSubviews {
   [super viewDidLayoutSubviews];

   [self updateLayout:[UIApplication sharedApplication].statusBarOrientation];
}


- (void)updateLayout:(UIInterfaceOrientation)toInterfaceOrientation {
   BOOL isPortrait = (toInterfaceOrientation == UIInterfaceOrientationPortrait) || (toInterfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);
   if (isPortrait) {
      [self setupVertical];
      [self setupTabbarPanelInVertical:self.tabbarView];
   } else {
      [self setupHorizontal];
      [self setupDetailPanel:self.detailView];
      [self setupTabbarPanelInHorizontal:self.tabbarView];
   }
}


- (void)setupHorizontal {
   CGFloat aWidth = self.view.frame.size.width;
   CGFloat aHeight = self.view.frame.size.height;

   CGRect rect = self.videoPlayView.frame;
   rect.size.width = aWidth / 2;
   rect.size.height = aHeight / 2;
   self.videoPlayView.frame = rect;

   [self.view addSubview:self.detailView];
   rect = self.detailView.frame;
   rect.origin.y = aHeight / 2;
   rect.size.width = aWidth / 2;
   rect.size.height = aHeight / 2;
   self.detailView.frame = rect;

   rect = self.tabbarView.frame;
   rect.origin.x = aWidth / 2;
   rect.origin.y = 0;
   rect.size.width = aWidth / 2;
   rect.size.height = aHeight;
   self.tabbarView.frame = rect;
}


- (void)setupVertical {
   CGFloat aWidth = self.view.frame.size.width;
   CGFloat aHeight = self.view.frame.size.height;

   CGRect rect = self.videoPlayView.frame;
   rect.size.width = aWidth;
   rect.size.height = aHeight / 2;
   self.videoPlayView.frame = rect;

   [self.detailView removeFromSuperview];

   rect = self.tabbarView.frame;
   rect.origin.x = 0;
   rect.origin.y = aHeight / 2;
   rect.size.width = aWidth;
   rect.size.height = aHeight / 2;
   self.tabbarView.frame = rect;
}


@end
