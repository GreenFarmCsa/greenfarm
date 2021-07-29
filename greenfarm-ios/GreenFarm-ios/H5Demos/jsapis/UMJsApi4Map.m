
#import "UMJsApi4Map.h"
#import "MapViewController.h"
#import "LiveNavigationController.h"


@implementation UMJsApi4Map

- (void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback {
    
    self.callBack = callback;
//    NSArray *infos = @[@{@"name":@"abcdefghijklmnopqrstuvwsyz",@"loc":@[@50.82191692907181, @-0.13811767101287842]},@{@"name":@"wsx",@"loc":@[@60.82191692907181, @-0.13811767101287842]},@{@"name":@"rfv",@"loc":@[@70.82191692907181, @-0.13811767101287842]}];
    NSArray *infos = data[@"infos"];
    MapViewController *vc = [[MapViewController alloc]init];
    vc.infos = infos;
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(receiveMapValue:) name:@"MapValueNotify" object:nil];
    UIViewController * h5VC = context.currentViewController;
    LiveNavigationController * navc = [[LiveNavigationController alloc] initWithRootViewController:vc];
    navc.supportRotation = NO;
    navc.horizontal = NO;
    navc.modalPresentationStyle = UIModalPresentationFullScreen;
    [navc setNavigationBarHidden:YES];
    [h5VC presentViewController:navc animated:NO completion:nil];
    
}

- (void)receiveMapValue:(NSNotification *)notify {
    self.callBack(notify.object);

}
@end
