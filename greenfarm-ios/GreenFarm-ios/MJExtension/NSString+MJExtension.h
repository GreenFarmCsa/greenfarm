

#import <Foundation/Foundation.h>
#import "MJExtensionConst.h"

@interface NSString (MJExtension)

- (NSString *)mj_underlineFromCamel;

- (NSString *)mj_camelFromUnderline;

- (NSString *)mj_firstCharUpper;

- (NSString *)mj_firstCharLower;

- (BOOL)mj_isPureInt;

- (NSURL *)mj_url;
@end
