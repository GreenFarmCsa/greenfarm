
#import "UMJsApi4MapSelect.h"
#import "SelectMapViewController.h"
#import "LiveNavigationController.h"

@implementation UMJsApi4MapSelect

- (void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback {
    self.callBack = callback;
    SelectMapViewController *vc = [[SelectMapViewController alloc]init];
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(receiveMapSelectValue:) name:@"MapSelectValueNotify" object:nil];
    UIViewController * h5VC = context.currentViewController;
    LiveNavigationController * navc = [[LiveNavigationController alloc] initWithRootViewController:vc];
    navc.supportRotation = NO;
    navc.horizontal = NO;
    navc.modalPresentationStyle = UIModalPresentationFullScreen;
    [navc setNavigationBarHidden:YES];
    [h5VC presentViewController:navc animated:NO completion:nil];
    
}

- (void)receiveMapSelectValue:(NSNotification *)notify {
    self.callBack(notify.object);

}
@end
