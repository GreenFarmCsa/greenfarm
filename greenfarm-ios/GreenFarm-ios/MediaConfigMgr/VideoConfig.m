
#import <Foundation/Foundation.h>
#import "VideoConfig.h"

@implementation VideoConfig

@synthesize recordTime,maxTrimTime,minTrimTime;

-(id)init{
    return [self initVideoRecordTime:30 withMaxTrimTime:30 withMinTrimTime:3];
}

-(id)initVideoRecordTime:(int) mRecordTime withMaxTrimTime:(int)mMaxTrimTime withMinTrimTime:(int)mMinTrimTime{
    if(self=[super init]){
        recordTime=mRecordTime;
        maxTrimTime=mMaxTrimTime;
        minTrimTime=mMinTrimTime;
    }
    return self;
}

@end
