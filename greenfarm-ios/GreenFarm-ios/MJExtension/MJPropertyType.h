

#import <Foundation/Foundation.h>


@interface MJPropertyType : NSObject

@property (nonatomic, copy) NSString *code;


@property (nonatomic, readonly, getter=isIdType) BOOL idType;


@property (nonatomic, readonly, getter=isNumberType) BOOL numberType;


@property (nonatomic, readonly, getter=isBoolType) BOOL boolType;


@property (nonatomic, readonly) Class typeClass;


@property (nonatomic, readonly, getter = isFromFoundation) BOOL fromFoundation;

@property (nonatomic, readonly, getter = isKVCDisabled) BOOL KVCDisabled;


+ (instancetype)cachedTypeWithCode:(NSString *)code;
@end
