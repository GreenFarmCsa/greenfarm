

#import "UMJsApi4Ruler.h"
#import "GreenFarm_ios-Swift.h"
#import "LiveNavigationController.h"

@implementation UMJsApi4Ruler

- (void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback {
    self.callBack = callback;
    RulerARProViewController *rulerVC = [[RulerARProViewController alloc]init];
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(receiveArea:) name:@"areaValueNotify" object:nil];

    UIViewController * h5VC = context.currentViewController;
    LiveNavigationController * navc = [[LiveNavigationController alloc] initWithRootViewController:rulerVC];
    navc.supportRotation = NO;
    navc.horizontal = NO;
    navc.modalPresentationStyle = UIModalPresentationFullScreen;
    [navc setNavigationBarHidden:YES];
    [h5VC presentViewController:navc animated:NO completion:nil];
}

- (void)receiveArea:(NSNotification *)notify {
    NSString *result = notify.object;
    if (![result containsString:@"m"]) {
        self.callBack(@"0");
    }
    NSArray *arr = [result componentsSeparatedByString:@"m"];
    if (arr.count>0) {
        self.callBack(arr[0]);
    } else {
        self.callBack(@"0");
    }
}

@end
