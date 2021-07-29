

#import "UMKernel.h"
#import <UIKit/UIKit.h>
@class UMSceneParam;
@class UMView;
NS_ASSUME_NONNULL_BEGIN

@interface UMScene : UMKernel
@property(nonatomic, strong) UMView *umView;

@property(nonatomic, strong) UMSceneParam *createParam;

@property(nonatomic, strong) UIViewController *viewController;

@property(nonatomic, strong) NSArray *dynamicJsUrls;

@property(nonatomic, strong) NSString *startupParamsJs;

@property(nonatomic, strong) NSString *codeSnippets4InsertAlipayJsBridgeFile;

@property(nonatomic, strong) NSArray *independenceUserScripts;

@property(nonatomic, strong) NSArray *relayOnAlipayJsBridgeUserScripts;


+ (instancetype)sceneWithSceneParam:(UMSceneParam *)sceneParam parentObject:(UMKernel *)parentObject;
@end

NS_ASSUME_NONNULL_END


@interface UMSceneParam : NSObject

@property(nonatomic, strong) Class          viewControllerCls; // default is PSDViewController
@property(nonatomic, strong) Class          contentViewCls; // default is PSDWebVeiw
@property(nonatomic, copy) NSString         *url;
@property(nonatomic, strong) NSDictionary   *expandParams;

@end
