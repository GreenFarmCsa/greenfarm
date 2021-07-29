
@interface ResultFile : NSObject

@property (strong, nonatomic) NSString *type;
@property (strong, nonatomic) NSString *uFileId;
@property (strong, nonatomic) NSString *fileName;
@property (strong, nonatomic) NSString *localPath;
@property (strong, nonatomic) NSString *errorCause;
@property (strong, nonatomic) NSString *metadata;
@property (assign, nonatomic) BOOL success;
@property (strong, nonatomic) NSString *originalPath;
-(NSString *)description;
@end
