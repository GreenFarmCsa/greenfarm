

@interface PhotoConfig : NSObject

@property(atomic,assign) bool capture;
@property(atomic,assign) NSInteger maxSelectable;
@property(atomic,retain) NSString* mimeType;

-(id)init;
-(id)initPhotoCapture:(bool)mCapture withMaxSelectable:(NSInteger) mMaxSelectable withMimeType:(NSString*) mimeType;

@end
