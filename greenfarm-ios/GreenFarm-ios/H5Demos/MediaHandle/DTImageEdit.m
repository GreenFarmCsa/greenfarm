

#import <Foundation/Foundation.h>
#import "DTImageEdit.h"
#import <UIKit/UIKit.h>

static const CGFloat DEFAULT_WATERMARK_SIZE = 20;
static const CGFloat DEFAULT_WATERMARK_MARGIN = 8;
static const CGFloat DEFAULT_PHOTO_WIDTH = 720;
static const CGFloat DEFAULT_THUMBNAIL_WIDTH = 500;
static const CGFloat DEFAULT_WATERMARK_WIDTH = 25;
static const int DEFAULT_WATERMARK_RATIO = 20;
static CGFloat watermarkSize;
static CGFloat watermarkMargin;
static CGSize watermarkRect;
@interface DTImageEdit()
@end

@implementation DTImageEdit
+(UIImage *) addWatermarkToImage:(UIImage *)bgImage withContent:(NSString *)content{
    
    UIFont *font = [UIFont boldSystemFontOfSize:24];
    UIImage *waterImage = [self imageWithString:content font:font width:50 textAlignment:NSTextAlignmentRight];
    UIImageWriteToSavedPhotosAlbum(waterImage, nil, nil, nil);
    [self watermarkSizeAdapter:bgImage.size];
    
    UIGraphicsBeginImageContextWithOptions(bgImage.size, NO, 0.0);
    
    [bgImage drawInRect:CGRectMake(0, 0, bgImage.size.width, bgImage.size.height)];
    
    CGFloat waterW = waterImage.size.width * watermarkSize;
    CGFloat waterH = waterImage.size.height * watermarkSize;
    CGFloat waterX = bgImage.size.width - waterW - watermarkMargin;
    CGFloat waterY = bgImage.size.height - waterH - watermarkMargin;
    [waterImage drawInRect:CGRectMake(waterX, waterY, waterW, waterH)];
    
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    return newImage;
    
    
}

+(UIImage *)watermarkImage:(UIImage *)img withName:(NSString *)name

{
    [self watermarkSizeAdapter:img.size];
    NSString* mark = name;
    int w = img.size.width;
    int h = img.size.height;
    
    UIGraphicsBeginImageContext(img.size);
    [img drawInRect:CGRectMake(0, 0, w, h)];
    
    UIFont *font = [UIFont systemFontOfSize:watermarkSize];
    /// Make a copy of the default paragraph style
    NSMutableParagraphStyle *paragraphStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    /// Set line break mode
    paragraphStyle.lineBreakMode = NSLineBreakByTruncatingTail;
    /// Set text alignment
    paragraphStyle.alignment = NSTextAlignmentRight;
    
    NSDictionary *attributes = @{ NSFontAttributeName: font,
                                  NSForegroundColorAttributeName : [UIColor colorWithWhite:1.0 alpha:0.8],
                                  NSParagraphStyleAttributeName: paragraphStyle };
    
    [mark drawInRect:CGRectMake(w-watermarkRect.width-watermarkMargin, h-watermarkRect.height-watermarkMargin, watermarkRect.width, watermarkRect.height) withAttributes:attributes];
    //    [mark drawInRect:CGRectMake(w-sizeH.width-watermarkMargin, h-sizeH.height-watermarkMargin, sizeH.width, sizeH.height) withFont:[UIFont systemFontOfSize:watermarkSize] lineBreakMode:NSLineBreakByTruncatingTail alignment:(NSTextAlignment)NSTextAlignmentRight];
    
    UIImage *aimg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return aimg;
    
}
+(void) watermarkSizeAdapter:(CGSize)size {
    CGFloat width = size.width>size.height? size.height : size.width;
    watermarkSize = width/720 * DEFAULT_WATERMARK_SIZE;
    watermarkMargin = width/720 * DEFAULT_WATERMARK_MARGIN;
    CGFloat waterRectWidth = width/720 * DEFAULT_WATERMARK_WIDTH;
    watermarkRect = CGSizeMake(DEFAULT_WATERMARK_RATIO*waterRectWidth,waterRectWidth);
}

+(UIImage *)imageWithString:(NSString *)string font:(UIFont *)font width:(CGFloat)width textAlignment:(NSTextAlignment)textAlignment
{
    NSDictionary *attributeDic = @{NSFontAttributeName:font,
                                   NSForegroundColorAttributeName : [UIColor whiteColor]
                                   };
    
    CGSize size = [string boundingRectWithSize:CGSizeMake(width, 1000)
                                       options:NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingTruncatesLastVisibleLine
                                    attributes:attributeDic
                                       context:nil].size;
    
    if ([UIScreen.mainScreen respondsToSelector:@selector(scale)])
    {
        if (UIScreen.mainScreen.scale == 2.0)
        {
            UIGraphicsBeginImageContextWithOptions(size, NO, 1.0);
        } else
        {
            UIGraphicsBeginImageContext(size);
        }
    }
    else
    {
        UIGraphicsBeginImageContext(size);
    }
    
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    // [[UIColor whiteColor] set];
    
    CGRect rect = CGRectMake(0, 0, size.width + 1, size.height + 1);
    
    CGContextFillRect(context, rect);
    
    
    NSMutableParagraphStyle *paragraph = [[NSMutableParagraphStyle alloc] init];
    paragraph.alignment = textAlignment;
    
    NSDictionary *attributes = @ {
    NSForegroundColorAttributeName:[UIColor blackColor],
    NSFontAttributeName:font,
    NSParagraphStyleAttributeName:paragraph
    };
    
    [string drawInRect:rect withAttributes:attributes];
    
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    return image;
}

+(UIImage*)compressImage:(UIImage *)img{
    CGSize size = img.size;
    float percent = [self calculateCompressPercentWithWidth:size.width Height:size.height];
    CGSize thumbSize = CGSizeMake(size.width*percent, size.height*percent);
    UIGraphicsBeginImageContext(thumbSize);
    [img drawInRect:CGRectMake(0, 0, thumbSize.width, thumbSize.height)];
    UIImage *resultImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return resultImage;
}

+(float) calculateCompressPercentWithWidth:(CGFloat) width Height:(CGFloat) height{
    width = width <= height ? width : height;
    return width <= DEFAULT_THUMBNAIL_WIDTH ? 1.0 : (float) DEFAULT_THUMBNAIL_WIDTH / width;
}


+ (NSString *)saveImage:(UIImage *)tempImage
{
    NSString *image_fileType = @"";
    if (UIImagePNGRepresentation(tempImage) == nil) {
        image_fileType = @"JPEG";
    }else{
        image_fileType = @"PNG";
    }
    NSString *imageName = [NSString stringWithFormat:@"/%d.%@",1000000+(arc4random() % 999999999),image_fileType];
    NSData * imageData = UIImagePNGRepresentation(tempImage);
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString * documentsDirectory = [paths objectAtIndex:0];
    // Now we get the full path to the file
    NSString * fullPathToFile = [documentsDirectory stringByAppendingString:imageName];
    // and then we write it out
    [imageData writeToFile:fullPathToFile atomically:NO];
    
    return imageName;
}

+(UIImage *)readImage:(NSString *)key{
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString * documentsDirectory = [paths objectAtIndex:0];
    // Now we get the full path to the file
    NSString * fullPathToFile = [documentsDirectory stringByAppendingString:key];
    NSData * data = [NSData dataWithContentsOfFile:fullPathToFile];
    UIImage * image = [UIImage imageWithData:data];
    
    return image;
}

@end
