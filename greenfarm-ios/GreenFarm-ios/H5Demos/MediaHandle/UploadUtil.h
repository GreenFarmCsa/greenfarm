
#import <UIKit/UIKit.h>

#ifndef UploadUtil_h
#define UploadUtil_h

typedef void (^ UploadSuccessHandler)(id  _Nullable responseObject);
typedef void (^ UploadFailureHandler)(id  _Nullable responseObject,NSError * error);

@interface UploadUtil : NSObject

+ (void)uploadPhotoWithURL:(NSString *)URL sessionId:(NSString *)sessionId image:(UIImage *)image;
+ (void)uploadPhoto2WithURL:(const NSString *)URL sessionId:(NSString *)sessionId image:(UIImage *)image
       uploadSuccessHandler:(UploadSuccessHandler) uploadSuccessHandler
       uploadFailureHandler:(UploadFailureHandler) uploadFailureHandler;

+ (void)uploadVideoWithURL:(const NSString *)URL sessionId:(NSString *)sessionId videoUrl:(NSURL *)videoUrl
      uploadSuccessHandler:(UploadSuccessHandler) uploadSuccessHandler
      uploadFailureHandler:(UploadFailureHandler) uploadFailureHandler;

@end

#endif /* UploadUtil_h */
