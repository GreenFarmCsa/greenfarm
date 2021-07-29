

#import "UMJsApiHandler.h"

@interface UMJsApiHandler()
@end
@implementation UMJsApiHandler

-(void)registerJsapiWithName:(NSString *)name context:(UMContext *)context;{
    
    [context.bridge registerHandler:name handler:^(id data, UMJBResponseCallback responseCallback) {
//        NSLog(@"testObjcCallback called: %@", data);
//            responseCallback(@"Response from testObjcCallback12323");
        [self handler:data context:context callback:responseCallback];
    }];
}
-(void)handler:(NSDictionary *)data context:(UMContext *)context callback:(UMJBResponseCallback)callback{
   
}
@end
