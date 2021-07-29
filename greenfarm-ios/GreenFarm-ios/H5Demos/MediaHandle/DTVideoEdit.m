
#import <AVFoundation/AVFoundation.h>
#import "DTVideoEdit.h"
#import <MediaSupport/UIColor+HXExtension.h>
#define degreesToRadians( degrees ) ( ( degrees ) / 180.0 * M_PI )

static const CGFloat DEFAULT_WATERMARK_SIZE = 10;
static const CGFloat DEFAULT_WATERMARK_MARGIN = 4;
static const CGFloat DEFAULT_VIDEO_WIDTH = 360;
static const CGFloat DEFAULT_WATERMARK_WIDTH = 13;
static const int DEFAULT_WATERMARK_RATIO = 15;
static CGFloat watermarkSize;
static CGFloat watermarkMargin;
static CGSize watermarkRect;
@implementation DTVideoEdit


+(void) addWatermark:(NSString *)waterMark toVideo:(NSURL *)Url complete:(void (^)(NSURL *compressUrl))complete{
    CGFloat degress = 0;
    AVAsset *asset = [AVAsset assetWithURL:Url];
    NSArray *tracks = [asset tracksWithMediaType:AVMediaTypeVideo];
    if([tracks count] > 0) {
        AVAssetTrack *videoTrack = [tracks objectAtIndex:0];
        CGAffineTransform t = videoTrack.preferredTransform;
        
        if(t.a == 0 && t.b == 1.0 && t.c == -1.0 && t.d == 0){
            // Portrait
            degress = 90;
        }else if(t.a == 0 && t.b == -1.0 && t.c == 1.0 && t.d == 0){
            // PortraitUpsideDown
            degress = 270;
        }else if(t.a == 1.0 && t.b == 0 && t.c == 0 && t.d == 1.0){
            // LandscapeRight
            degress = 0;
        }else if(t.a == -1.0 && t.b == 0 && t.c == 0 && t.d == -1.0){
            // LandscapeLeft
            degress = 180;
        }
    }
    
    AVURLAsset *videoAsset = [AVURLAsset assetWithURL:Url];
    
    AVMutableComposition *mixComposition = [[AVMutableComposition alloc] init];
    
    
    AVMutableCompositionTrack *videoTrack = [mixComposition addMutableTrackWithMediaType:AVMediaTypeVideo
                                                                        preferredTrackID:kCMPersistentTrackID_Invalid];
    [videoTrack insertTimeRange:CMTimeRangeMake(kCMTimeZero, videoAsset.duration)
                        ofTrack:[[videoAsset tracksWithMediaType:AVMediaTypeVideo] firstObject]
                         atTime:kCMTimeZero error:nil];
    
    
    AVMutableCompositionTrack *audioTrack = [mixComposition addMutableTrackWithMediaType:AVMediaTypeAudio
                                                                        preferredTrackID:kCMPersistentTrackID_Invalid];
    [audioTrack insertTimeRange:CMTimeRangeMake(kCMTimeZero, videoAsset.duration)
                        ofTrack:[[videoAsset tracksWithMediaType:AVMediaTypeAudio] firstObject]
                         atTime:kCMTimeZero error:nil];
    
    
    AVMutableVideoCompositionInstruction *mainInstruction = [AVMutableVideoCompositionInstruction videoCompositionInstruction];
    mainInstruction.timeRange = CMTimeRangeMake(kCMTimeZero, videoAsset.duration);
    
    
    AVMutableVideoCompositionLayerInstruction *videolayerInstruction = [AVMutableVideoCompositionLayerInstruction videoCompositionLayerInstructionWithAssetTrack:videoTrack];
    
    [videolayerInstruction setOpacity:0.0 atTime:videoAsset.duration];
   
    AVMutableVideoComposition *mainCompositionInst = [AVMutableVideoComposition videoComposition];

    
    AVAssetTrack *videoAssetTrack = [[videoAsset tracksWithMediaType:AVMediaTypeVideo] firstObject];
    CGSize naturalSize;
    if (degress==90||degress==270){
        naturalSize = CGSizeMake(videoAssetTrack.naturalSize.height, videoAssetTrack.naturalSize.width);
    } else {
        naturalSize = videoAssetTrack.naturalSize;
    }
    
    CGAffineTransform translateToCenter;
    CGAffineTransform mixedTransform;
    if(degress == 90){
        
        translateToCenter = CGAffineTransformMakeTranslation(videoTrack.naturalSize.height,0.0);
        mixedTransform = CGAffineTransformRotate(translateToCenter,M_PI_2);
        [videolayerInstruction setTransform:mixedTransform atTime:kCMTimeZero];
    }else if(degress == 180){
        
        translateToCenter = CGAffineTransformMakeTranslation(videoTrack.naturalSize.width, videoTrack.naturalSize.height);
        mixedTransform = CGAffineTransformRotate(translateToCenter,M_PI);
        [videolayerInstruction setTransform:mixedTransform atTime:kCMTimeZero];
    }else if(degress == 270){
        
        translateToCenter = CGAffineTransformMakeTranslation(0.0, videoAssetTrack.naturalSize.width);
        mixedTransform = CGAffineTransformRotate(translateToCenter,M_PI_2*3.0);
        [videolayerInstruction setTransform:mixedTransform atTime:kCMTimeZero];
    }
    
    
    // 3.3 - Add instructions
    mainInstruction.layerInstructions = [NSArray arrayWithObjects:videolayerInstruction,nil];
    
    
   
    
    float renderWidth, renderHeight;
    renderWidth = naturalSize.width;
    renderHeight = naturalSize.height;
    mainCompositionInst.renderSize = CGSizeMake(renderWidth, renderHeight);
    mainCompositionInst.instructions = [NSArray arrayWithObject:mainInstruction];
    mainCompositionInst.frameDuration = CMTimeMake(1, 30);
    [self applyVideoEffectsToComposition:mainCompositionInst content:waterMark size:naturalSize];
    
    NSDate *datanow = [NSDate date];
    NSString *timesta = [NSString stringWithFormat:@"%lid_watermark", (long)[datanow timeIntervalSince1970]];
    NSString *fileName = [timesta stringByAppendingString:@".mp4"];
    
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *myPathDocs =  [documentsDirectory stringByAppendingPathComponent:fileName];
    
    [[NSFileManager defaultManager] removeItemAtPath:myPathDocs error:nil];
    NSURL* videoUrl = [NSURL fileURLWithPath:myPathDocs];
    
    
    AVAssetExportSession *exporter = [[AVAssetExportSession alloc] initWithAsset:mixComposition
                                                                      presetName:AVAssetExportPresetHighestQuality];
    exporter.outputURL = videoUrl;
    exporter.outputFileType = AVFileTypeMPEG4;
    exporter.shouldOptimizeForNetworkUse = YES;
    exporter.videoComposition = mainCompositionInst;
    [exporter exportAsynchronouslyWithCompletionHandler:^{
        dispatch_async(dispatch_get_main_queue(), ^{
            if( exporter.status == AVAssetExportSessionStatusCompleted ){
                NSURL *compressURL = exporter.outputURL;
             
                complete(compressURL);
            }else if( exporter.status == AVAssetExportSessionStatusFailed ){
                NSLog(@"导出failed");
                complete(nil);
            }
        });
    }];
}

+ (void)applyVideoEffectsToComposition:(AVMutableVideoComposition *)composition content:(NSString *)content size:(CGSize)size
{
    [self watermarkSizeAdapter:size];
 
    CATextLayer *subtitle1Text = [[CATextLayer alloc] init];
    //    [subtitle1Text setFont:@"Helvetica-Bold"];
    [subtitle1Text setAlignmentMode:kCAAlignmentRight];
    [subtitle1Text setFontSize:watermarkSize];
    subtitle1Text.wrapped = NO;//自动换行
    [subtitle1Text setFrame:CGRectMake(size.width-watermarkMargin-watermarkRect.width, watermarkMargin, watermarkRect.width, watermarkRect.height)];
    [subtitle1Text setString:content];
    //    [subtitle1Text setAlignmentMode:kCAAlignmentCenter];
    [subtitle1Text setForegroundColor:[[UIColor colorWithWhite:1.0 alpha:0.8] CGColor]];
    
    
    //    CALayer*picLayer = [CALayer layer];
    //    picLayer.contents = (id)[UIImage imageNamed:@"videoWater2"].CGImage;
    //    picLayer.frame = CGRectMake(size.width-15-87, 15, 87, 26);
    
    // 2 - The usual overlay
    CALayer *overlayLayer = [CALayer layer];
    //    [overlayLayer addSublayer:picLayer];
    [overlayLayer addSublayer:subtitle1Text];
    overlayLayer.frame = CGRectMake(0, 0, size.width, size.height);
    [overlayLayer setMasksToBounds:YES];
    
    CALayer *parentLayer = [CALayer layer];
    CALayer *videoLayer = [CALayer layer];
    parentLayer.frame = CGRectMake(0, 0, size.width, size.height);
    videoLayer.frame = CGRectMake(0, 0, size.width, size.height);
    [parentLayer addSublayer:videoLayer];
    [parentLayer addSublayer:overlayLayer];
    
    composition.animationTool = [AVVideoCompositionCoreAnimationTool videoCompositionCoreAnimationToolWithPostProcessingAsVideoLayer:videoLayer inLayer:parentLayer];
}


+(CGFloat)ytfileSize:(NSURL *)path{
    return [[NSData dataWithContentsOfURL:path] length]/1024.00 /1024.00;
}

+(void) watermarkSizeAdapter:(CGSize)size {
    CGFloat width = size.width>size.height? size.height : size.width;
    watermarkSize = width/DEFAULT_VIDEO_WIDTH * DEFAULT_WATERMARK_SIZE;
    watermarkMargin = width/DEFAULT_VIDEO_WIDTH * DEFAULT_WATERMARK_MARGIN;
    CGFloat waterRectWidth = width/DEFAULT_VIDEO_WIDTH * DEFAULT_WATERMARK_WIDTH;
    watermarkRect = CGSizeMake(DEFAULT_WATERMARK_RATIO*waterRectWidth,waterRectWidth);
}

@end
