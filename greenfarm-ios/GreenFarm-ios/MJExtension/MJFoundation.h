

#import <Foundation/Foundation.h>

@interface MJFoundation : NSObject

+ (BOOL)isClassFromFoundation:(Class)c;
+ (BOOL)isFromNSObjectProtocolProperty:(NSString *)propertyName;

@end
