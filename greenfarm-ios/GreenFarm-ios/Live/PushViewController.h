//
//  PushViewController.h



#import <UIKit/UIKit.h>

@interface PushViewController : UIViewController
@property (nonatomic, strong) NSString *pushURL;

@property (nonatomic, strong) void(^closeLiveblock)(void);
@end

