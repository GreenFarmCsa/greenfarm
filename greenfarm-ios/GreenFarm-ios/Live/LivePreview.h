//
//  LivePreview.h

#import <UIKit/UIKit.h>

@interface LivePreview : UIView
@property (nonatomic, strong) void(^personalblock)(void);
@property (nonatomic, strong) NSString *pushURL;
@end
