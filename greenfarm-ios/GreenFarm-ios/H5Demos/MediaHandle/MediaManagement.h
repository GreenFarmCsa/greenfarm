

#ifndef MediaManagement_h
#define MediaManagement_h

#import "ResultFile.h"
#import <MediaSupport/HXPhotoPicker.h>
#import "MediaConfig.h"

#define MMWeakSelf __weak typeof(self) weakSelf = self;
typedef void (^ UploadFinishHandler)(NSMutableArray *resultFileList);
typedef NS_ENUM(NSUInteger, ManagerType) {
    Select = 0,
    Record = 1
};
@interface MediaManagement : NSObject

@property (strong, nonatomic) HXPhotoManager *manager;
@property (strong, nonatomic) NSString *seesionid;
@property (strong, nonatomic) NSString *nickName;
@property (strong, nonatomic) UIViewController *context;
@property (nonatomic, strong) void (^uploadFinishHandler)(NSMutableArray *resultFileList);

- (id)initWithManager:(HXPhotoManager *)manager  sessionid:(NSString *)seesionid context:(UIViewController *)context;
- (id)initWithManager:(HXPhotoManager *)manager config:(MediaConfig *)config  context:(UIViewController *)context;
- (id) initWithManager:(HXPhotoManager *)manager context:(UIViewController *)context;

- (void)getMediaWithType:(NSUInteger*) type uploadFinishHandler:(UploadFinishHandler) uploadFinishHandler;

@end

#endif /* MediaManagement_h */
