

#import <Foundation/Foundation.h>
#import <objc/runtime.h>
#import "MJPropertyType.h"
#import "MJPropertyKey.h"


@interface MJProperty : NSObject

@property (nonatomic, assign) objc_property_t property;

@property (nonatomic, readonly) NSString *name;

@property (nonatomic, readonly) MJPropertyType *type;

@property (nonatomic, assign) Class srcClass;


- (void)setOriginKey:(id)originKey forClass:(Class)c;
- (NSArray *)propertyKeysForClass:(Class)c;


- (void)setObjectClassInArray:(Class)objectClass forClass:(Class)c;
- (Class)objectClassInArrayForClass:(Class)c;

- (void)setValue:(id)value forObject:(id)object;

- (id)valueForObject:(id)object;

+ (instancetype)cachedPropertyWithProperty:(objc_property_t)property;

@end
