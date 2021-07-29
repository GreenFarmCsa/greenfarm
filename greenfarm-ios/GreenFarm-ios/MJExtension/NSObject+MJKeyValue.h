

#import <Foundation/Foundation.h>
#import "MJExtensionConst.h"
#import <CoreData/CoreData.h>
#import "MJProperty.h"


@protocol MJKeyValue <NSObject>
@optional

+ (NSArray *)mj_allowedPropertyNames;


+ (NSArray *)mj_ignoredPropertyNames;


+ (NSDictionary *)mj_replacedKeyFromPropertyName;


+ (id)mj_replacedKeyFromPropertyName121:(NSString *)propertyName;


+ (NSDictionary *)mj_objectClassInArray;



+ (NSLocale *)mj_numberLocale;


- (id)mj_newValueFromOldValue:(id)oldValue property:(MJProperty *)property;


- (void)mj_keyValuesDidFinishConvertingToObject MJExtensionDeprecated("请使用`mj_didConvertToObjectWithKeyValues:`替代");
- (void)mj_keyValuesDidFinishConvertingToObject:(NSDictionary *)keyValues MJExtensionDeprecated("请使用`mj_didConvertToObjectWithKeyValues:`替代");
- (void)mj_didConvertToObjectWithKeyValues:(NSDictionary *)keyValues;

- (void)mj_objectDidFinishConvertingToKeyValues MJExtensionDeprecated("请使用`mj_objectDidConvertToKeyValues:`替代");
- (void)mj_objectDidConvertToKeyValues:(NSMutableDictionary *)keyValues;

@end

@interface NSObject (MJKeyValue) <MJKeyValue>

+ (NSError *)mj_error;


+ (void)mj_referenceReplacedKeyWhenCreatingKeyValues:(BOOL)reference;


- (instancetype)mj_setKeyValues:(id)keyValues;


- (instancetype)mj_setKeyValues:(id)keyValues context:(NSManagedObjectContext *)context;


- (NSMutableDictionary *)mj_keyValues;
- (NSMutableDictionary *)mj_keyValuesWithKeys:(NSArray *)keys;
- (NSMutableDictionary *)mj_keyValuesWithIgnoredKeys:(NSArray *)ignoredKeys;


+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray;
+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray keys:(NSArray *)keys;
+ (NSMutableArray *)mj_keyValuesArrayWithObjectArray:(NSArray *)objectArray ignoredKeys:(NSArray *)ignoredKeys;


+ (instancetype)mj_objectWithKeyValues:(id)keyValues;


+ (instancetype)mj_objectWithKeyValues:(id)keyValues context:(NSManagedObjectContext *)context;

+ (instancetype)mj_objectWithFilename:(NSString *)filename;


+ (instancetype)mj_objectWithFile:(NSString *)file;


+ (NSMutableArray *)mj_objectArrayWithKeyValuesArray:(id)keyValuesArray;


+ (NSMutableArray *)mj_objectArrayWithKeyValuesArray:(id)keyValuesArray context:(NSManagedObjectContext *)context;


+ (NSMutableArray *)mj_objectArrayWithFilename:(NSString *)filename;


+ (NSMutableArray *)mj_objectArrayWithFile:(NSString *)file;


- (NSData *)mj_JSONData;

- (id)mj_JSONObject;

- (NSString *)mj_JSONString;
@end
