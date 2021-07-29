

#import "MJPropertyKey.h"

@implementation MJPropertyKey

- (id)valueInObject:(id)object
{
    if ([object isKindOfClass:[NSDictionary class]] && self.type == MJPropertyKeyTypeDictionary) {
        return object[self.name];
    } else if ([object isKindOfClass:[NSArray class]] && self.type == MJPropertyKeyTypeArray) {
        NSArray *array = object;
        NSUInteger index = self.name.intValue;
        if (index < array.count) return array[index];
        return nil;
    }
    return nil;
}
@end
