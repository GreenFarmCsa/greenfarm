

#import <UIKit/UIKit.h>
#import <WebKit/WebKit.h>
@class UMScene;
NS_ASSUME_NONNULL_BEGIN

@interface UMH5BaseViewController : UIViewController
@property (nonatomic ,strong)WKWebView *wkWebView;

@property (nonatomic, strong) UMScene *scene;

@property(nonatomic, strong) NSDictionary   *expandParams;
@property (nonatomic ,strong) NSString *url;
@property (nonatomic ,strong) NSString *localName;

//-(instancetype)initWithUrl:(NSString *)url;


- (void)reload;



- (void)goback;

- (void)goForward;
@end

NS_ASSUME_NONNULL_END
