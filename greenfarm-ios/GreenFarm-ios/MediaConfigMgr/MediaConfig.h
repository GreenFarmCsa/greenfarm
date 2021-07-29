
#import "PhotoConfig.h"
#import "VideoConfig.h"

@interface MediaConfig : NSObject

@property(atomic,retain) NSString* session;
@property(atomic,retain) PhotoConfig* photoConfig;
@property(atomic,retain) VideoConfig* videoConfig;
// bu lmg
@property(atomic,retain) NSString* nickName;
@property(atomic,retain) NSString* sessionId;
@property(atomic,assign) NSInteger maxSelectable;
@property(atomic,assign) NSInteger mimeType;
@property(atomic,assign) NSInteger maxRecordTime;
@property(atomic,assign) Boolean capture;


-(id)initConfig:(id)config withSession:(NSString*) mSession;
-(id)initSessionId:(NSString*)_sessionId withMaxSelectable:(NSInteger)_mMaxSelectable withMimeType:(NSInteger)_mimeType withMaxRecordTime:(NSInteger)_mMaxRecordTime withCapture:(Boolean)_capture;
-(id)initSessionId:(NSString*)_sessionId withMaxSelectable:(NSInteger)_mMaxSelectable withMimeType:(NSInteger)_mimeType withMaxRecordTime:(NSInteger)_mMaxRecordTime withCapture:(Boolean)_capture withNickName:(NSString*)_nickName;
-(NSString*) description;
@end
