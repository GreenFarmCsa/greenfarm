//
//  UMJsApi4VRCamera.m


#import <Foundation/Foundation.h>
#import "UMJsApi4VRCamera.h"
#import <MediaSupport/HXPhotoPicker.h>
#import "CVWrapper.h"
#import "UploadUtil.h"
#import "ResultFile.h"
#import "MJExtension.h"

#define MMWeakSelf __weak typeof(self) weakSelf = self;
static const NSString *pictureURL = @"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/upload";
@interface UMJsApi4VRCamera()
<UIActionSheetDelegate,UIAlertViewDelegate,HXCustomCameraViewControllerDelegate,HXAlbumListViewControllerDelegate>
@property (strong, nonatomic) HXPhotoManager *manager;
@property (strong, atomic) UMContext* mUMContext;
@property (strong, nonatomic) NSMutableArray *resultFileList;
@end

@implementation UMJsApi4VRCamera

-(void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback{
    for(NSString *key in data){
        NSLog(@"key=%@, value=%@",key,data[key]);
    }
    self.mUMContext=context;
    
    [[context currentViewController] hx_presentSelectPhotoControllerWithManager:self.manager didDone:^(NSArray<HXPhotoModel *> * _Nullable allList, NSArray<HXPhotoModel *> * _Nullable photoList, NSArray<HXPhotoModel *> * _Nullable videoList, BOOL isOriginal, UIViewController * _Nullable viewController, HXPhotoManager * _Nullable manager) {
        NSMutableArray<UIImage *> *images = [NSMutableArray new];
        dispatch_group_t downloadGroup = dispatch_group_create();
        for( HXPhotoModel *model in photoList) {
            dispatch_group_enter(downloadGroup);
            [model requestPreviewImageWithSize:model.imageSize startRequestICloud:^(PHImageRequestID iCloudRequestId, HXPhotoModel * _Nullable model) {
                
                        } progressHandler:^(double progress, HXPhotoModel * _Nullable model) {
                            
                        } success:^(UIImage * _Nullable image, HXPhotoModel * _Nullable model, NSDictionary * _Nullable info) {
                            [images addObject:image];
                            dispatch_group_leave(downloadGroup);
                        } failed:^(NSDictionary * _Nullable info, HXPhotoModel * _Nullable model) {
                            dispatch_group_leave(downloadGroup);
                        }];
        }
        __weak typeof(self) weakSelf = self;
        dispatch_group_notify(downloadGroup, dispatch_get_main_queue(), ^{
            NSLog(@"end");
            __strong typeof(self) strongSelf = weakSelf;
            UIImage * image = [CVWrapper processWithArray:images];
            UIImage * zoomImage = [strongSelf compressImage:image];
            [strongSelf upload:zoomImage];
            //UIImageWriteToSavedPhotosAlbum(image, nil, nil, nil);
            
        });
    } cancel:^(UIViewController * _Nullable viewController, HXPhotoManager * _Nullable manager) {
        MMWeakSelf
        [weakSelf.mUMContext.bridge callHandler:@"jumpVRCamera" data:@false responseCallback:^(id responseData){
        }];
    }];
    
}

- (void) upload:(UIImage *)image {
    ResultFile *resultFile = [ResultFile new];
    resultFile.type = @"picture";
    self.resultFileList = [NSMutableArray arrayWithCapacity:1];
    [UploadUtil uploadPhoto2WithURL:pictureURL sessionId:nil image:image
               uploadSuccessHandler:^(id  _Nullable responseObject) {
                   NSDictionary *response = (NSDictionary *)responseObject;
                   if ([response objectForKey:@"data"] != nil){
                       NSLog(@"ufileid: %@",[response objectForKey:@"data"]);
                       resultFile.uFileId = [response objectForKey:@"data"];
                       resultFile.success = YES;
                   } else {
                       resultFile.uFileId = [response objectForKey:@"data"];
                       resultFile.success = NO;
                   }
        [self.resultFileList addObject:resultFile];
        [self uploadFinishWithresultFileList:self.resultFileList];

               } uploadFailureHandler:^(id  _Nullable responseObject,NSError * error) {
                   NSDictionary *response = (NSDictionary *)responseObject;
                   if ([response objectForKey:@"message"] == nil) {
                       // [response setValue:error forKey:@"message"];
                       response = @{@"message": [NSString stringWithFormat:@"%ld:%@",(long)[error code],[error localizedDescription]]};
                   }
                   resultFile.errorCause = [response objectForKey:@"message"];
                   resultFile.success = NO;
                   [self.resultFileList addObject:resultFile];
                   [self uploadFinishWithresultFileList:self.resultFileList];
               }];
    
}

- (void) uploadFinishWithresultFileList:(NSMutableArray *)resultFileList {
    for (ResultFile *resultFile in resultFileList) {
        NSSLog(@"ResultFile: %@",resultFile);
    }
    NSMutableArray *configDictArray = [ResultFile mj_keyValuesArrayWithObjectArray:resultFileList];
    NSLog(@"afterHttpUplode-%@",@"before");
    [self.mUMContext.bridge callHandler:@"jumpVRCamera" data:configDictArray responseCallback:^(id responseData){
    }];
    NSLog(@"afterHttpUplode-%@",@"after");
    [self.manager clearSelectedList];
//    [self.h5WebViewController popToViewController:self.h5WebViewController animated:YES];
}

-(UIImage*)compressImage:(UIImage *)img{
    CGFloat h = img.size.height;
    CGFloat w = img.size.width;
    UIImage *cutImage;
    
    if (w>2*h) {
        CGRect rect = CGRectMake(w/2-h, 0, 2*h, h);
        cutImage = [self cutImage:img withRect:rect];
    } else if (w<2*h) {
        CGRect rect = CGRectMake(0, h/2-w/4, w, w/2);
        cutImage = [self cutImage:img withRect:rect];
    } else {
        cutImage = img;
    }
    
    CGFloat currentHeight = cutImage.size.height;
    CGFloat orgHeight = 2;
    CGFloat maxHeight = 2048;
    while (currentHeight > 2*orgHeight && orgHeight < maxHeight) {
        orgHeight*=2;
    }
    
    UIImage * zoomImage;
    CGSize size;
    if (currentHeight-orgHeight <= 2*orgHeight-currentHeight || orgHeight >= maxHeight) {
        size = CGSizeMake(2*orgHeight, orgHeight);
    } else {
        size = CGSizeMake(4*orgHeight, 2*orgHeight);
    }
    zoomImage = [self telescopicImage:img ToSize:size];
    
    return zoomImage;
}

- (UIImage *)cutImage:(UIImage *)image withRect:(CGRect )rect

{

    CGImageRef imageRef = CGImageCreateWithImageInRect([image CGImage], rect);

    UIImage * img = [UIImage imageWithCGImage:imageRef];

    CGImageRelease(imageRef);

    return img;

}

- (UIImage *)telescopicImage:(UIImage *)img ToSize:(CGSize) size

{

    UIGraphicsBeginImageContext(size);

    [img drawInRect:CGRectMake(0, 0, size.width, size.height)];

    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();

    UIGraphicsEndImageContext();

    return newImage;

}

- (HXPhotoManager *)manager {
    if (!_manager) {
        _manager = [[HXPhotoManager alloc] initWithType:HXPhotoManagerSelectedTypePhoto];
        _manager.configuration.type = HXConfigurationTypeWXMoment;
        //_manager.configuration.photoEditConfigur.onlyCliping = YES;
        _manager.configuration.languageType = HXPhotoLanguageTypeEn;
        //_manager.configuration.photoEditConfigur.aspectRatio = HXPhotoEditAspectRatioType_Custom;
        //_manager.configuration.photoEditConfigur.isRoundCliping = NO;
        _manager.configuration.openCamera = YES;
        _manager.configuration.photoMaxNum = 6;
        _manager.configuration.selectTogether = NO;
        _manager.configuration.cameraPhotoJumpEdit = NO;
    }
    return _manager;
}

@end
