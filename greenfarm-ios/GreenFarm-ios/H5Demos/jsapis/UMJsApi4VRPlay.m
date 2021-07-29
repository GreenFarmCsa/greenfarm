//
//  UMJsApi4VRPlay.m


#import <Foundation/Foundation.h>
#import "UMJsApi4VRPlay.h"
#import "VRViewController.h"
#import "NSString+Extension.h"

@implementation UMJsApi4VRPlay

-(void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback{
    NSString *url = data[@"url"];
    if (![url isAvalidString]) {
        callback(@"Required parameter illegal");
    }
    UIViewController * h5VC = context.currentViewController;
    VRViewController * vrVC = [[VRViewController alloc] init];
    vrVC.vrURL = url;
    vrVC.modalPresentationStyle = UIModalPresentationFullScreen;
    [h5VC presentViewController:vrVC animated:YES completion:nil];
}

@end
