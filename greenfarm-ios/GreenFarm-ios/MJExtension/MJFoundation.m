

#import "MJFoundation.h"
#import "MJExtensionConst.h"
#import <CoreData/CoreData.h>
#import "objc/runtime.h"

@implementation MJFoundation

+ (BOOL)isClassFromFoundation:(Class)c
{
    if (c == [NSObject class] || c == [NSManagedObject class]) return YES;
    
    static NSSet *foundationClasses;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        foundationClasses = [NSSet setWithObjects:
                             [NSURL class],
                             [NSDate class],
                             [NSValue class],
                             [NSData class],
                             [NSError class],
                             [NSArray class],
                             [NSDictionary class],
                             [NSString class],
                             [NSAttributedString class], nil];
    });
    
    __block BOOL result = NO;
    [foundationClasses enumerateObjectsUsingBlock:^(Class foundationClass, BOOL *stop) {
        if ([c isSubclassOfClass:foundationClass]) {
            result = YES;
            *stop = YES;
        }
    }];
    return result;
}

+ (BOOL)isFromNSObjectProtocolProperty:(NSString *)propertyName
{
    if (!propertyName) return NO;
    
    static NSSet<NSString *> *objectProtocolPropertyNames;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        unsigned int count = 0;
        objc_property_t *propertyList = protocol_copyPropertyList(@protocol(NSObject), &count);
        NSMutableSet *propertyNames = [NSMutableSet setWithCapacity:count];
        for (int i = 0; i < count; i++) {
            objc_property_t property = propertyList[i];
            NSString *propertyName = [NSString stringWithCString:property_getName(property) encoding:NSUTF8StringEncoding];
            if (propertyName) {
                [propertyNames addObject:propertyName];
            }
        }
        objectProtocolPropertyNames = [propertyNames copy];
        free(propertyList);
    });
    
    return [objectProtocolPropertyNames containsObject:propertyName];
}

@end
