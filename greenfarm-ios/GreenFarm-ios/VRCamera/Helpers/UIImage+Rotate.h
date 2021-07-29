

#import <UIKit/UIKit.h>

@interface UIImage (Rotate)

//faster, alters the exif flag but doesn't change the pixel data
- (UIImage*)rotateExifToOrientation:(UIImageOrientation)orientation;


//slower, rotates the actual pixel matrix
- (UIImage*)rotateBitmapToOrientation:(UIImageOrientation)orientation;

- (UIImage*)rotateToImageOrientation;

@end
