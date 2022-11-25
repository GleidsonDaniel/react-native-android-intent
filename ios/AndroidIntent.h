
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNAndroidIntentSpec.h"

@interface AndroidIntent : NSObject <NativeAndroidIntentSpec>
#else
#import <React/RCTBridgeModule.h>

@interface AndroidIntent : NSObject <RCTBridgeModule>
#endif

@end
