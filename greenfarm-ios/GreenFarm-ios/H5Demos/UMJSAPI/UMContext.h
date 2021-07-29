

#import <Foundation/Foundation.h>
#import "UMWKWebViewJavascriptBridge.h"
@class UMScene;
NS_ASSUME_NONNULL_BEGIN

@interface UMContext : NSObject
@property (nonatomic ,strong)UIViewController *currentViewController;
@property (nonatomic ,strong)UMWKWebViewJavascriptBridge *bridge;
@property (nonatomic ,strong)UMScene *currentScene;
@end

NS_ASSUME_NONNULL_END
