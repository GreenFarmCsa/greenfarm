
#import <Foundation/Foundation.h>


@interface NSObject (Extension)
- (BOOL)isAvalidString;
@end

@interface NSString (Extension)


+ (NSString *)intToString:(int)text;


+ (NSString *)integerToString:(NSInteger)text;

+ (NSString *)longToString:(int64_t)text;

- (NSString *)append:(NSString *)text;

- (NSString *)replace:(NSString *)oldStr with:(NSString *)newStr;

- (NSArray *)separatedBy:(NSString *)text;

- (NSString *)URLEncodedString;

- (NSString *)turnToHttpUrl;

- (NSString *)turnToHttpsUrl;

- (NSString *)sha1;

- (NSString *)sha256;

- (NSString *)md5;

- (NSString *)base64;

- (NSData *)base64ToData;

- (NSString *)base64Decode;

- (NSData *)turnToUTF8Data;

- (NSDictionary *)jsonToDictionary;

- (NSString *)turnToHHMMSS;

- (NSString *)filterEmpty;

- (BOOL)judgeStringIsEmpty;

- (BOOL)isPureNumbers;

- (BOOL)isSmallerThanTime:(NSString *)time;

+ (NSString *)stringWithBool:(BOOL)flag;

+ (NSString *)hexStringWithLongLong:(long long)decimal;

+ (NSString *)urlStringWithDictionary:(NSDictionary *)dic;

+ (BOOL)isBlankString:(NSString *)string;

+ (NSString *)substringFromWith:(NSString *)str;
@end
