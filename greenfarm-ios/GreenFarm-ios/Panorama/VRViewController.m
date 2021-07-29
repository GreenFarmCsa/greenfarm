

#import <Foundation/Foundation.h>
#import "VRViewController.h"
#import "PanoramaView.h"
#import "UIView+YYAdd.h"

@interface VRViewController (){
    PanoramaView *panoramaView;
    BOOL candraw;
}
@property (nonatomic, strong) UIButton *closeButton;
@end

@implementation VRViewController

- (void)viewDidLoad{
    [super viewDidLoad];
    panoramaView = [[PanoramaView alloc] init];
    
    [panoramaView setOrientToDevice:YES];
    [panoramaView setTouchToPan:YES];
    [panoramaView setPinchToZoom:YES];
    [panoramaView setShowTouches:NO];
    [panoramaView setVRMode:NO];
//    [panoramaView setImageWithName:@"2048.jpg"];
    [self setView:panoramaView];
    [self.view addSubview:self.closeButton];
    if (!self.vrURL) {
        self.vrURL = VRURL;
    }
    //[panoramaView setImageWithName:@"gf_default.jpg"];
    NSURL *url = [NSURL URLWithString:self.vrURL];
    NSURLRequest *URLRequest = [NSURLRequest requestWithURL:url cachePolicy:NSURLRequestReturnCacheDataElseLoad timeoutInterval:20.0];
    [[[NSURLSession sharedSession] dataTaskWithRequest:URLRequest completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        dispatch_sync(dispatch_get_main_queue(), ^{
            if (error == nil && data != nil) {
                UIImage *image = [UIImage imageWithData:data];
                [self->panoramaView setImage:image];
            } else {
                [self->panoramaView setImageWithName:@"gf_default.jpg"];
            }
            self->candraw = YES;
        });

    }] resume];
}

-(void) glkView:(GLKView *)view drawInRect:(CGRect)rect{
    if (candraw) {
        [panoramaView draw];
    }
  //  [panoramaView draw];
}

- (UIButton *)closeButton {
    if (!_closeButton) {
        _closeButton = [UIButton new];
        _closeButton.size = CGSizeMake(50, 50);
        _closeButton.left = self.view.width - 10 - _closeButton.width;
        _closeButton.top = 30;
        [_closeButton setImage:[UIImage imageNamed:@"gf_native_close"] forState:UIControlStateNormal];
        _closeButton.exclusiveTouch = YES;
        [_closeButton addTarget:self action:@selector(close) forControlEvents:UIControlEventTouchUpInside];
    }
    return _closeButton;
}

-(void)close {
    if (self.presentingViewController) {
        [self dismissViewControllerAnimated:NO completion:nil];
    } else {
        [self.navigationController popViewControllerAnimated:NO];
    }
}
@end
