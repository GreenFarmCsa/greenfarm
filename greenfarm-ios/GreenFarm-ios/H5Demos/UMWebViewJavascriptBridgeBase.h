

#import <Foundation/Foundation.h>
#define kOldProtocolScheme @"umjbscheme"
#define kNewProtocolScheme @"https"
#define kQueueHasMessage   @"__umjb_queue_message__"
#define kBridgeLoaded      @"__bridge_loaded__"

typedef void (^UMJBResponseCallback)(id responseData);
typedef void (^UMJBHandler)(id data, UMJBResponseCallback responseCallback);
typedef NSDictionary UMJBMessage;

@protocol UMWebViewJavascriptBridgeBaseDelegate <NSObject>
- (NSString*) _evaluateJavascript:(NSString*)javascriptCommand;
@end
NS_ASSUME_NONNULL_BEGIN

@interface UMWebViewJavascriptBridgeBase : NSObject
@property (weak, nonatomic) id <UMWebViewJavascriptBridgeBaseDelegate> delegate;
@property (strong, nonatomic) NSMutableArray* startupMessageQueue;
@property (strong, nonatomic) NSMutableDictionary* responseCallbacks;
@property (strong, nonatomic) NSMutableDictionary* messageHandlers;
@property (strong, nonatomic) UMJBHandler messageHandler;

+ (void)enableLogging;
+ (void)setLogMaxLength:(int)length;
- (void)reset;
- (void)sendData:(id)data responseCallback:(UMJBResponseCallback)responseCallback handlerName:(NSString*)handlerName;
- (void)flushMessageQueue:(NSString *)messageQueueString;
- (void)injectJavascriptFile;
- (BOOL)isWebViewJavascriptBridgeURL:(NSURL*)url;
- (BOOL)isQueueMessageURL:(NSURL*)urll;
- (BOOL)isBridgeLoadedURL:(NSURL*)urll;
- (void)logUnkownMessage:(NSURL*)url;
- (NSString *)webViewJavascriptCheckCommand;
- (NSString *)webViewJavascriptFetchQueyCommand;
- (void)disableJavscriptAlertBoxSafetyTimeout;
@end

NS_ASSUME_NONNULL_END
