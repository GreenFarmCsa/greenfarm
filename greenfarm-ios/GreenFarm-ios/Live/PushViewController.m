//
//  PushViewController.m


#import "PushViewController.h"
#import "LivePreview.h"
#import "NSString+Extension.h"
#import "AFNetworking.h"

static const NSString *heartbeatURL = @"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/live/tickTime?liveId=";

@interface PushViewController ()
@property (nonatomic, strong) NSTimer *liveTimer;
@end

@implementation PushViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    LivePreview *liveView = [[LivePreview alloc] initWithFrame:self.view.bounds];
    if(self.pushURL) {
        liveView.pushURL = self.pushURL;
    } else {
        liveView.pushURL = liveURL;
    }
    __weak typeof(self) _self = self;
    liveView.personalblock = ^{
        [_self back];
        _self.closeLiveblock();
    };
     [self.view addSubview:liveView];
    
    NSString * liveId = [NSString substringFromWith:self.pushURL];
    NSString * url = [NSString stringWithFormat:@"%@%@",heartbeatURL,liveId];
    AFHTTPSessionManager *manager =[AFHTTPSessionManager manager];
    self.liveTimer = [NSTimer scheduledTimerWithTimeInterval:20.0 repeats:YES block:^(NSTimer * _Nonnull timer) {
        NSLog(@"call heartbeat：%@",liveId);
        [manager POST:url parameters:nil headers:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
            NSLog(@"success:%@",responseObject);
        } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
            NSString* errorStr=[NSString stringWithFormat:@"%ld:%@",(long)[error code],[error localizedDescription]];
            NSLog(@"failure:%@",errorStr);
        }];
    }];
}

- (void)back {
    [self.liveTimer invalidate];
    self.liveTimer = nil;
    if (self.presentingViewController) {
        [self dismissViewControllerAnimated:NO completion:nil];
    } else {
        [self.navigationController popViewControllerAnimated:NO];
    }
}

- (UIInterfaceOrientationMask)supportedInterfaceOrientations {
    return UIInterfaceOrientationMaskPortrait;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation {
    return NO;
}

@end
