

#import <Foundation/Foundation.h>
#import "UMWKWebViewJavascriptBridge.h"
#import "UMContext.h"
//@class UMContext;
NS_ASSUME_NONNULL_BEGIN

@interface UMJsApiHandler : NSObject


- (void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback;
@end

NS_ASSUME_NONNULL_END
