#import "ListViewController.h"


@implementation ListViewController

- (void)dealloc {
//	NSLog(@"dealloc %@", self);
}


- (void)viewDidLoad {
   [super viewDidLoad];
//	NSLog(@"%@ viewDidLoad", self.title);
}


- (void)viewWillAppear:(BOOL)animated {
   [super viewWillAppear:animated];
//	NSLog(@"%@ viewWillAppear", self.title);
}


- (void)viewDidAppear:(BOOL)animated {
   [super viewDidAppear:animated];
//	NSLog(@"%@ viewDidAppear", self.title);
}


- (void)viewWillDisappear:(BOOL)animated {
   [super viewWillDisappear:animated];
//	NSLog(@"%@ viewWillDisappear", self.title);
}


- (void)viewDidDisappear:(BOOL)animated {
   [super viewDidDisappear:animated];
//	NSLog(@"%@ viewDidDisappear", self.title);
}


- (void)willMoveToParentViewController:(UIViewController *)parent {
   [super willMoveToParentViewController:parent];
//	NSLog(@"%@ willMoveToParentViewController %@", self.title, parent);
}


- (void)didMoveToParentViewController:(UIViewController *)parent {
   [super didMoveToParentViewController:parent];
//	NSLog(@"%@ didMoveToParentViewController %@", self.title, parent);
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
   return YES;
}


- (void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration {
   [super willRotateToInterfaceOrientation:toInterfaceOrientation duration:duration];
//	NSLog(@"%@ willRotateToInterfaceOrientation", self.title);
}


#pragma mark - UITableViewDataSource


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
   return 50;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
   static NSString * CellIdentifier = @"Cell";

   UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
   if (cell == nil)
      cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];

   cell.selectionStyle = UITableViewCellSelectionStyleGray;
   cell.textLabel.text = [NSString stringWithFormat:@"%@ - Row %d", self.title, indexPath.row];
   return cell;
}


#pragma mark - UITableViewDelegate


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {

}


@end