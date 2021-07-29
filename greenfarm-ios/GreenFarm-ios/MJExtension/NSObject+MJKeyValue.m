

#import "NSObject+MJKeyValue.h"
#import "NSObject+MJProperty.h"
#import "NSString+MJExtension.h"
#import "MJProperty.h"
#import "MJPropertyType.h"
#import "MJExtensionConst.h"
#import "MJFoundation.h"
#import "NSString+MJExtension.h"
#import "NSObject+MJClass.h"

@implementation NSDecimalNumber(MJKeyValue)

- (id)standardValueWithTypeCode:(NSString *)typeCode {
   
    if ([typeCode isEqualToString:MJPropertyTypeLongLong]) {
        return @(self.longLongValue);
    } else if ([typeCode isEqualToString:MJPropertyTypeLongLong.uppercaseString]) {
        return @(self.unsignedLongLongValue);
    } else if ([typeCode isEqualToString:MJPropertyTypeLong]) {
        return @(self.longValue);
    } else if ([typeCode isEqualToString:MJPropertyTypeLong.uppercaseString]) {
        return @(self.unsignedLongValue);
    } else {
        return @(self.doubleValue);
    }
}

@end

@implementation NSObject (MJKeyValue)


static const char MJErrorKey = '\0';
+ (NSError *)mj_error
{
    return objc_getAssociatedObject(self, &MJErrorKey);
}

+ (void)setMj_error:(NSError *)error
{
    objc_setAssociatedObject(self, &MJErrorKey, error, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}


static const char MJReferenceReplacedKeyWhenCreatingKeyValuesKey = '\0';

+ (void)mj_referenceReplacedKeyWhenCreatingKeyValues:(BOOL)reference
{
    objc_setAssociatedObject(self, &MJReferenceReplacedKeyWhenCreatingKeyValuesKey, @(reference), OBJC_ASSOCIATION_ASSIGN);
}

+ (BOOL)mj_isReferenceReplacedKeyWhenCreatingKeyValues
{
    __block id value = objc_getAssociatedObject(self, &MJReferenceReplacedKeyWhenCreatingKeyValuesKey);
    if (!value) {
        [self mj_enumerateAllClasses:^(__unsafe_unretained Class c, BOOL *stop) {
            value = objc_getAssociatedObject(c, &MJReferenceReplacedKeyWhenCreatingKeyValuesKey);
            
            if (value) *stop = YES;
        }];
    }
    return [value boolValue];
}


+ (void)load
{
    [self mj_referenceReplacedKeyWhenCreatingKeyValues:YES];
}


- (instancetype)mj_setKeyValues:(id)keyValues
{
    return [self mj_setKeyValues:keyValues context:nil];
}


- (instancetype)mj_setKeyValues:(id)keyValues context:(NSManagedObjectContext *)context
{
  
    keyValues = [keyValues mj_JSONObject];
    
    MJExtensionAssertError([keyValues isKindOfClass:[NSDictionary class]], self, [self class], @"keyValues参数不是一个字典");
    
    Class clazz = [self class];
    NSArray *allowedPropertyNames = [clazz mj_totalAllowedPropertyNames];
    NSArray *ignoredPropertyNames = [clazz mj_totalIgnoredPropertyNames];
    
    NSLocale *numberLocale = nil;
    if ([self.class respondsToSelector:@selector(mj_numberLocale)]) {
        numberLocale = self.class.mj_numberLocale;
    }
    

    [clazz mj_enumerateProperties:^(MJProperty *property, BOOL *stop) {
        @try {
            
            if (allowedPropertyNames.count && ![allowedPropertyNames containsObject:property.name]) return;
            if ([ignoredPropertyNames containsObject:property.name]) return;
            
            
            id value;
            NSArray *propertyKeyses = [property propertyKeysForClass:clazz];
            for (NSArray *propertyKeys in propertyKeyses) {
                value = keyValues;
                for (MJPropertyKey *propertyKey in propertyKeys) {
                    value = [propertyKey valueInObject:value];
                }
                if (value) break;
            }
            
            
            id newValue = [clazz mj_getNewValueFromObject:self oldValue:value property:property];
            if (newValue != value) {
                [property setValue:newValue forObject:self];
                return;
            }
            
            
            if (!value || value == [NSNull null]) return;
            
            
            MJPropertyType *type = property.type;
            Class propertyClass = type.typeClass;
            Class objectClass = [property objectClassInArrayForClass:[self class]];
            
            
            if (propertyClass == [NSMutableArray class] && [value isKindOfClass:[NSArray class]]) {
                value = [NSMutableArray arrayWithArray:value];
            } else if (propertyClass == [NSMutableDictionary class] && [value isKindOfClass:[NSDictionary class]]) {
                value = [NSMutableDictionary dictionaryWithDictionary:value];
            } else if (propertyClass == [NSMutableString class] && [value isKindOfClass:[NSString class]]) {
                value = [NSMutableString stringWithString:value];
            } else if (propertyClass == [NSMutableData class] && [value isKindOfClass:[NSData class]]) {
                value = [NSMutableData dataWithData:value];
            }
            
            if (!type.isFromFoundation && propertyClass) {
                value = [propertyClass mj_objectWithKeyValues:value context:context];
            } else if (objectClass) {
                if (objectClass == [NSURL class] && [value isKindOfClass:[NSArray class]]) {
                    // string array -> url array
                    NSMutableArray *urlArray = [NSMutableArray array];
                    for (NSString *string in value) {
                        if (![string isKindOfClass:[NSString class]]) continue;
                        [urlArray addObject:string.mj_url];
                    }
                    value = urlArray;
                } else {
                    value = [objectClass mj_objectArrayWithKeyValuesArray:value context:context];
                }
            } else if (propertyClass == [NSString class]) {
                if ([value isKindOfClass:[NSNumber class]]) {
                    // NSNumber -> NSString
                    value = [value description];
                } else if ([value isKindOfClass:[NSURL class]]) {
                    // NSURL -> NSString
                    value = [value absoluteString];
                }
            } else if ([value isKindOfClass:[NSString class]]) {
                if (propertyClass == [NSURL class]) {
                    // NSString -> NSURL
                    
                    value = [value mj_url];
                } else if (type.isNumberType) {
                    NSString *oldValue = value;
                    
                    // NSString -> NSDecimalNumber, 使用 DecimalNumber 来转换数字, 避免丢失精度以及溢出
                    NSDecimalNumber *decimalValue = [NSDecimalNumber decimalNumberWithString:oldValue
                                                                                      locale:numberLocale];
                    
                    
                    if (decimalValue == NSDecimalNumber.notANumber) {
                        value = @(0);
                    }else if (propertyClass != [NSDecimalNumber class]) {
                        value = [decimalValue standardValueWithTypeCode:type.code];
                    } else {
                        value = decimalValue;
                    }
                    
                   
                    if (type.isBoolType) {
                        
                        NSString *lower = [oldValue lowercaseString];
                        if ([lower isEqualToString:@"yes"] || [lower isEqualToString:@"true"]) {
                            value = @YES;
                        } else if ([lower isEqualToString:@"no"] || [lower isEqualToString:@"false"]) {
                            value = @NO;
                        }
                    }
                }
            } else if ([value isKindOfClass:[NSNumber class]] && propertyClass == [NSDecimalNumber class]){
                if (![value isKindOfClass:[NSDecimalNumber class]]) {
                    value = [NSDecimalNumber decimalNumberWithDecimal:[((NSNumber *)value) decimalValue]];
                }
            }
            
            
            if (propertyClass && ![value isKindOfClass:propertyClass]) {
                value = nil;
            }
            
            
            [property setValue:value forObject:self];
        } @catch (NSException *exception) {
            MJExtensionBuildError([self class], exception.reason);
            MJExtensionLog(@"%@", exception);
        }
    }];
    
    
    if ([self respondsToSelector:@selector(mj_didConvertToObjectWithKeyValues:)]) {
        [self mj_didConvertToObjectWithKeyValues:keyValues];
    }
#pragma clang diagnostic push
#pragma clang diagnostic ignored"-Wdeprecated-declarations"
    if ([self respondsToSelector:@selector(mj_keyValuesDidFinishConvertingToObject)]) {
        [self mj_keyValuesDidFinishConvertingToObject];
    }
    if ([self respondsToSelector:@selector(mj_keyValuesDidFinishConvertingToObject:)]) {
        [self mj_keyValuesDidFinishConvertingToObject:keyValues];
    }
#pragma clang diagnostic pop
    return self;
}

+ (instancetype)mj_objectWithKeyValues:(id)keyValues
{
    return [self mj_objectWithKeyValues:keyValues context:nil];
}

+ (instancetype)mj_objectWithKeyValues:(id)keyValues context:(NSManagedObjectContext *)context
{
    
    keyValues = [keyValues mj_JSONObject];
    MJExtensionAssertError([keyValues isKindOfClass:[NSDictionary class]], nil, [self class], @"keyValues参数不是一个字典");
    
    if ([self isSubclassOfClass:[NSManagedObject class]] && context) {
        NSString *entityName = [NSStringFromClass(self) componentsSeparatedByString:@"."].lastObject;
        return [[NSEntityDescription insertNewObjectForEntityForName:entityName inManagedObjectContext:context] mj_setKeyValues:keyValues context:context];
    }
    return [[[self alloc] init] mj_setKeyValues:keyValues];
}

+ (instancetype)mj_objectWithFilename:(NSString *)filename
{
    MJExtensionAssertError(filename != nil, nil, [self class], @"filename参数为nil");
    
    return [self mj_objectWithFile:[[NSBundle mainBundle] pathForResource:filename ofType:nil]];
}

+ (instancetype)mj_objectWithFile:(NSString *)file
{
    MJExtensionAssertError(file != nil, nil, [self class], @"file参数为nil");
    
    return [self mj_objectWithKeyValues:[NSDictionary dictionaryWithContentsOfFile:file]];
}


+ (NSMutableArray *)mj_objectArrayWithKeyValuesArray:(NSArray *)keyValuesArray
{
    return [self mj_objectArrayWithKeyValuesArray:keyValuesArray context:nil];
}

+ (NSMutableArray *)mj_objectArrayWithKeyValuesArray:(id)keyValuesArray context:(NSManagedObjectContext *)context
{
    
    keyValuesArray = [keyValuesArray mj_JSONObject];
    
    
    MJExtensionAssertError([keyValuesArray isKindOfClass:[NSArray class]], nil, [self class], @"keyValuesArray参数不是一个数组");
    
    
    if ([MJFoundation isClassFromFoundation:self]) return [NSMutableArray arrayWithArray:keyValuesArray];
    

    
    NSMutableArray *modelArray = [NSMutableArray array];
    
    
    for (NSDictionary *keyValues in keyValuesArray) {
        if ([keyValues isKindOfClass:[NSArray class]]){
            [modelArray addObject:[self mj_objectArrayWithKeyValuesArray:keyValues context:context]];
        } else {
            id model = [self mj_objectWithKeyValues:keyValues context:context];
            if (model) [modelArray addObject:model];
        }
    }
    
    return modelArray;
}

+ (NSMutableArray *)mj_objectArrayWithFilename:(NSString *)filename
{
    MJExtensionAssertError(filename != nil, nil, [self class], @"filename参数为nil");
    
    return [self mj_objectArrayWithFile:[[NSBundle mainBundle] pathForResource:filename ofType:nil]];
}

+ (NSMutableArray *)mj_objectArrayWithFile:(NSString *)file
{
    MJExtensionAssertError(file != nil, nil, [self class], @"file参数为nil");
    
    return [self mj_objectArrayWithKeyValuesArray:[NSArray arrayWithContentsOfFile:file]];
}


- (NSMutableDictionary *)mj_keyValues
{
    return [self mj_keyValuesWithKeys:nil ignoredKeys:nil];
}

- (NSMutableDictionary *)mj_keyValuesWithKeys:(NSArray *)keys
{
    return [self mj_keyValuesWithKeys:keys ignoredKeys:nil];
}

- (NSMutableDictionary *)mj_keyValuesWithIgnoredKeys:(NSArray *)ignoredKeys
{
    return [self mj_keyValuesWithKeys:nil ignoredKeys:ignoredKeys];
}

- (NSMutableDictionary *)mj_keyValuesWithKeys:(NSArray *)keys ignoredKeys:(NSArray *)ignoredKeys
{
   
    if ([self isMemberOfClass:NSNull.class]) { return nil; }
    
    MJExtensionAssertError(![MJFoundation isClassFromFoundation:[self class]], (NSMutableDictionary *)self, [self class], @"不是自定义的模型类")
    
    id keyValues = [NSMutableDictionary dictionary];
    
    Class clazz = [self class];
    NSArray *allowedPropertyNames = [clazz mj_totalAllowedPropertyNames];
    NSArray *ignoredPropertyNames = [clazz mj_totalIgnoredPropertyNames];
    
    [clazz mj_enumerateProperties:^(MJProperty *property, BOOL *stop) {
        @try {
            
            if (allowedPropertyNames.count && ![allowedPropertyNames containsObject:property.name]) return;
            if ([ignoredPropertyNames containsObject:property.name]) return;
            if (keys.count && ![keys containsObject:property.name]) return;
            if ([ignoredKeys containsObject:property.name]) return;
            
            
            id value = [property valueForObject:self];
            if (!value) return;
            
            
            MJPropertyType *type = property.type;
            Class propertyClass = type.typeClass;
            if (!type.isFromFoundation && propertyClass) {
                value = [value mj_keyValues];
            } else if ([value isKindOfClass:[NSArray class]]) {
                
                value = [NSObject mj_keyValuesArrayWithObjectArray:value];
            } else if (propertyClass == [NSURL class]) {
                value = [value absoluteString];
            }
            
            
            if ([clazz mj_isReferenceReplacedKeyWhenCreatingKeyValues]) {
                NSArray *propertyKeys = [[property propertyKeysForClass:clazz] firstObject];
                NSUInteger keyCount = propertyKeys.count;
                
                __block id innerContainer = keyValues;
                [propertyKeys enumerateObjectsUsingBlock:^(MJPropertyKey *propertyKey, NSUInteger idx, BOOL *stop) {
                    
                    MJPropertyKey *nextPropertyKey = nil;
                    if (idx != keyCount - 1) {
                        nextPropertyKey = propertyKeys[idx + 1];
                    }
                    
                    if (nextPropertyKey) {
                        
                        id tempInnerContainer = [propertyKey valueInObject:innerContainer];
                        if (tempInnerContainer == nil || [tempInnerContainer isKindOfClass:[NSNull class]]) {
                            if (nextPropertyKey.type == MJPropertyKeyTypeDictionary) {
                                tempInnerContainer = [NSMutableDictionary dictionary];
                            } else {
                                tempInnerContainer = [NSMutableArray array];
                            }
                            if (propertyKey.type == MJPropertyKeyTypeDictionary) {
                                innerContainer[propertyKey.name] = tempInnerContainer;
                            } else {
                                innerContainer[propertyKey.name.intValue] = tempInnerContainer;
                            }
                        }
                        
                        if ([tempInnerContainer isKindOfClass:[NSMutableArray class]]) {
                            NSMutableArray *tempInnerContainerArray = tempInnerContainer;
                            int index = nextPropertyKey.name.intValue;
                            while (tempInnerContainerArray.count < index + 1) {
                                [tempInnerContainerArray addObject:[NSNull null]];
                            }
                        }
                        
                        innerContainer = tempInnerContainer;
                    } else {
                        if (propertyKey.type == MJPropertyKeyTypeDictionary) {
                            innerContainer[propertyKey.name] = value;
                        } else {
                            innerContainer[propertyKey.name.intValue] = value;
                        }
                    }
                }];
            } else {
                keyValues[property.name] = value;
            }
        } @catch (NSException *exception) {
            MJExtensionBuildError([self class], exception.reason);
            MJExtensionLog(@"%@", exception);
        }
    }];
    
    
    if ([self respondsToSelector:@selector(mj_objectDidConvertToKeyValues:)]) {
        [self mj_objectDidConvertToKeyValues:keyValues];
    }
#pragma clang diagnostic push
#pragma clang diagnostic ignored"-Wdeprecated-declarations"
    if ([self respondsToSelector:@selector(mj_objectDidFinishConvertingToKeyValues)]) {
        [self mj_objectDidFinishConvertingToKeyValues];
    }
#pragma clang diagnostic pop
    
    return keyValues;
}

+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray
{
    return [self mj_keyValuesArrayWithObjectArray:objectArray keys:nil ignoredKeys:nil];
}

+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray keys:(NSArray *)keys
{
    return [self mj_keyValuesArrayWithObjectArray:objectArray keys:keys ignoredKeys:nil];
}

+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray ignoredKeys:(NSArray *)ignoredKeys
{
    return [self mj_keyValuesArrayWithObjectArray:objectArray keys:nil ignoredKeys:ignoredKeys];
}

+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray keys:(NSArray *)keys ignoredKeys:(NSArray *)ignoredKeys
{
    
    MJExtensionAssertError([objectArray isKindOfClass:[NSArray class]], nil, [self class], @"objectArray参数不是一个数组");
    
    NSMutableArray *keyValuesArray = [NSMutableArray array];
    for (id object in objectArray) {
        if (keys) {
            id convertedObj = [object mj_keyValuesWithKeys:keys];
            if (!convertedObj) { continue; }
            [keyValuesArray addObject:convertedObj];
        } else {
            id convertedObj = [object mj_keyValuesWithIgnoredKeys:ignoredKeys];
            if (!convertedObj) { continue; }
            [keyValuesArray addObject:convertedObj];
        }
    }
    return keyValuesArray;
}


- (NSData *)mj_JSONData
{
    if ([self isKindOfClass:[NSString class]]) {
        return [((NSString *)self) dataUsingEncoding:NSUTF8StringEncoding];
    } else if ([self isKindOfClass:[NSData class]]) {
        return (NSData *)self;
    }
    
    return [NSJSONSerialization dataWithJSONObject:[self mj_JSONObject] options:kNilOptions error:nil];
}

- (id)mj_JSONObject
{
    if ([self isKindOfClass:[NSString class]]) {
        return [NSJSONSerialization JSONObjectWithData:[((NSString *)self) dataUsingEncoding:NSUTF8StringEncoding] options:kNilOptions error:nil];
    } else if ([self isKindOfClass:[NSData class]]) {
        return [NSJSONSerialization JSONObjectWithData:(NSData *)self options:kNilOptions error:nil];
    }
    
    return self.mj_keyValues;
}

- (NSString *)mj_JSONString
{
    if ([self isKindOfClass:[NSString class]]) {
        return (NSString *)self;
    } else if ([self isKindOfClass:[NSData class]]) {
        return [[NSString alloc] initWithData:(NSData *)self encoding:NSUTF8StringEncoding];
    }
    
    return [[NSString alloc] initWithData:[self mj_JSONData] encoding:NSUTF8StringEncoding];
}

@end
