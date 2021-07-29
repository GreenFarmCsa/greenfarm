

#import "MJProperty.h"
#import "MJFoundation.h"
#import "MJExtensionConst.h"
#import <objc/message.h>
#include "TargetConditionals.h"

@interface MJProperty()
@property (strong, nonatomic) NSMutableDictionary *propertyKeysDict;
@property (strong, nonatomic) NSMutableDictionary *objectClassInArrayDict;
@property (strong, nonatomic) dispatch_semaphore_t propertyKeysLock;
@property (strong, nonatomic) dispatch_semaphore_t objectClassInArrayLock;
@end

@implementation MJProperty


- (instancetype)init
{
    if (self = [super init]) {
        _propertyKeysDict = [NSMutableDictionary dictionary];
        _objectClassInArrayDict = [NSMutableDictionary dictionary];
        _propertyKeysLock = dispatch_semaphore_create(1);
        _objectClassInArrayLock = dispatch_semaphore_create(1);
    }
    return self;
}


+ (instancetype)cachedPropertyWithProperty:(objc_property_t)property
{
    MJProperty *propertyObj = objc_getAssociatedObject(self, property);
    if (propertyObj == nil) {
        propertyObj = [[self alloc] init];
        propertyObj.property = property;
        objc_setAssociatedObject(self, property, propertyObj, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
    return propertyObj;
}


- (void)setProperty:(objc_property_t)property
{
    _property = property;
    
    MJExtensionAssertParamNotNil(property);
    
    
    _name = @(property_getName(property));
    
    
    NSString *attrs = @(property_getAttributes(property));
    NSUInteger dotLoc = [attrs rangeOfString:@","].location;
    NSString *code = nil;
    NSUInteger loc = 1;
    if (dotLoc == NSNotFound) {
        code = [attrs substringFromIndex:loc];
    } else {
        code = [attrs substringWithRange:NSMakeRange(loc, dotLoc - loc)];
    }
    _type = [MJPropertyType cachedTypeWithCode:code];
}


- (id)valueForObject:(id)object
{
    if (self.type.KVCDisabled) return [NSNull null];
    
    id value = [object valueForKey:self.name];
    
   
#if defined(__arm__) || (TARGET_OS_SIMULATOR && !__LP64__)
    if (self.type.isBoolType) {
        value = @([(NSNumber *)value boolValue]);
    }
#endif
    
    return value;
}


- (void)setValue:(id)value forObject:(id)object
{
    if (self.type.KVCDisabled || value == nil) return;
    [object setValue:value forKey:self.name];
}


- (NSArray *)propertyKeysWithStringKey:(NSString *)stringKey
{
    if (stringKey.length == 0) return nil;
    
    NSMutableArray *propertyKeys = [NSMutableArray array];
    NSArray *oldKeys = [stringKey componentsSeparatedByString:@"."];
    
    for (NSString *oldKey in oldKeys) {
        NSUInteger start = [oldKey rangeOfString:@"["].location;
        if (start != NSNotFound) {
            NSString *prefixKey = [oldKey substringToIndex:start];
            NSString *indexKey = prefixKey;
            if (prefixKey.length) {
                MJPropertyKey *propertyKey = [[MJPropertyKey alloc] init];
                propertyKey.name = prefixKey;
                [propertyKeys addObject:propertyKey];
                
                indexKey = [oldKey stringByReplacingOccurrencesOfString:prefixKey withString:@""];
            }
            
           
            NSArray *cmps = [[indexKey stringByReplacingOccurrencesOfString:@"[" withString:@""] componentsSeparatedByString:@"]"];
            for (NSInteger i = 0; i<cmps.count - 1; i++) {
                MJPropertyKey *subPropertyKey = [[MJPropertyKey alloc] init];
                subPropertyKey.type = MJPropertyKeyTypeArray;
                subPropertyKey.name = cmps[i];
                [propertyKeys addObject:subPropertyKey];
            }
        } else {
            MJPropertyKey *propertyKey = [[MJPropertyKey alloc] init];
            propertyKey.name = oldKey;
            [propertyKeys addObject:propertyKey];
        }
    }
    
    return propertyKeys;
}


- (void)setOriginKey:(id)originKey forClass:(Class)c
{
    if ([originKey isKindOfClass:[NSString class]]) {
        NSArray *propertyKeys = [self propertyKeysWithStringKey:originKey];
        if (propertyKeys.count) {
            [self setPorpertyKeys:@[propertyKeys] forClass:c];
        }
    } else if ([originKey isKindOfClass:[NSArray class]]) {
        NSMutableArray *keyses = [NSMutableArray array];
        for (NSString *stringKey in originKey) {
            NSArray *propertyKeys = [self propertyKeysWithStringKey:stringKey];
            if (propertyKeys.count) {
                [keyses addObject:propertyKeys];
            }
        }
        if (keyses.count) {
            [self setPorpertyKeys:keyses forClass:c];
        }
    }
}


- (void)setPorpertyKeys:(NSArray *)propertyKeys forClass:(Class)c
{
    if (propertyKeys.count == 0) return;
    NSString *key = NSStringFromClass(c);
    if (!key) return;
    
    MJ_LOCK(self.propertyKeysLock);
    self.propertyKeysDict[key] = propertyKeys;
    MJ_UNLOCK(self.propertyKeysLock);
}

- (NSArray *)propertyKeysForClass:(Class)c
{
    NSString *key = NSStringFromClass(c);
    if (!key) return nil;
    
    MJ_LOCK(self.propertyKeysLock);
    NSArray *propertyKeys = self.propertyKeysDict[key];
    MJ_UNLOCK(self.propertyKeysLock);
    return propertyKeys;
}


- (void)setObjectClassInArray:(Class)objectClass forClass:(Class)c
{
    if (!objectClass) return;
    NSString *key = NSStringFromClass(c);
    if (!key) return;
    
    MJ_LOCK(self.objectClassInArrayLock);
    self.objectClassInArrayDict[key] = objectClass;
    MJ_UNLOCK(self.objectClassInArrayLock);
}

- (Class)objectClassInArrayForClass:(Class)c
{
    NSString *key = NSStringFromClass(c);
    if (!key) return nil;
    
    MJ_LOCK(self.objectClassInArrayLock);
    Class objectClass = self.objectClassInArrayDict[key];
    MJ_UNLOCK(self.objectClassInArrayLock);
    return objectClass;
}
@end
