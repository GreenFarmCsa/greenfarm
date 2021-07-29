

#import "UMKernel.h"
#import <UIKit/UIKit.h>
@class UMContentView;
@class UMViewParam;
NS_ASSUME_NONNULL_BEGIN

@interface UMView : UMKernel

@property (nonatomic ,strong) UMViewParam *createParam;
@property (nonatomic ,strong) UMContentView *contentView;

+ (instancetype)viewWithViewParam:(UMViewParam *)viewParam parentObject:(UMKernel *)parentObject;

@end

NS_ASSUME_NONNULL_END

@interface UMViewParam : NSObject

@property(nonatomic, strong) Class          contentViewCls;
@property(nonatomic, assign) CGRect         frame;
@property(nonatomic, copy) NSString         *url;
@property(nonatomic, strong) NSDictionary   *expandParams;

@end
