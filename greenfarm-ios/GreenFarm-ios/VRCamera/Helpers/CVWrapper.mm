

#import "CVWrapper.h"
#import "UIImage+OpenCV.h"
#import "stitching.hpp"
#import "UIImage+Rotate.h"


@implementation CVWrapper

+ (UIImage*) processImageWithOpenCV: (UIImage*) inputImage
{
    NSArray* imageArray = [NSArray arrayWithObject:inputImage];
    UIImage* result = [[self class] processWithArray:imageArray];
    return result;
}

+ (UIImage*) processWithOpenCVImage1:(UIImage*)inputImage1 image2:(UIImage*)inputImage2;
{
    NSArray* imageArray = [NSArray arrayWithObjects:inputImage1,inputImage2,nil];
    UIImage* result = [[self class] processWithArray:imageArray];
    return result;
}

+ (UIImage*) processWithArray:(NSArray*)imageArray
{
    if ([imageArray count]==0){
        NSLog (@"imageArray is empty");
        return 0;
    }
    std::vector<cv::Mat> matImages;

    for (id image in imageArray) {
        if ([image isKindOfClass: [UIImage class]]) {
            /*
             All images taken with the iPhone/iPa cameras are LANDSCAPE LEFT orientation. The  UIImage imageOrientation flag is an instruction to the OS to transform the image during display only. When we feed images into openCV, they need to be the actual orientation that we expect them to be for stitching. So we rotate the actual pixel matrix here if required.
             */
            UIImage* rotatedImage = [image rotateToImageOrientation];
            cv::Mat matImage = [rotatedImage CVMat3];
            NSLog (@"matImage: %@",image);
            matImages.push_back(matImage);
        }
    }
    NSLog (@"stitching...");
    cv::Mat stitchedMat;
    //cv::Mat stitchedMat = stitch (matImages);
    cv::Mat stitchedMat1;
    try {
        stitchedMat = stitch (matImages);
        corpBoundingRect(stitchedMat, stitchedMat1);
    } catch (cv::Exception exception) {
        return [imageArray objectAtIndex:0];
    }
    UIImage* result =  [UIImage imageWithCVMat:stitchedMat1];
    return result;
}

+ (UIImage *)imageWithImage:(UIImage *)image Scale:(CGFloat)scale
{
    CGFloat width = image.size.width * scale;
    CGFloat heigth = image.size.height * scale;
    UIGraphicsBeginImageContext(CGSizeMake(width, heigth));
    [image drawInRect:CGRectMake(0, 0, width, heigth)];
    UIImage * resultImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return resultImage;
}

@end
