

#import <Foundation/Foundation.h>

typedef enum {
    MJPropertyKeyTypeDictionary = 0,
    MJPropertyKeyTypeArray
} MJPropertyKeyType;


@interface MJPropertyKey : NSObject

@property (copy,   nonatomic) NSString *name;

@property (assign, nonatomic) MJPropertyKeyType type;


- (id)valueInObject:(id)object;

@end
