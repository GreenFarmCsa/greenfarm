
#import "RulerViewController.h"
#import "GreenFarm_ios-Swift.h"

@interface RulerViewController ()

@end

@implementation RulerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.title = @"Ruler";
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIButton *clickBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    clickBtn.frame = CGRectMake(20, 150, UIScreen.mainScreen.bounds.size.width-20*2, 80) ;
    [clickBtn setTitle:@"调用Ruler" forState:UIControlStateNormal];
    [clickBtn setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
    clickBtn.backgroundColor = [UIColor brownColor];
    [clickBtn addTarget:self action:@selector(clickBtnAction) forControlEvents:UIControlEventTouchUpInside];
    clickBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18];
    [self.view addSubview:clickBtn];
}

- (void)clickBtnAction {
    RulerARProViewController *rulerVC = [[RulerARProViewController alloc]init];
    self.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:rulerVC animated:YES];
    self.hidesBottomBarWhenPushed = NO;
}

@end
