

#ifndef DTVideoEdit_h
#define DTVideoEdit_h

@interface DTVideoEdit : NSObject

+(void)addWatermark:(NSString *)waterMark toVideo:(NSURL *)videoUrl complete:(void (^)(NSURL *compressUrl))complete;

@end

#endif /* DTVideoEdit_h */
