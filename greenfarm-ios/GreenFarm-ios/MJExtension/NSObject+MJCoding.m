

#import "NSObject+MJCoding.h"
#import "NSObject+MJClass.h"
#import "NSObject+MJProperty.h"
#import "MJProperty.h"

@implementation NSObject (MJCoding)

- (void)mj_encode:(NSCoder *)encoder
{
    Class clazz = [self class];
    
    NSArray *allowedCodingPropertyNames = [clazz mj_totalAllowedCodingPropertyNames];
    NSArray *ignoredCodingPropertyNames = [clazz mj_totalIgnoredCodingPropertyNames];
    
    [clazz mj_enumerateProperties:^(MJProperty *property, BOOL *stop) {
        if (allowedCodingPropertyNames.count && ![allowedCodingPropertyNames containsObject:property.name]) return;
        if ([ignoredCodingPropertyNames containsObject:property.name]) return;
        
        id value = [property valueForObject:self];
        if (value == nil) return;
        [encoder encodeObject:value forKey:property.name];
    }];
}

- (void)mj_decode:(NSCoder *)decoder
{
    Class clazz = [self class];
    
    NSArray *allowedCodingPropertyNames = [clazz mj_totalAllowedCodingPropertyNames];
    NSArray *ignoredCodingPropertyNames = [clazz mj_totalIgnoredCodingPropertyNames];
    
    [clazz mj_enumerateProperties:^(MJProperty *property, BOOL *stop) {
        if (allowedCodingPropertyNames.count && ![allowedCodingPropertyNames containsObject:property.name]) return;
        if ([ignoredCodingPropertyNames containsObject:property.name]) return;
        
        id value = [decoder decodeObjectForKey:property.name];
        if (value == nil) {
            value = [decoder decodeObjectForKey:[@"_" stringByAppendingString:property.name]];
        }
        if (value == nil) return;
        [property setValue:value forObject:self];
    }];
}
@end
