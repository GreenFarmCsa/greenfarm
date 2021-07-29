

#ifndef DTImageEdit_h
#define DTImageEdit_h
#import <UIKit/UIKit.h>

@interface DTImageEdit : NSObject

+(UIImage *)addWatermarkToImage:(UIImage *)img withContent:(NSString *)content;
+(UIImage *)watermarkImage:(UIImage *)img withName:(NSString *)name;
+(UIImage *)compressImage:(UIImage *)img;
+(NSString *)saveImage:(UIImage *)tempImage;
+(UIImage *)readImage:(NSString *)fullPathToFile;

@end

#endif /* DTImageEdit_h */
