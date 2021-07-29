
@interface VideoConfig : NSObject

@property(atomic,assign) int recordTime;
@property(atomic,assign) int maxTrimTime;
@property(atomic,assign) int minTrimTime;

-(id) init;
-(id)initVideoRecordTime:(int) mRecordTime withMaxTrimTime:(int)mMaxTrimTime withMinTrimTime:(int)mMinTrimTime;

@end
