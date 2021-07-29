


#import <Foundation/Foundation.h>
#import "UMWebViewJavascriptBridgeBase.h"
#import <WebKit/WebKit.h>
NS_ASSUME_NONNULL_BEGIN

@interface UMWKWebViewJavascriptBridge : NSObject<WKNavigationDelegate, UMWebViewJavascriptBridgeBaseDelegate>
+ (instancetype)bridgeForWebView:(WKWebView*)webView;
+ (void)enableLogging;

- (void)registerHandler:(NSString*)handlerName handler:(UMJBHandler)handler;
- (void)removeHandler:(NSString*)handlerName;
- (void)callHandler:(NSString*)handlerName;
- (void)callHandler:(NSString*)handlerName data:(id)data;
- (void)callHandler:(NSString*)handlerName data:(id)data responseCallback:(UMJBResponseCallback)responseCallback;
- (void)reset;
- (void)setWebViewDelegate:(id)webViewDelegate;
- (void)disableJavscriptAlertBoxSafetyTimeout;
- (NSString*) _evaluateJavascript:(NSString*)javascriptCommand;
@end

NS_ASSUME_NONNULL_END
