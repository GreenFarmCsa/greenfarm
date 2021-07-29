
#import "AppDelegate.h"
#import "UMH5BaseViewController.h"
#import <MapKit/MapKit.h>

@interface AppDelegate () {
    BOOL done;
}

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    [self createRootVC];
    NSTimeInterval delayDuration = 1.5;
    NSTimer *connectionTimer = [NSTimer scheduledTimerWithTimeInterval:delayDuration target:self selector:@selector(timerFired:) userInfo:nil repeats:NO];
    [[NSRunLoop currentRunLoop] addTimer:connectionTimer forMode:NSDefaultRunLoopMode];
    do {
        [[NSRunLoop currentRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:1.0]];
    } while (!done);
    
    return YES;
}

- (void)timerFired:(NSTimer *)timer {
    done = YES;
}

- (void)createRootVC {
    NSString *baseUrl = @"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/index.html#";
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    UMH5BaseViewController *h5VC = [[UMH5BaseViewController alloc] init];
    h5VC.url = baseUrl;
    
//    UMH5BaseViewController *loadVC = [[UMH5BaseViewController alloc] init];
//    loadVC.url = @"http://172.20.10.2:9527/";
//    loadVC.localName = @"index";
    self.window.rootViewController = h5VC;
    [self.window makeKeyAndVisible];
    self.window.backgroundColor = [UIColor whiteColor];
    CLLocationManager *locationManager = [[CLLocationManager alloc] init];
    if ([CLLocationManager authorizationStatus]==kCLAuthorizationStatusNotDetermined){
           [locationManager requestWhenInUseAuthorization];
    }
}

@end
