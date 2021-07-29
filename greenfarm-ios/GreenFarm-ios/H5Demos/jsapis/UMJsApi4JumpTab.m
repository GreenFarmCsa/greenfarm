//
//  UMJsApi4JumpTab.m
//  umap-demos-nativeH5
//
//  Created by WxW on 2021/6/4.
//

#import "UMJsApi4JumpTab.h"

@implementation UMJsApi4JumpTab
-(void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback{
    context.currentViewController.tabBarController.selectedIndex = [data[@"index"] integerValue];
    //h5回调
    callback(@"success");
    [context.bridge callHandler:@"jumpTab" data:@{@"index":@"selected"} responseCallback:^(id responseData) {
        NSLog(@"%@",responseData);
    }];
}
@end
