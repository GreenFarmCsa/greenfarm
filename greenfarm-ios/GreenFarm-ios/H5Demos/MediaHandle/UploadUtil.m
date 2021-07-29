

#import <Foundation/Foundation.h>
#import "UploadUtil.h"
#import "AFNetworking.h"

@implementation UploadUtil

+ (void)uploadPhotoWithURL:(NSString *)URL sessionId:(NSString *)sessionId image:(UIImage *)image {
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    
    manager.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"application/json",@"text/html",@"image/jpeg",@"image/jpg",@"application/octet-stream",@"text/json",nil];
    manager.responseSerializer= [AFHTTPResponseSerializer serializer];
    manager.requestSerializer = [AFJSONRequestSerializer new];
    manager.requestSerializer.timeoutInterval = 20.0;
//    NSDictionary * headers = @{@"Authorization":sessionId,@"ReqSource":@"ditingMobile",@"X-Requested-With":@"XMLHttpRequest"};
    NSDictionary * headers = @{@"X-Requested-With":@"XMLHttpRequest"};   //  [manager.requestSerializer setValue:sessionId forHTTPHeaderField:@"Authorization"];
//    [manager.requestSerializer setValue:@"ditingMobile" forHTTPHeaderField:@"ReqSource"];
    NSData *data = UIImageJPEGRepresentation(image,0.8);
    NSDictionary *para = @{@"file":data};
    [manager POST:URL parameters:para headers:headers  constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        //上传的参数(上传图片，以文件流的格式)
        NSDate *datanow = [NSDate date];
        NSString *timesta = [NSString stringWithFormat:@"%lid", (long)[datanow timeIntervalSince1970]];
        NSString *filename = [timesta stringByAppendingString:@".jpg"];
        [formData appendPartWithFileData:data name:@"file" fileName:filename mimeType:@"application/octet-stream"];
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSInteger errCode = [responseObject[@"errorCode"] integerValue];
        if (errCode == 0) {
            NSLog(@"upload success%@",responseObject);
        }else{
            NSLog(@"upload error%@",responseObject[@"errMessage"]);
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"upload error%@",error.description);
    }];
}

+(void) uploadPhoto2WithURL:(const NSString *)URL sessionId:(NSString *)sessionId image:(UIImage *)image
               uploadSuccessHandler:(UploadSuccessHandler)uploadSuccessHandler uploadFailureHandler:(UploadFailureHandler)uploadFailureHandler{
    NSDate *datanow = [NSDate date];
    NSString *timesta = [NSString stringWithFormat:@"%lid", (long)[datanow timeIntervalSince1970]];
    NSString *filename = [timesta stringByAppendingString:@".jpg"];
    NSData *data = UIImageJPEGRepresentation(image,0.8);
    NSMutableURLRequest *request = [[AFHTTPRequestSerializer serializer] multipartFormRequestWithMethod:@"POST" URLString:URL parameters:nil constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        [formData appendPartWithFileData:data name:@"file" fileName:filename mimeType:@"application/octet-stream"];
    } error:nil];
    
//    [request setValue:sessionId forHTTPHeaderField:@"Authorization"];
//    [request setValue:@"ditingMobile" forHTTPHeaderField:@"ReqSource"];
 //   [request setValue:@"multipart/form-data" forHTTPHeaderField:@"Content-Type"];
    [request setValue:@"XMLHttpRequest" forHTTPHeaderField:@"X-Requested-With"];
//    [request setValue:@"gzip, deflate, br" forHTTPHeaderField:@"Accept-Encoding"];
//    [request setValue:@"PostmanRuntime/7.24.1" forHTTPHeaderField:@"User-Agent"];
//    [request setValue:@"8114409c-1685-4301-be46-3f2f7f049a8e" forHTTPHeaderField:@"Postman-Token"];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:[NSURLSessionConfiguration ephemeralSessionConfiguration]];
    
    NSURLSessionUploadTask *uploadTask;
    uploadTask = [manager
                  uploadTaskWithStreamedRequest:request
                  progress:^(NSProgress * _Nonnull uploadProgress) {
                      // This is not called back on the main queue.
                      // You are responsible for dispatching to the main queue for UI updates
                  }
                  completionHandler:^(NSURLResponse * _Nonnull response, id  _Nullable responseObject, NSError * _Nullable error) {
                      if (error) {
                          NSLog(@"Error: %@", error);
                          uploadFailureHandler(responseObject,error);
                      } else {
                          NSLog(@"%@ %@", response, responseObject);
                          uploadSuccessHandler(responseObject);
                      }
                  }];
    
    [uploadTask resume];
}

+(void) uploadVideoWithURL:(const NSString *)URL sessionId:(NSString *)sessionId videoUrl:(NSURL *)videoUrl uploadSuccessHandler:(UploadSuccessHandler)uploadSuccessHandler uploadFailureHandler:(UploadFailureHandler)uploadFailureHandler{
    NSDate *datanow = [NSDate date];
    NSString *timesta = [NSString stringWithFormat:@"%lid", (long)[datanow timeIntervalSince1970]];
    NSString *filename = [timesta stringByAppendingString:@".mp4"];
    NSMutableURLRequest *request = [[AFHTTPRequestSerializer serializer] multipartFormRequestWithMethod:@"POST" URLString:URL parameters:nil constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        [formData appendPartWithFileURL:videoUrl name:@"file" fileName:filename mimeType:@"application/octet-stream" error:nil];
    } error:nil];
    
//    [request setValue:sessionId forHTTPHeaderField:@"Authorization"];
//    [request setValue:@"ditingMobile" forHTTPHeaderField:@"ReqSource"];
    [request setValue:@"XMLHttpRequest" forHTTPHeaderField:@"X-Requested-With"];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:[NSURLSessionConfiguration ephemeralSessionConfiguration]];
    
    NSURLSessionUploadTask *uploadTask;
    uploadTask = [manager
                  uploadTaskWithStreamedRequest:request
                  progress:^(NSProgress * _Nonnull uploadProgress) {
                      // This is not called back on the main queue.
                      // You are responsible for dispatching to the main queue for UI updates
                      
                  }
                  completionHandler:^(NSURLResponse * _Nonnull response, id  _Nullable responseObject, NSError * _Nullable error) {
                      if (error) {
                          NSLog(@"Error: %@", error);
                          uploadFailureHandler(responseObject,error);
                      } else {
                          NSLog(@"%@ %@", response, responseObject);
                          uploadSuccessHandler(responseObject);
                      }
                  }];
    
    [uploadTask resume];
}
@end
