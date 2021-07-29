

#import <Foundation/Foundation.h>
#import "LiveNavigationController.h"

@implementation LiveNavigationController

- (BOOL)shouldAutorotate{
    return _supportRotation;
}

- (UIInterfaceOrientationMask)supportedInterfaceOrientations {
    return _horizontal ? UIInterfaceOrientationMaskLandscapeRight : UIInterfaceOrientationMaskPortrait;
}

@end
