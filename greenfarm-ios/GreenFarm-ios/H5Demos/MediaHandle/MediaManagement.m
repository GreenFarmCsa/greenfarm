

#import <Foundation/Foundation.h>
#import "MediaManagement.h"
#import <MediaSupport/HXPhotoPicker.h>
#import "UploadUtil.h"
#import "DTImageEdit.h"
#import "DTVideoEdit.h"
#import "UMJsApi4JumpCamera.h"

static const NSString *videoUploadURL = @"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/upload";
static const NSString *pictureURL = @"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/upload";
static const int defaultPhotoWidth = 720;

@interface MediaManagement()
@property (strong, nonatomic) NSMutableArray *resultFileList;
@property (strong, nonatomic) NSMutableDictionary *resultFileListWithImage;
@property (nonatomic) int finishCount;
@property (nonatomic) int fileCount;
@end

@implementation MediaManagement


- (id) initWithManager:(HXPhotoManager *)manager sessionid:(NSString *)seesionid  context:(UIViewController *)context {
    self.seesionid = seesionid;
    self.manager = manager;
    self.context = context;
    return self;
}

- (id) initWithManager:(HXPhotoManager *)manager config:(MediaConfig *)config  context:(UIViewController *)context {
    self.seesionid = config.sessionId;
    self.nickName = config.nickName;
    self.manager = manager;
    self.context = context;
    return self;
}

- (id) initWithManager:(HXPhotoManager *)manager context:(UIViewController *)context {
    self.manager = manager;
    self.context = context;
    return self;
}

- (void) getMediaWithType:(NSUInteger *) type uploadFinishHandler:(UploadFinishHandler)uploadFinishHandler{
    self.uploadFinishHandler = uploadFinishHandler;
    if (type == Select) {
        [self goAlbum];
    } else {
        [self goCamera];
    }
}

- (void) goCamera{
    if(![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        [_context.view hx_showImageHUDText:@"The device does not support cameras"];
        return;
    }
    AVAuthorizationStatus authStatus = [AVCaptureDevice authorizationStatusForMediaType:AVMediaTypeVideo];
    if (authStatus == AVAuthorizationStatusRestricted || authStatus == AVAuthorizationStatusDenied) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Can't use camera" message:@"Please allow access to the camera in settings privacy camera" delegate:self cancelButtonTitle:@"cancel" otherButtonTitles:@"settings", nil];
        [alert show];
        return;
    }
    
    [_context hx_presentCustomCameraViewControllerWithManager:self.manager done:^(HXPhotoModel *model, HXCustomCameraViewController *viewController) {
        MMWeakSelf
        NSArray<HXPhotoModel *> *allList = [NSArray arrayWithObject:model];
        [weakSelf uploadFile:allList];
    } cancel:^(HXCustomCameraViewController *viewController) {
        NSSLog(@"cancel");
        MMWeakSelf
        weakSelf.uploadFinishHandler(nil);
    }];
    
}

- (void) goAlbum{
    [_context hx_presentSelectPhotoControllerWithManager:self.manager didDone:^(NSArray<HXPhotoModel *> *allList, NSArray<HXPhotoModel *> *photoList, NSArray<HXPhotoModel *> *videoList, BOOL isOriginal, UIViewController *viewController, HXPhotoManager *manager) {
        
        NSSLog(@"block - all - %@",allList);
        NSSLog(@"block - photo - %@",photoList);
        NSSLog(@"block - video - %@",videoList);
        MMWeakSelf
        [weakSelf uploadFile:allList];
        
    } cancel:^(UIViewController *viewController, HXPhotoManager *manager) {
        NSSLog(@"block - 取消了");
        MMWeakSelf
        weakSelf.uploadFinishHandler(nil);
    }];
}

- (void)uploadFile : (NSArray<HXPhotoModel *> *) allList{
    if ([allList objectAtIndex:0].subType == HXPhotoModelMediaSubTypePhoto){
        [self uploadPhoto:allList];
    } else {
        [self uploadVideo:[allList objectAtIndex:0]];
    }
}

- (void)uploadPhoto : (NSArray<HXPhotoModel *> *) allList {
    self.fileCount = (int)[allList count];
    self.resultFileList = [NSMutableArray arrayWithCapacity:self.fileCount];
    self.resultFileListWithImage=[NSMutableDictionary dictionaryWithCapacity:self.fileCount];
    self.finishCount = 0;
    for( HXPhotoModel *model in allList) {
        ResultFile *resultFile = [[ResultFile alloc] init];
        [self.resultFileList addObject:resultFile];

        CGSize size;
        float precent = 1;
        size = CGSizeMake(model.imageSize.width * precent, model.imageSize.height * precent);
        resultFile.metadata = [NSString stringWithFormat:@"%i#%i",(int)size.width,(int)size.height];
        resultFile.type = @"picture";
        [model requestPreviewImageWithSize:size startRequestICloud:^(PHImageRequestID iCloudRequestId, HXPhotoModel * _Nullable model) {
            
        } progressHandler:^(double progress, HXPhotoModel * _Nullable model) {
            
        } success:^(UIImage * _Nullable image, HXPhotoModel * _Nullable model, NSDictionary * _Nullable info) {

            NSLog(@"Info: %@", info);
            NSLog(@"video size=%lu M",model.assetByte/1024);
            
            resultFile.originalPath=[DTImageEdit saveImage:image];

            [UploadUtil uploadPhoto2WithURL:pictureURL sessionId:self.seesionid image:image
                       uploadSuccessHandler:^(id  _Nullable responseObject) {
                           NSDictionary *response = (NSDictionary *)responseObject;
                           if ([response objectForKey:@"data"] != nil){
                               [self.resultFileListWithImage setObject:image forKey:[response objectForKey:@"data"]];
                           }
                           [self uploadResult:response success:YES resultFile:resultFile];

                       } uploadFailureHandler:^(id  _Nullable responseObject,NSError * error) {
                           NSDictionary *response = (NSDictionary *)responseObject;
                           if ([response objectForKey:@"message"] == nil) {
                               // [response setValue:error forKey:@"message"];
                               response = @{@"message": [NSString stringWithFormat:@"%ld:%@",(long)[error code],[error localizedDescription]]};
                           }
                           [self uploadResult:response success:NO resultFile:resultFile];
                       }];
        } failed:^(NSDictionary * _Nullable info, HXPhotoModel * _Nullable model) {
            
            NSLog(@"Error: %@", info);
        }];

    }
}
//
- (void)uploadVideo : (HXPhotoModel *)model {
    self.fileCount = 2;
    self.resultFileList = [NSMutableArray arrayWithCapacity:self.fileCount];
    self.finishCount = 0;
    ResultFile *resultFile = [[ResultFile alloc] init];
    resultFile.type=@"video";
    [self.resultFileList addObject:resultFile];
    ResultFile *thumbnail = [[ResultFile alloc] init];
    thumbnail.type=@"picture";
    [self.resultFileList addObject:thumbnail];
    [model requestAVAssetStartRequestICloud:nil progressHandler:nil success:^(AVAsset * _Nullable avAsset, AVAudioMix * _Nullable audioMix, HXPhotoModel * _Nullable model, NSDictionary * _Nullable info) {

    } failed:nil];

    
    [model requestAVAssetExportSessionStartRequestICloud:nil progressHandler:nil success:^(AVAssetExportSession * _Nullable assetExportSession, HXPhotoModel * _Nullable model, NSDictionary * _Nullable info) {

    } failed:nil];

    
    [model exportVideoWithPresetName:AVAssetExportPreset640x480 startRequestICloud:nil iCloudProgressHandler:nil exportProgressHandler:^(float progress, HXPhotoModel * _Nullable model) {
        
    } success:^(NSURL * _Nullable videoURL, HXPhotoModel * _Nullable model) {
        NSLog(@"video size=%lu M",model.assetByte/(1024*1024));

        
        resultFile.originalPath=[[videoURL absoluteString] lastPathComponent];
        
        if (model.thumbPhoto!=nil) {
            thumbnail.originalPath=[DTImageEdit saveImage:model.thumbPhoto];
        } else {
            AVURLAsset  *asset = [AVURLAsset assetWithURL:videoURL];
            NSArray *array = asset.tracks;
            CGSize videoSize = CGSizeZero;

            for(AVAssetTrack  *track in array)
            {
                if([track.mediaType isEqualToString:AVMediaTypeVideo])
                {
                    videoSize = track.naturalSize;
                }
            }

            CGSize size;
            size = CGSizeMake(videoSize.height, videoSize.width);
            [model highQualityRequestThumbImageWithWidth:videoSize.width completion:^(UIImage * _Nullable image, HXPhotoModel * _Nullable model, NSDictionary * _Nullable info) {
                thumbnail.originalPath=[DTImageEdit saveImage:image];
            }];
        }



            [UploadUtil uploadVideoWithURL:videoUploadURL sessionId:self.seesionid videoUrl:videoURL
                      uploadSuccessHandler:^(id  _Nullable responseObject) {
                NSDictionary *response = (NSDictionary *)responseObject;
                [self uploadResult:response success:YES resultFile:resultFile];
            }
                      uploadFailureHandler:^(id  _Nullable responseObject,NSError* error) {
                NSDictionary *response = (NSDictionary *)responseObject;
                if ([response objectForKey:@"message"] == nil) {
                    
                    response = @{@"message": [NSString stringWithFormat:@"%ld:%@",(long)[error code],[error localizedDescription]]};
                }
                [self uploadResult:response success:NO resultFile:resultFile];
            }];


            thumbnail.metadata = [NSString stringWithFormat:@"%i#%i",(int)model.asset.pixelWidth,(int)model.asset.pixelHeight];

            [self uploadThumbnail:model videoURL:videoURL];


    } failed:nil];

}

-(void) uploadThumbnail : (HXPhotoModel *) model  videoURL :(NSURL *) videoURL{
    if (model.thumbPhoto!=nil) {
        [UploadUtil uploadPhoto2WithURL:videoUploadURL sessionId:self.seesionid image:model.thumbPhoto
                   uploadSuccessHandler:^(id  _Nullable responseObject) {
            NSDictionary *response = (NSDictionary *)responseObject;
            [self uploadResult:response success:YES resultFile:[self.resultFileList objectAtIndex:1]];
        }
                   uploadFailureHandler:^(id  _Nullable responseObject,NSError* error) {
            NSDictionary *response = (NSDictionary *)responseObject;
            if ([response objectForKey:@"message"] == nil) {
                // [response setValue:error forKey:@"message"];
                response = @{@"message": [NSString stringWithFormat:@"%ld:%@",(long)[error code],[error localizedDescription]]};
            }
            [self uploadResult:response success:NO resultFile:[self.resultFileList objectAtIndex:1]];
        }];
    } else {
        AVURLAsset  *asset = [AVURLAsset assetWithURL:videoURL];
        NSArray *array = asset.tracks;
        CGSize videoSize = CGSizeZero;

        for(AVAssetTrack  *track in array)
        {
            if([track.mediaType isEqualToString:AVMediaTypeVideo])
            {
                videoSize = track.naturalSize;
            }
        }

        CGSize size;
        size = CGSizeMake(videoSize.height, videoSize.width);
        [model highQualityRequestThumbImageWithWidth:videoSize.width completion:^(UIImage * _Nullable image, HXPhotoModel * _Nullable model, NSDictionary * _Nullable info) {
            [UploadUtil uploadPhoto2WithURL:videoUploadURL sessionId:self.seesionid image:image
                       uploadSuccessHandler:^(id  _Nullable responseObject) {
                NSDictionary *response = (NSDictionary *)responseObject;
                [self uploadResult:response success:YES resultFile:[self.resultFileList objectAtIndex:1]];
            }
                       uploadFailureHandler:^(id  _Nullable responseObject,NSError* error) {
                NSDictionary *response = (NSDictionary *)responseObject;
                if ([response objectForKey:@"message"] == nil) {
                    // [response setValue:error forKey:@"message"];
                    response = @{@"message": [NSString stringWithFormat:@"%ld:%@",(long)[error code],[error localizedDescription]]};
                }
                [self uploadResult:response success:NO resultFile:[self.resultFileList objectAtIndex:1]];
            }];
        }];
    }
}
//
-(void) uploadResult : (NSDictionary *) response success :(BOOL) success resultFile :(ResultFile *)resultFile {

    if (success) {
        NSLog(@"ufileid: %@",[response objectForKey:@"data"]);
        resultFile.uFileId = [response objectForKey:@"data"];
        resultFile.success = YES;
    } else {
        NSLog(@"Error: %@",[response objectForKey:@"message"]);
        resultFile.errorCause = [response objectForKey:@"message"];
        resultFile.success = NO;
    }

    if (++self.finishCount==self.fileCount) {
        self.uploadFinishHandler(self.resultFileList);
//        [self addPhotoThumbnail];
    }
}


-(float) calculateCompressPercentWithWidth:(CGFloat) width Height:(CGFloat) height{
    width = width <= height ? width : height;
    return width <= defaultPhotoWidth ? 1.0 : (float) defaultPhotoWidth / width;
}


@end
