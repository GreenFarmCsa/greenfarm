
#import "UMJsApi4JumpCamera.h"
#import "MediaManagement.h"
#import "MJExtension.h"

@interface UMJsApi4JumpCamera()
<UIActionSheetDelegate,UIAlertViewDelegate,HXCustomCameraViewControllerDelegate,HXAlbumListViewControllerDelegate>
@property (strong, nonatomic) HXPhotoManager *manager;
@property (strong, atomic) MediaConfig* mediaConfig;
@property (strong, atomic) UMContext* mUMContext;
@end

@implementation UMJsApi4JumpCamera
-(void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback{
    for(NSString *key in data){
        NSLog(@"key=%@, value=%@",key,data[key]);
    }
    NSString* session=data[@"sessionId"];
//    NSString* nickName=data[@"nickName"];
    NSInteger maxSelectable=[data[@"maxSelectable"] integerValue];
    NSInteger mimeType=[data[@"mimeType"] integerValue];
    NSInteger recordTime=[data[@"maxRecordTime"] integerValue];
    Boolean capture=[data[@"capture"] boolValue];
    NSUInteger opType=[data[@"type"] integerValue];
    
//    if(session==nil||maxSelectable<0||(mimeType<0&&mimeType>2)||recordTime<0){
//        callback(@{@"success":@"false",@"errorCause":@"必要参数非法"});
//        return;
//    }
    if(maxSelectable<0||(mimeType<0&&mimeType>2)||recordTime<0){
        callback(@{@"success":@"false",@"errorCause":@"必要参数非法"});
        return;
    }
    self.mediaConfig=[[MediaConfig alloc]initSessionId:session withMaxSelectable:maxSelectable withMimeType:mimeType withMaxRecordTime:recordTime withCapture:capture];    //h5回调
    self.mUMContext=context;
    
    MediaManagement *mediaManage = [[MediaManagement alloc] initWithManager:self.manager context:context.currentViewController];
    
    [mediaManage getMediaWithType:opType uploadFinishHandler:^(NSMutableArray *resultFileList) {
        [self uploadFinishWithresultFileList:resultFileList];
    }];

    
}

- (HXPhotoManager *)manager {
//    if (!_manager) {
        _manager = [[HXPhotoManager alloc] initWithType:HXPhotoManagerSelectedTypePhotoAndVideo];
        _manager.configuration.type = HXConfigurationTypeWXMoment;
        _manager.configuration.photoEditConfigur.onlyCliping = YES;
        _manager.configuration.languageType = HXPhotoLanguageTypeEn;
        _manager.configuration.photoEditConfigur.aspectRatio = HXPhotoEditAspectRatioType_Custom;
        _manager.configuration.photoEditConfigur.isRoundCliping = NO;
        _manager.configuration.openCamera = self.mediaConfig.capture;
        _manager.configuration.photoMaxNum = self.mediaConfig.maxSelectable;
        _manager.configuration.videoMaxNum = 1;
        _manager.configuration.maxNum = self.mediaConfig.maxSelectable;
        _manager.configuration.selectTogether = NO;
        _manager.configuration.videoMaximumDuration = self.mediaConfig.maxRecordTime;
        _manager.configuration.videoMaximumSelectDuration = self.mediaConfig.maxRecordTime*100;
        _manager.configuration.maxVideoClippingTime = self.mediaConfig.maxRecordTime*100;
        _manager.type = self.mediaConfig.mimeType;
    //}
    return _manager;
}

- (void) uploadFinishWithresultFileList:(NSMutableArray *)resultFileList {
    if(!resultFileList){
        [self.mUMContext.bridge callHandler:@"jumpCamera" data:@false responseCallback:^(id responseData){
        }];
    }
    for (ResultFile *resultFile in resultFileList) {
        NSSLog(@"ResultFile: %@",resultFile);
    }
    NSMutableArray *configDictArray = [MediaConfig mj_keyValuesArrayWithObjectArray:resultFileList];
    NSLog(@"afterHttpUplode-%@",@"before");
    [self.mUMContext.bridge callHandler:@"jumpCamera" data:configDictArray responseCallback:^(id responseData){
    }];
    NSLog(@"afterHttpUplode-%@",@"after");
    [self.manager clearSelectedList];
//    [self.h5WebViewController popToViewController:self.h5WebViewController animated:YES];
}
@end
