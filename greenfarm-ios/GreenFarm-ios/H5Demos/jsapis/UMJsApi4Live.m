
#import "UMJsApi4Live.h"
#import "PushViewController.h"
#import "PullViewController.h"
#import "LiveNavigationController.h"
#import "LiveRoomModel.h"
#import "NSString+Extension.h"
@implementation UMJsApi4Live
-(void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback{
    int index = [data[@"index"] integerValue];
    NSString *urlString = data[@"url"];
    
    if (![urlString isAvalidString]) {
        callback(@"Required parameter illegal");
    }
    
    UIViewController * h5VC = context.currentViewController;
    if (index == 0) {
        PushViewController * vc = [[PushViewController alloc]init];
        vc.pushURL = urlString;
        vc.closeLiveblock = ^{
            callback(@"live close");
        };
        LiveNavigationController * navc = [[LiveNavigationController alloc] initWithRootViewController:vc];
        navc.supportRotation = NO;
        navc.horizontal = NO;
        navc.modalPresentationStyle = UIModalPresentationFullScreen;
        [navc setNavigationBarHidden:YES];
        [h5VC presentViewController:navc animated:YES completion:nil];
    } else {
        NSString *nickName = data[@"username"];
        NSString *picUrl = data[@"imgurl"];
        NSString *farmName = data[@"location"];
        
        if ((![nickName isAvalidString])|| (![farmName isAvalidString])) {
            callback(@"Required parameter illegal");
        }
        
        LiveRoomModel *model = [[LiveRoomModel alloc]init];
        model.nickName = nickName;
        model.farmName = farmName;
        model.picUrl = picUrl;
        model.url = urlString;
        
        PullViewController * vc = [[PullViewController alloc]init];
        vc.model = model;
        vc.modalPresentationStyle = UIModalPresentationFullScreen;
        [h5VC presentViewController:vc animated:YES completion:nil];
    }
    
    
}
@end
