
#import <Foundation/Foundation.h>
#import "MediaConfig.h"
@implementation MediaConfig

@synthesize photoConfig,videoConfig,session,sessionId,maxSelectable,maxRecordTime,mimeType,capture,nickName;

-(id)initConfig:(id)config withSession:(NSString*) mSession{
    if(self=[super init]){
        if([config respondsToSelector:@selector(initPhotoCapture:withMaxSelectable:withMimeType:)]){
            photoConfig=config;
        }else{
            videoConfig=config;
        }
        session=mSession;
    }
    return self;
}

-(id)initSessionId:(NSString*)_sessionId withMaxSelectable:(NSInteger)_maxSelectable withMimeType:(NSInteger)_mimeType withMaxRecordTime:(NSInteger)_maxRecordTime withCapture:(Boolean)_capture{
    if(self=[super init]){
        sessionId=_sessionId;
        maxSelectable=_maxSelectable;
        mimeType=_mimeType;
        maxRecordTime=_maxRecordTime;
        capture=_capture;
    }
    return self;
}

-(id)initSessionId:(NSString*)_sessionId withMaxSelectable:(NSInteger)_maxSelectable withMimeType:(NSInteger)_mimeType withMaxRecordTime:(NSInteger)_maxRecordTime withCapture:(Boolean)_capture withNickName:(NSString*)_nickName{
    if(self=[super init]){
        sessionId=_sessionId;
        maxSelectable=_maxSelectable;
        mimeType=_mimeType;
        maxRecordTime=_maxRecordTime;
        capture=_capture;
        nickName=_nickName;
    }
    return self;
}

-(NSString*) description{
    return [NSString stringWithFormat:@"photoConfig=[capture=%d,maxSelectable=%ld,mimeType=%@];videoConfig=[recordTime=%d,minTrimTime=%d,maxTrimTime=%d];session=%@",
            photoConfig.capture,(long)photoConfig.maxSelectable,photoConfig.mimeType,videoConfig.recordTime,videoConfig.minTrimTime,videoConfig.maxTrimTime,session];
}

@end
