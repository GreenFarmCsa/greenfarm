
#import <Foundation/Foundation.h>
#import "ResultFile.h"
@implementation ResultFile
@synthesize uFileId,fileName,localPath,errorCause,metadata,success,type,originalPath;
-(NSString *)description{
    
    NSString *result = self.success ? @"true" : @"false";

    NSString *str =[NSString stringWithFormat:@"uFileId = %@;success = %@;errorCause = %@;metadata = %@;fileName = %@;localPath=%@;type=%@;originalPath=%@",self.uFileId,result,self.errorCause,self.metadata,self.fileName,self.localPath,self.type,self.originalPath];
    return str;
}

@end
