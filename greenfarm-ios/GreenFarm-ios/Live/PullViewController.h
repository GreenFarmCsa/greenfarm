//
//  PullViewController.h


#ifndef PullViewController_h
#define PullViewController_h

#import "LiveRoomModel.h"

@interface PullViewController : UIViewController
@property (nonatomic, strong) NSString* manifest;
@property (nonatomic, strong) LiveRoomModel *model;
@end

#endif /* PullViewController_h */
