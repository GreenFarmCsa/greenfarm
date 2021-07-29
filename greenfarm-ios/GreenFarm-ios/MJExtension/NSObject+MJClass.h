

#import <Foundation/Foundation.h>


typedef void (^MJClassesEnumeration)(Class c, BOOL *stop);

typedef NSArray * (^MJAllowedPropertyNames)(void);
typedef NSArray * (^MJAllowedCodingPropertyNames)(void);

typedef NSArray * (^MJIgnoredPropertyNames)(void);
typedef NSArray * (^MJIgnoredCodingPropertyNames)(void);


@interface NSObject (MJClass)

+ (void)mj_enumerateClasses:(MJClassesEnumeration)enumeration;
+ (void)mj_enumerateAllClasses:(MJClassesEnumeration)enumeration;


+ (void)mj_setupAllowedPropertyNames:(MJAllowedPropertyNames)allowedPropertyNames;


+ (NSMutableArray *)mj_totalAllowedPropertyNames;


+ (void)mj_setupIgnoredPropertyNames:(MJIgnoredPropertyNames)ignoredPropertyNames;


+ (NSMutableArray *)mj_totalIgnoredPropertyNames;


+ (void)mj_setupAllowedCodingPropertyNames:(MJAllowedCodingPropertyNames)allowedCodingPropertyNames;


+ (NSMutableArray *)mj_totalAllowedCodingPropertyNames;


+ (void)mj_setupIgnoredCodingPropertyNames:(MJIgnoredCodingPropertyNames)ignoredCodingPropertyNames;


+ (NSMutableArray *)mj_totalIgnoredCodingPropertyNames;

+ (void)mj_setupBlockReturnValue:(id (^)(void))block key:(const char *)key;
@end
