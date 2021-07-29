//
//  PullViewController.m



#import <Foundation/Foundation.h>
#import "PullViewController.h"
#import <IJKMediaFramework/IJKMediaFramework.h>
#import "UIView+YYAdd.h"
#import <Masonry/Masonry.h>
@interface PullViewController ()
@property(atomic, retain) id<IJKMediaPlayback> player;
@property (nonatomic, strong) UIButton *closeButton;
@property (nonatomic, strong)UILabel * roomLbl;
//@property (nonatomic, strong)UILabel * farmLbl;
@end

@implementation PullViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
#ifdef DEBUG
    [IJKFFMoviePlayerController setLogReport:YES];
    [IJKFFMoviePlayerController setLogLevel:k_IJK_LOG_DEBUG];
#else
    [IJKFFMoviePlayerController setLogReport:NO];
    [IJKFFMoviePlayerController setLogLevel:k_IJK_LOG_INFO];
#endif
    
    [IJKFFMoviePlayerController checkIfFFmpegVersionMatch:YES];
    // [IJKFFMoviePlayerController checkIfPlayerVersionMatch:YES major:1 minor:0 micro:0];
    IJKFFOptions *options = [IJKFFOptions optionsByDefault];
    if (self.manifest != nil){
        [options setPlayerOptionValue:@"ijklas"         forKey:@"iformat"];
        [options setPlayerOptionIntValue:0              forKey:@"find_stream_info"];
        [options setFormatOptionValue:self.manifest     forKey:@"manifest_string"];
    }
    self.player = [[IJKFFMoviePlayerController alloc] initWithContentURL:[NSURL URLWithString: self.model.url] withOptions:options];
    self.player.view.autoresizingMask = UIViewAutoresizingFlexibleWidth|UIViewAutoresizingFlexibleHeight;
    self.player.view.frame = self.view.bounds;
    self.player.scalingMode = IJKMPMovieScalingModeAspectFill;
    self.player.shouldAutoplay = YES;
    
    self.view.autoresizesSubviews = YES;
    [self.view addSubview:self.player.view];
    [self.view addSubview:self.closeButton];
    [self.view addSubview:self.roomLbl];
    //[self.view addSubview:self.farmLbl];
    self.view.backgroundColor = [UIColor whiteColor];
}

//- (UIStatusBarStyle)preferredStatusBarStyle {
//
//return UIStatusBarStyleLightContent;
//
//}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self installMovieNotificationObservers];
    
    [self.player prepareToPlay];
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    
    [self.player shutdown];
    [self removeMovieNotificationObservers];
}

//-(BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation{
//    return UIInterfaceOrientationIsLandscape(toInterfaceOrientation);
//}

- (BOOL)shouldAutorotate{
    return NO;
}

- (UIInterfaceOrientationMask)supportedInterfaceOrientations
{
    return UIInterfaceOrientationMaskPortrait;
}

- (UIInterfaceOrientation)preferredInterfaceOrientationForPresentation{
    return UIInterfaceOrientationPortrait;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (UIButton *)closeButton {
    if (!_closeButton) {
        _closeButton = [UIButton new];
        _closeButton.size = CGSizeMake(44, 44);
        _closeButton.left = self.view.width - 20 - _closeButton.width;
        _closeButton.top = 40;
        [_closeButton setImage:[UIImage imageNamed:@"gf_native_close"] forState:UIControlStateNormal];
        _closeButton.exclusiveTouch = YES;
        [_closeButton addTarget:self action:@selector(close) forControlEvents:UIControlEventTouchUpInside];
    }
    return _closeButton;
}

-(UILabel*)roomLbl {
    if (!_roomLbl) {
        _roomLbl = [[UILabel alloc]init];
        [self.view addSubview:_roomLbl];
        [_roomLbl mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.mas_equalTo(20);
            make.top.mas_equalTo(40);
            make.height.mas_equalTo(50);
            make.width.mas_equalTo(150);
        }];
        
        UIImageView *backView = [UIImageView new];
        backView.image = [UIImage imageNamed:@"gf_back"];
        [_roomLbl addSubview:backView];
        [backView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.edges.mas_equalTo(UIEdgeInsetsZero);
        }];
        
        UIImageView *headView = [UIImageView new];
        headView.layer.cornerRadius = 20;
        headView.layer.masksToBounds = YES;
        [backView addSubview:headView];
        if (self.model.picUrl) {
            NSURL *url = [NSURL URLWithString:self.model.picUrl];
            NSURLRequest *URLRequest = [NSURLRequest requestWithURL:url cachePolicy:NSURLRequestReturnCacheDataElseLoad timeoutInterval:20.0];
            [[[NSURLSession sharedSession] dataTaskWithRequest:URLRequest completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
                dispatch_sync(dispatch_get_main_queue(), ^{
                    if (error == nil && data != nil) {
                        UIImage *image = [UIImage imageWithData:data];
                        [headView setImage:image];
                    } else {
                        headView.image = [UIImage imageNamed:@"gf_cow"];
                    }
                });

            }] resume];
        } else {
            headView.image = [UIImage imageNamed:@"gf_cow"];
        }
        [headView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.mas_equalTo(5);
            make.top.mas_equalTo(5);
            make.bottom.mas_equalTo(-5);
            make.size.mas_equalTo(CGSizeMake(40, 40));
        }];
        
        UILabel *nameLabel = [UILabel new];
        nameLabel.text = self.model.nickName;
        nameLabel.textColor = [UIColor whiteColor];
        nameLabel.font = [UIFont boldSystemFontOfSize:16];
        [backView addSubview:nameLabel];
        [nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.mas_equalTo(headView.mas_right).offset(10);
            make.right.mas_equalTo(-3);
            make.height.mas_equalTo(backView.mas_height);
        }];
    }
    UILabel *farmLbl = [UILabel new];
    [self.view addSubview:farmLbl];
    farmLbl.text = self.model.farmName;
    farmLbl.textColor = [UIColor whiteColor];
    farmLbl.font = [UIFont boldSystemFontOfSize:16];
    [farmLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(self->_roomLbl.mas_left).mas_offset(5);
        make.right.mas_equalTo(self->_roomLbl.mas_right);
        make.top.mas_equalTo(self->_roomLbl.mas_bottom).mas_offset(10);
    }];
    return _roomLbl;
}


-(void)close {
    if (self.presentingViewController) {
        [self dismissViewControllerAnimated:YES completion:nil];
    } else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

#pragma mark Install Movie Notifications

/* Register observers for the various movie object notifications. */
-(void)installMovieNotificationObservers
{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(loadStateDidChange:)
                                                 name:IJKMPMoviePlayerLoadStateDidChangeNotification
                                               object:_player];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(moviePlayBackDidFinish:)
                                                 name:IJKMPMoviePlayerPlaybackDidFinishNotification
                                               object:_player];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(mediaIsPreparedToPlayDidChange:)
                                                 name:IJKMPMediaPlaybackIsPreparedToPlayDidChangeNotification
                                               object:_player];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(moviePlayBackStateDidChange:)
                                                 name:IJKMPMoviePlayerPlaybackStateDidChangeNotification
                                               object:_player];
}

#pragma mark Remove Movie Notification Handlers

/* Remove the movie notification observers from the movie object. */
-(void)removeMovieNotificationObservers
{
    [[NSNotificationCenter defaultCenter]removeObserver:self name:IJKMPMoviePlayerLoadStateDidChangeNotification object:_player];
    [[NSNotificationCenter defaultCenter]removeObserver:self name:IJKMPMoviePlayerPlaybackDidFinishNotification object:_player];
    [[NSNotificationCenter defaultCenter]removeObserver:self name:IJKMPMediaPlaybackIsPreparedToPlayDidChangeNotification object:_player];
    [[NSNotificationCenter defaultCenter]removeObserver:self name:IJKMPMoviePlayerPlaybackStateDidChangeNotification object:_player];
}

- (void)loadStateDidChange:(NSNotification*)notification
{
    //    MPMovieLoadStateUnknown        = 0,
    //    MPMovieLoadStatePlayable       = 1 << 0,
    //    MPMovieLoadStatePlaythroughOK  = 1 << 1, // Playback will be automatically started in this state when shouldAutoplay is YES
    //    MPMovieLoadStateStalled        = 1 << 2, // Playback will be automatically paused in this state, if started
    
    IJKMPMovieLoadState loadState = _player.loadState;
    
    if ((loadState & IJKMPMovieLoadStatePlaythroughOK) != 0) {
        NSLog(@"loadStateDidChange: IJKMPMovieLoadStatePlaythroughOK: %d\n", (int)loadState);
    } else if ((loadState & IJKMPMovieLoadStateStalled) != 0) {
        NSLog(@"loadStateDidChange: IJKMPMovieLoadStateStalled: %d\n", (int)loadState);
    } else {
        NSLog(@"loadStateDidChange: ???: %d\n", (int)loadState);
    }
}

- (void)moviePlayBackDidFinish:(NSNotification*)notification
{
    //    MPMovieFinishReasonPlaybackEnded,
    //    MPMovieFinishReasonPlaybackError,
    //    MPMovieFinishReasonUserExited
    int reason = [[[notification userInfo] valueForKey:IJKMPMoviePlayerPlaybackDidFinishReasonUserInfoKey] intValue];
    
    switch (reason)
    {
        case IJKMPMovieFinishReasonPlaybackEnded:
            NSLog(@"playbackStateDidChange: IJKMPMovieFinishReasonPlaybackEnded: %d\n", reason);
            break;
            
        case IJKMPMovieFinishReasonUserExited:
            NSLog(@"playbackStateDidChange: IJKMPMovieFinishReasonUserExited: %d\n", reason);
            break;
            
        case IJKMPMovieFinishReasonPlaybackError:
            NSLog(@"playbackStateDidChange: IJKMPMovieFinishReasonPlaybackError: %d\n", reason);
            break;
            
        default:
            NSLog(@"playbackPlayBackDidFinish: ???: %d\n", reason);
            break;
    }
}

- (void)mediaIsPreparedToPlayDidChange:(NSNotification*)notification
{
    NSLog(@"mediaIsPreparedToPlayDidChange\n");
}

- (void)moviePlayBackStateDidChange:(NSNotification*)notification
{
    //    MPMoviePlaybackStateStopped,
    //    MPMoviePlaybackStatePlaying,
    //    MPMoviePlaybackStatePaused,
    //    MPMoviePlaybackStateInterrupted,
    //    MPMoviePlaybackStateSeekingForward,
    //    MPMoviePlaybackStateSeekingBackward
    
    switch (_player.playbackState)
    {
        case IJKMPMoviePlaybackStateStopped: {
            NSLog(@"IJKMPMoviePlayBackStateDidChange %d: stoped", (int)_player.playbackState);
            break;
        }
        case IJKMPMoviePlaybackStatePlaying: {
            NSLog(@"IJKMPMoviePlayBackStateDidChange %d: playing", (int)_player.playbackState);
            break;
        }
        case IJKMPMoviePlaybackStatePaused: {
            NSLog(@"IJKMPMoviePlayBackStateDidChange %d: paused", (int)_player.playbackState);
            break;
        }
        case IJKMPMoviePlaybackStateInterrupted: {
            NSLog(@"IJKMPMoviePlayBackStateDidChange %d: interrupted", (int)_player.playbackState);
            break;
        }
        case IJKMPMoviePlaybackStateSeekingForward:
        case IJKMPMoviePlaybackStateSeekingBackward: {
            NSLog(@"IJKMPMoviePlayBackStateDidChange %d: seeking", (int)_player.playbackState);
            break;
        }
        default: {
            NSLog(@"IJKMPMoviePlayBackStateDidChange %d: unknown", (int)_player.playbackState);
            break;
        }
    }
}

@end
