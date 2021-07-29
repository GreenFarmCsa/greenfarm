
#import <Foundation/Foundation.h>
#import "PhotoConfig.h"

@implementation PhotoConfig
@synthesize capture,maxSelectable,mimeType;
-(id)init{
    return [self initPhotoCapture:true withMaxSelectable:9 withMimeType:@"image/video"];
}

-(id)initPhotoCapture:(bool)mCapture withMaxSelectable:(NSInteger) mMaxSelectable withMimeType:(NSString*) mMimeType{
    if(self=[super init]){
        capture=mCapture;
        maxSelectable=mMaxSelectable;
        mimeType=mMimeType;
    }
    return self;
}


@end
