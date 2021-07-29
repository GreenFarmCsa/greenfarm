

#import "UMH5BaseViewController.h"

#import "UMWKWebViewJavascriptBridge.h"
#import "umContext.h"
#import <objc/runtime.h>
#import "UMJsApiHandler.h"
#import "UMScene.h"
#define KmainScreenWidth [UIScreen mainScreen].bounds.size.width
#define KmainScreenHeight [UIScreen mainScreen].bounds.size.height
#define KmainStatusBarHeight [[UIApplication sharedApplication] statusBarFrame].size.height
#define KmainNavBarHeight 44.0
#define KmainTopHeight (KmainStatusBarHeight + KmainNavBarHeight)
#define KmainTabBarHeight ([[UIApplication sharedApplication] statusBarFrame].size.height>20.0?83.0:49.0)
@interface UMH5BaseViewController ()<WKNavigationDelegate, WKUIDelegate,WKScriptMessageHandler>
@property (nonatomic ,strong)UMWKWebViewJavascriptBridge *bridge;

@property (nonatomic, strong) UIProgressView * progressView;
@end

@implementation UMH5BaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    //Start local web server
    [self setupWebView];
    [self.view addSubview:self.progressView];
    [self addUMObserver];
    
    //Local request which use local resource
    [self loadRequest];
    
    
    [UMWKWebViewJavascriptBridge enableLogging];
    _bridge = [UMWKWebViewJavascriptBridge bridgeForWebView:_wkWebView];
    [_bridge setWebViewDelegate:self];
    
    
    UMContext *context = [[UMContext alloc] init];
    context.currentViewController = self;
    context.bridge = _bridge;
    NSArray *arr = [self plistArray];
    SEL selector = @selector(registerJsapiWithName:context:);
    for (NSDictionary *dict in arr) {
        Class class = NSClassFromString(dict[@"name"]);
        id jsapiModel = [[class alloc] init];
        [jsapiModel performSelector:selector withObject:dict[@"jsApi"] withObject:context];
    }
    NSString *jspath = [[NSBundle mainBundle]pathForResource:@"um_startParas" ofType:@"js"];
    NSString *str = [NSString stringWithContentsOfFile:jspath encoding:NSUTF8StringEncoding error:nil];
    [_bridge _evaluateJavascript:str];
}
-(void)clickBack{
    [self goback];
}

//- (void)viewWillAppear:(BOOL)animated {
//    [super viewWillAppear:animated];
//    [self loadRequest];
//}

#pragma mark - Getter
- (UIProgressView *)progressView {
    if (!_progressView){
        _progressView = [[UIProgressView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 1)];
        _progressView.tintColor = [UIColor lightGrayColor];
        _progressView.trackTintColor = [UIColor clearColor];
    }
    return _progressView;
}
- (void)viewSafeAreaInsetsDidChange {
    [super viewSafeAreaInsetsDidChange];
    UIEdgeInsets insets = self.view.safeAreaInsets;
    self.progressView.frame = CGRectMake(0, insets.top + 2, self.view.frame.size.width, 2);
}
-(void)addUMObserver{
    //添加监测网页加载进度的观察者
    [self.wkWebView addObserver:self
                   forKeyPath:NSStringFromSelector(@selector(estimatedProgress))
                      options:0
                      context:nil];
    [self.wkWebView addObserver:self
                   forKeyPath:@"title"
                      options:NSKeyValueObservingOptionNew
                      context:nil];
}

- (void)setupWebView {
    if (!_wkWebView) {
        WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
        WKUserContentController *controller = [[WKUserContentController alloc] init];
        configuration.userContentController = controller;


        configuration.processPool = [[WKProcessPool alloc] init];
        
        _wkWebView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 30, KmainScreenWidth, KmainScreenHeight-30)
                                        configuration:configuration];
        _wkWebView.navigationDelegate = self;
        _wkWebView.UIDelegate = self;
        
        [self.view addSubview:_wkWebView];
    }
}

- (void)loadRemoteRequest {
    if (_wkWebView) {
        [_wkWebView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"https://smallfan.net/demo.html"]]];
    }
}

- (void)loadRequest {
    if (_wkWebView) {
        if (self.url) {
            [_wkWebView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:self.url] cachePolicy:NSURLRequestReloadIgnoringCacheData timeoutInterval:30.0]];
        }else{
//            NSString *bundleStr = [[NSBundle mainBundle] pathForResource:self.localName ofType:@"html"];
//            [_wkWebView loadFileURL:[NSURL fileURLWithPath:bundleStr] allowingReadAccessToURL:[NSURL fileURLWithPath:[NSBundle mainBundle].bundlePath]];
            NSURL *url = [[NSBundle mainBundle] URLForResource:@"index.html" withExtension:nil];
            [_wkWebView loadRequest:[NSURLRequest requestWithURL:url]];

            

        }
       
    }
    
}
#pragma mark -- WKScriptMessageHandler

-(void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message{
    if ([message.name isEqualToString:@"ShowMessage"]) {
            NSLog(@"当前的cookie为： %@", message.body);
//            [self openCamera];
    }
}


-(void)webView:(WKWebView *)webView didFinishNavigation:(null_unspecified WKNavigation *)navigation{
    
}

#pragma mark - WKNavigationDelegate


-                   (void)webView:(WKWebView *)webView
didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
                completionHandler:(void (^)(NSURLSessionAuthChallengeDisposition disposition, NSURLCredential * _Nullable credential))completionHandler {
    
    if ([challenge.protectionSpace.authenticationMethod isEqualToString:NSURLAuthenticationMethodServerTrust]) {
        NSURLCredential *card = [[NSURLCredential alloc] initWithTrust:challenge.protectionSpace.serverTrust];
        completionHandler(NSURLSessionAuthChallengeUseCredential, card);
    }
}

#pragma mark - UIDelegate
-                       (void)webView:(WKWebView *)webView
   runJavaScriptAlertPanelWithMessage:(NSString *)message
                     initiatedByFrame:(WKFrameInfo *)frame
                    completionHandler:(void (^)(void))completionHandler {
    
    NSString *alertTitle = @"Reminder";
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:alertTitle
                                                                             message:message
                                                                      preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:[UIAlertAction actionWithTitle:@"sure"
                                                        style:UIAlertActionStyleDefault
                                                      handler:^(UIAlertAction * _Nonnull action) {
                                                          completionHandler();
                                                      }]];
    [self presentViewController:alertController animated:YES completion:nil];
}
- (NSArray *)plistArray{
    
    NSString *bunldPlistPath = [[NSBundle mainBundle] pathForResource:@"UMJsapi-list" ofType:@"plist"];
    return [[NSDictionary alloc] initWithContentsOfFile:bunldPlistPath][@"jsapis"];
}


- (void)reload{
    [self.wkWebView reload];
}

- (void)goback{
    if ([self.wkWebView canGoBack])
    {
        
        [self.wkWebView goBack];
        
        
    }else{
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (void)goForward{
    
    [self.wkWebView canGoForward] ? [_wkWebView goForward] : NULL;
}

#pragma mark - KVO

-(void)observeValueForKeyPath:(NSString *)keyPath
                     ofObject:(id)object
                       change:(NSDictionary<NSKeyValueChangeKey,id> *)change
                      context:(void *)context{
    
    if ([keyPath isEqualToString:NSStringFromSelector(@selector(estimatedProgress))]
        && object == _wkWebView) {
        
        NSLog(@"Web page loading speed = %f",_wkWebView.estimatedProgress);
        self.progressView.progress = _wkWebView.estimatedProgress;
        if (_wkWebView.estimatedProgress >= 1.0f) {
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                self.progressView.progress = 0;
                NSString *jspath = [[NSBundle mainBundle]pathForResource:@"um_pub_h5" ofType:@"js"];
                NSString *str = [NSString stringWithContentsOfFile:jspath encoding:NSUTF8StringEncoding error:nil];
                [self.bridge _evaluateJavascript:str];
            });
        }
        
    }else if([keyPath isEqualToString:@"title"]
             && object == _wkWebView){
        if ([_wkWebView canGoBack]) {
            self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"h_back"] style:UIBarButtonItemStylePlain target:self action:@selector(clickBack)];
        }else{
            self.navigationItem.leftBarButtonItem = nil;
        }
        self.navigationItem.title = _wkWebView.title;
    }else{
        [super observeValueForKeyPath:keyPath
                             ofObject:object
                               change:change
                              context:context];
    }
}


-(void)dealloc{
   
    [_wkWebView removeObserver:self
                  forKeyPath:NSStringFromSelector(@selector(estimatedProgress))];
    [_wkWebView removeObserver:self
                  forKeyPath:NSStringFromSelector(@selector(title))];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
