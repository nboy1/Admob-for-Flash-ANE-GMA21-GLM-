//
//
//  
//
//  Created by nboy1 "H Souidi" on 6/3/2023.
//
#import "admob.h"
@import GoogleMobileAds;
@import GoogleMobileAds;
@import UIKit;
#import "FlashRuntimeExtensions.h"
#include <UserMessagingPlatform/UserMessagingPlatform.h>

FREContext mContext;
GADInterstitialAd *interstitial;
UIView *mUIView;
UILabel *label;
GADBannerView *BannerView;
GADRequest *request;
UIViewController* viewController;
GADRewardedInterstitialAd *rewardedInterstitialAd;
GADAppOpenAd *appOpenAd;
NSString *rewadmsg;
NSString *bannerIds;
NSString *positions;
NSString* rewadmsg2;
NSString *rewardedID_local;
NSString* testDevideID=nil;
bool RewardedBool=true;
bool tagForUnderAgeOfConsent_bool =true;
bool tag_for_child_directed_treatment_bool=true;
NSString *tag_for_max_ad_content_rating_str=nil;
 

@interface AdMobRewardedVideoDelegate : NSObject <GADFullScreenContentDelegate>
@property (nonatomic, assign) FREContext context;

@end

void Debug_Geography_EEA(FREContext ctx){
    
    // Create a UMPRequestParameters object.
    UMPRequestParameters *parameters = [[UMPRequestParameters alloc] init];
    // Set tag for under age of consent. Here NO means users are not under age.
    parameters.tagForUnderAgeOfConsent = NO;

    // Request an update to the consent information.
    [UMPConsentInformation.sharedInstance
        requestConsentInfoUpdateWithParameters:parameters
                             completionHandler:^(NSError *_Nullable error) {
                               if (error) {
                                 // Handle the error.
                               } else {
                                 // You are now ready to see if a form is available.
                              UMPFormStatus formStatus =
                              UMPConsentInformation.sharedInstance
                              .formStatus;
                              if (formStatus == UMPFormStatusAvailable) {
                                  [UMPConsentForm loadWithCompletionHandler:^(UMPConsentForm *form,
                                                                                NSError *loadError) {
                                      if (loadError) {
                                        // Handle the error.
                                      } else {
                                        // Present the form. You can also hold on to the reference to present
                                        // later.
                                        if (UMPConsentInformation.sharedInstance.consentStatus ==
                                            UMPConsentStatusRequired) {
                                          [form
                                              presentFromViewController:viewController
                                                      completionHandler:^(NSError *_Nullable dismissError) {
                                                        if (UMPConsentInformation.sharedInstance.consentStatus ==
                                                            UMPConsentStatusObtained) {
                                                          // App can start requesting ads.
                                                        }
                                                          // Handle dismissal by reloading form.
                                                         // [ loadForm];
                                                      }];
                                        } else {
                                          // Keep the form available for changes to user consent.
                                        }
                                      }
                                    }];

                                                                }

                               }
                             }];

}

FREObject FREDebug_Geography_EEA(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]){
    
    Debug_Geography_EEA(ctx);
    return NULL;
}




GADRequest* requestGADMobileAdsParams(void){
   
    
    if (testDevideID!=nil) {
        GADMobileAds.sharedInstance.requestConfiguration.testDeviceIdentifiers =
        @[ testDevideID ];
    }else{
       //testDevideID empty
    }
    if(tagForUnderAgeOfConsent_bool==false) {
        [GADMobileAds.sharedInstance.requestConfiguration tagForUnderAgeOfConsent:NO];
        
        Debug_Geography_EEA( mContext);

    }else{
        [GADMobileAds.sharedInstance.requestConfiguration tagForUnderAgeOfConsent:YES];

    }
    if(tag_for_child_directed_treatment_bool==false) {
        [GADMobileAds.sharedInstance.requestConfiguration tagForChildDirectedTreatment:NO];

        Debug_Geography_EEA( mContext);

    }else{
        [GADMobileAds.sharedInstance.requestConfiguration tagForChildDirectedTreatment:YES];

    }

    if([tag_for_max_ad_content_rating_str isEqualToString:@"G"]) {
        GADMobileAds.sharedInstance.requestConfiguration.maxAdContentRating =
            GADMaxAdContentRatingGeneral;
    }
    if([tag_for_max_ad_content_rating_str isEqualToString:@"PG"]) {
        GADMobileAds.sharedInstance.requestConfiguration.maxAdContentRating =
            GADMaxAdContentRatingParentalGuidance;
    }
    if([tag_for_max_ad_content_rating_str isEqualToString:@"T"]) {
        GADMobileAds.sharedInstance.requestConfiguration.maxAdContentRating =
            GADMaxAdContentRatingTeen;
    }
    if([tag_for_max_ad_content_rating_str isEqualToString:@"MA"]) {
        GADMobileAds.sharedInstance.requestConfiguration.maxAdContentRating =
            GADMaxAdContentRatingMatureAudience;
    }
    
    request = [GADRequest request];

    return request;
}


void showInterstitial (void){
    UIWindow* win =   [[UIApplication sharedApplication] keyWindow];
         UIViewController* vc = [win rootViewController];
    
    if (interstitial) {
        [interstitial presentFromRootViewController:vc];
      } else {
        NSLog(@"Ad wasn't ready");
      }

  
}

 
void loadBannerAd (NSString *bannerId, NSString *position){

    
    UIWindow* win =   [[UIApplication sharedApplication] keyWindow];
    viewController = [win rootViewController];
    
    CGFloat gabs= GADAdSizeBanner.size.height;
  
    CGFloat viewHeight = viewController.view.frame.size.height;
    CGFloat total =gabs-viewHeight;

    
    
    
    if([position isEqualToString:@"TOP"]||[position isEqualToString:@"top"]){
        
        
        //****        top banner        ****//
        // mUIView
        
        mUIView = [[UIView alloc] initWithFrame:CGRectZero];
        mUIView.translatesAutoresizingMaskIntoConstraints = false;
        mUIView.backgroundColor=[UIColor blackColor];
        [viewController.view addSubview: mUIView];
        [mUIView.widthAnchor constraintEqualToConstant:viewController.view.frame.size.width].active = YES;
        [mUIView.heightAnchor constraintEqualToConstant:viewController.view.frame.size.height].active = YES;
        [mUIView.centerXAnchor constraintEqualToAnchor:viewController.view.centerXAnchor].active = YES;
        
        [mUIView.bottomAnchor constraintEqualToAnchor:viewController.view.bottomAnchor constant:total].active = YES;
        [mUIView.topAnchor constraintEqualToAnchor:viewController.view.topAnchor constant:0].active = YES;
        
        
        
        
        //Label
        label = [[UILabel alloc] initWithFrame:CGRectZero];
        label.translatesAutoresizingMaskIntoConstraints = false;
        label.text=@"Banner Ads";
        label.textColor=[UIColor grayColor];
        [label setFont: [label.font fontWithSize: 20]];
        [mUIView addSubview: label];
        [label.centerXAnchor constraintEqualToAnchor:mUIView.centerXAnchor].active = YES;
        [label.bottomAnchor constraintEqualToAnchor:viewController.view.bottomAnchor constant:total].active = YES;
        [label.topAnchor constraintEqualToAnchor:viewController.view.topAnchor constant:0].active = YES;
        
        
        
        // BannerView
        BannerView=[[GADBannerView alloc] initWithAdSize:GADAdSizeBanner];
        BannerView.center = CGPointMake(viewController.view.center.x,gabs/2);
        
        [mUIView addSubview:BannerView];
       

        //****        end top banner        ****//
    }
    
    
    else {
        
        
        //****        bottom banner        ****//
            
            // mUIView
            mUIView = [[UIView alloc] initWithFrame:CGRectZero];
            mUIView.translatesAutoresizingMaskIntoConstraints = false;
            mUIView.backgroundColor=[UIColor blackColor];
            [viewController.view addSubview: mUIView];
            [mUIView.widthAnchor constraintEqualToConstant:viewController.view.frame.size.width].active = YES;
            [mUIView.heightAnchor constraintEqualToConstant:viewController.view.frame.size.height].active = YES;
            [mUIView.centerXAnchor constraintEqualToAnchor:viewController.view.centerXAnchor].active = YES;
            [mUIView.topAnchor constraintEqualToAnchor:viewController.view.topAnchor constant:total*-1].active = YES;
            
            
            
            //Label
            label = [[UILabel alloc] initWithFrame:CGRectZero];
            label.translatesAutoresizingMaskIntoConstraints = false;
            label.text=@"Banner Ads";
            label.textColor=[UIColor grayColor];
            [label setFont: [label.font fontWithSize: 20]];
            [mUIView addSubview: label];
            [label.centerXAnchor constraintEqualToAnchor:mUIView.centerXAnchor].active = YES;
            [label.topAnchor constraintEqualToAnchor:viewController.view.topAnchor constant:total*-1+10].active = YES;
            
          
            // BannerView
            BannerView=[[GADBannerView alloc] initWithAdSize:GADAdSizeBanner];
            // BannerView.translatesAutoresizingMaskIntoConstraints = false;
            BannerView.center= CGPointMake(viewController.view.center.x,gabs/2);
            [mUIView addSubview:BannerView];
            
            
            //****       end bottom banner        ****//
            
        
        
        
       
    }
    
   
    // create banner
    BannerView.adUnitID = bannerId;
    BannerView.rootViewController = viewController;
    BannerView.delegate=(id<GADBannerViewDelegate>)viewController;
    request = requestGADMobileAdsParams();
     

    [BannerView loadRequest:request];
     
    
    
}




void message (NSString *messages){

    UIWindow* win =   [[UIApplication sharedApplication] keyWindow];
    viewController = [win rootViewController];
    
    CGFloat gabs= GADAdSizeBanner.size.height;
  
    CGFloat viewHeight = viewController.view.frame.size.height;
    CGFloat total =gabs-viewHeight;

    
    
  
        
        mUIView = [[UIView alloc] initWithFrame:CGRectZero];
        mUIView.translatesAutoresizingMaskIntoConstraints = false;
        mUIView.backgroundColor=[UIColor whiteColor];
        [viewController.view addSubview: mUIView];
        [mUIView.widthAnchor constraintEqualToConstant:viewController.view.frame.size.width].active = YES;
        [mUIView.heightAnchor constraintEqualToConstant:viewController.view.frame.size.height].active = YES;
        [mUIView.centerXAnchor constraintEqualToAnchor:viewController.view.centerXAnchor].active = YES;
        
        [mUIView.bottomAnchor constraintEqualToAnchor:viewController.view.bottomAnchor constant:total].active = YES;
        [mUIView.topAnchor constraintEqualToAnchor:viewController.view.topAnchor constant:0].active = YES;
        
        
        
        
        //Label
        label = [[UILabel alloc] initWithFrame:CGRectZero];
        label.translatesAutoresizingMaskIntoConstraints = false;
        label.text=messages;
        label.textColor=[UIColor blueColor];
        [label setFont: [label.font fontWithSize: 20]];
        [mUIView addSubview: label];
        [label.centerXAnchor constraintEqualToAnchor:mUIView.centerXAnchor].active = YES;
        [label.bottomAnchor constraintEqualToAnchor:viewController.view.bottomAnchor constant:total].active = YES;
        [label.topAnchor constraintEqualToAnchor:viewController.view.topAnchor constant:0].active = YES;
        
    }








@implementation AdMobRewardedVideoDelegate
#pragma mark GADFullScreenContentDelegate implementation

- (void)adWillPresentFullScreenContent:(nonnull id<GADFullScreenPresentingAd>)ad {
  NSLog(@"Rewarded ad will be presented.");
    
    FREDispatchStatusEventAsync(mContext, (uint8_t*)[@"onAdShowedFullScreenContent" UTF8String], (uint8_t*)[@"events" UTF8String]);
}

/// Tells the delegate that the rewarded ad failed to present.
- (void)ad:(nonnull id<GADFullScreenPresentingAd>)ad
    didFailToPresentFullScreenContentWithError:(nonnull NSError *)error {
  NSLog(@"Rewarded ad failed to present with error: %@", [error localizedDescription]);
    
    FREDispatchStatusEventAsync(mContext, (uint8_t*)[@"onAdFailedToShowFullScreenContent" UTF8String], (uint8_t*)[@"events" UTF8String]);
}

/// Tells the delegate that the ad dismissed full screen content.
- (void)adDidDismissFullScreenContent:(nonnull id<GADFullScreenPresentingAd>)ad {
    NSLog(@"Ad did dismiss full screen content.");
   
    FREDispatchStatusEventAsync(mContext, (uint8_t*)[@"onAdDismissedFullScreenContent" UTF8String], (uint8_t*)[@"events" UTF8String]);
}

@end






void loadInterstitial(FREContext context, NSString *interstitialId){
   

      [GADInterstitialAd loadWithAdUnitID:interstitialId
                                  request:requestGADMobileAdsParams()
                        completionHandler:^(GADInterstitialAd *ad, NSError *error) {
        if (error) {
          NSLog(@"Failed to load interstitial ad with error: %@", [error localizedDescription]);
          return;
        }
        interstitial = ad;
      }];

  
}
FREObject FRELoadInterstitial(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
   
    
    uint32_t length = 0;
    const uint8_t *value = NULL;
    NSString *interstitialId;
    
    // Convert AS parameter to Native Parameter
    FREGetObjectAsUTF8( argv[0], &length, &value );
    interstitialId = [NSString stringWithUTF8String: (char*) value];
    
   
    
    
    // Execute the function
    loadInterstitial(ctx, interstitialId);
    
    
    // Return
    return NULL;
}

FREObject FREShowInterstitial(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    
    // Execute the function
    showInterstitial();
    
    // Return
    return NULL;
}



FREObject FRELoadBannerAd(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    
    
    
    
    uint32_t length = 0;
    const uint8_t *value = NULL;
    NSString *bannerId;
    FREGetObjectAsUTF8( argv[0], &length, &value );
    bannerId= [NSString stringWithUTF8String: (char*) value];
    
    
    

    uint32_t length2 = 0;
    const uint8_t *value2 = NULL;
    NSString  *position;
    // Convert AS parameter to Native Parameter
   FREGetObjectAsUTF8( argv[1], &length2, &value2 );
    position = [NSString stringWithUTF8String: (char*) value2];
    
   
    
    
    // Execute the function
    loadBannerAd(bannerId,position);
    
    
    // Return
    return NULL;
}




FREObject FREgetAdaptiveBannerHeight(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
   
    
 
   FREObject adaptivebannersize;
    FRENewObjectFromDouble(GADAdSizeBanner.size.height*2, &adaptivebannersize);
 
    return adaptivebannersize;
}

FREObject FREhideAdaptiveBanner(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    [BannerView removeFromSuperview];
    return NULL;
}


void RewardedTimerCallback(void) {
    // set timer bool false
    RewardedBool=false;
}

void loadRewardedInterstitialAd (FREContext ctx,NSString* rewardeId){
    
    rewardedID_local=rewardeId;
    
    
    
    
    // Create a dispatch queue
    dispatch_queue_t queue = dispatch_queue_create("com.glm.admob", DISPATCH_QUEUE_SERIAL);
    
    // Create a timer that fires after 7 seconds
    dispatch_time_t interval = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(7.0 * NSEC_PER_SEC));
    dispatch_after(interval, queue, ^{
        RewardedTimerCallback();
    });
    
    
    
    
    UIWindow* win =   [[UIApplication sharedApplication] keyWindow];
    viewController = [win rootViewController];
    
    
    AdMobRewardedVideoDelegate *delegate = [[AdMobRewardedVideoDelegate alloc] init];
    delegate.context=ctx;
    
    
    rewardedInterstitialAd.fullScreenContentDelegate =  delegate;
    
    if(RewardedBool==true)
    {
        rewardedInterstitialAd.fullScreenContentDelegate =  delegate;
        
        [GADRewardedInterstitialAd loadWithAdUnitID:rewardeId
                                            request:requestGADMobileAdsParams()
                                  completionHandler:^(GADRewardedInterstitialAd *ad, NSError *error) {
            if (error) {
                NSLog(@"Failed to load rewarded interstitial ad with error: %@",
                      error.localizedDescription);
                FREDispatchStatusEventAsync(mContext, (uint8_t*)[@"onAdFailedToLoad" UTF8String], (uint8_t*)[@"events" UTF8String]);
                
                loadRewardedInterstitialAd(ctx,rewardedID_local);
                // self.playAgainButton.hidden = NO;
                return;
            }
            rewardedInterstitialAd.fullScreenContentDelegate =  delegate;
            RewardedBool=false;
            rewardedInterstitialAd = ad;
            rewardedInterstitialAd.fullScreenContentDelegate =  delegate;
        }];
    }
    
}






FREObject FREloadRewardedInterstitialAd(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{

    
    uint32_t length = 0;
    const uint8_t *value = NULL;
    NSString *rewardedId;
    
   
    FREGetObjectAsUTF8( argv[0], &length, &value );
    rewardedId = [NSString stringWithUTF8String: (char*) value];
    
   
    loadRewardedInterstitialAd(ctx, rewardedId);
    
    
    // Return
    return NULL;
}



void showRewardedInterstitialAd(void) {
    

    
    [rewardedInterstitialAd
        presentFromRootViewController:UIApplication.sharedApplication.keyWindow.rootViewController
             userDidEarnRewardHandler:^{
               GADAdReward *reward = rewardedInterstitialAd.adReward;

        FREDispatchStatusEventAsync(mContext, (uint8_t*)[@"onUserEarnedReward" UTF8String], (uint8_t*)[@"events" UTF8String]);
        
        
               NSString *rewardMessage =
                [NSString stringWithFormat:@"Reward received with "
                                             @"currency %@ , amount %ld",
                                              reward.type, [reward.amount longValue]];
               NSLog(@"%@", rewardMessage);
        
      
        

    }];
      
      
  
}

FREObject FREshowRewardedInterstitialAd(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    
    // Execute the function
    showRewardedInterstitialAd();
    
    // Return
    return NULL;
}
bool AppOpenBool=true;
bool AppOpenBool2=true;


void AppOpenTimerCallback(void) {
    // Do something here
    AppOpenBool=false;
}
void AppOpenTimerCallback2(void) {
    // Do something here
    AppOpenBool2=false;
}
// Get the screen size
CGSize getScreenSize(void) {
    UIScreen *screen = [UIScreen mainScreen];
    CGRect screenBounds = [screen bounds];
    return screenBounds.size;
}
// Get the current interface orientation
UIInterfaceOrientation getInterfaceOrientation(void) {
    UIApplication *application = [UIApplication sharedApplication];
    return application.statusBarOrientation;
}

void showAppOpenAd(FREContext ctx,NSString *AppOpenId) {
  

    UIWindow* win =   [[UIApplication sharedApplication] keyWindow];
    viewController = [win rootViewController];
    AppOpenBool=true;
    AppOpenBool2=true;
    // Create a timer that fires after 3 seconds
    
    // Create a dispatch queue
    dispatch_queue_t queue = dispatch_queue_create("com.glm.admob", DISPATCH_QUEUE_SERIAL);

    // Create a timer that fires after 3 seconds
    dispatch_time_t interval = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3.0 * NSEC_PER_SEC));
    dispatch_after(interval, queue, ^{
        AppOpenTimerCallback();
    });
    
    // Create second dispatch queue
    dispatch_queue_t queue2 = dispatch_queue_create("com.glm.admob", DISPATCH_QUEUE_SERIAL);

    // Create secong timer that fires after 10 seconds
    dispatch_time_t interval2 = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(10.0 * NSEC_PER_SEC));
    dispatch_after(interval2, queue2, ^{
        AppOpenTimerCallback2();
    });
    
    if (appOpenAd) {

        AppOpenBool=false;
        [appOpenAd presentFromRootViewController:viewController];
      } else {
        
          
     

    
    if(appOpenAd==nil){
        [GADAppOpenAd loadWithAdUnitID:AppOpenId
                               request:requestGADMobileAdsParams()
                           orientation:getInterfaceOrientation()
                     completionHandler:^(GADAppOpenAd *_Nullable appOpenAd, NSError *_Nullable error) {
            if (error) {
                if(AppOpenBool==true ){
                    
                    showAppOpenAd(ctx,AppOpenId);
                }else{
                    
                    if(AppOpenBool2==true ){
                        
                        [GADAppOpenAd loadWithAdUnitID:AppOpenId
                                               request:requestGADMobileAdsParams()
                                           orientation:getInterfaceOrientation()
                                     completionHandler:^(GADAppOpenAd *_Nullable appOpenAd, NSError *_Nullable error) {
                            if (error) {
                                
                                showAppOpenAd(ctx,AppOpenId);

                              //  NSLog(@"Failed to load app open ad: %@", error);
                               // return;
                            }
                            appOpenAd = appOpenAd;
                            
                        }];
                    }
                }
                    
              //  NSLog(@"Failed to load app open ad: %@", error);
               // return;
            }
            appOpenAd = appOpenAd;
            [appOpenAd presentFromRootViewController:viewController];

            
            
         }];
    }else{
        AppOpenBool=false;
        [appOpenAd presentFromRootViewController:viewController];
    }
      }
      
  
}



FREObject FREshowAppOpenAd(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    
    uint32_t length = 0;
    const uint8_t *value = NULL;
    NSString *AppOpenId;
    
   
    FREGetObjectAsUTF8( argv[0], &length, &value );
    AppOpenId = [NSString stringWithUTF8String: (char*) value];
    
   
    showAppOpenAd(ctx, AppOpenId);
    
    
    // Return
    return NULL;
}




FREObject FREtest_device_id(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]){
    
    uint32_t length = 0;
    const uint8_t *value = NULL;

    FREGetObjectAsUTF8( argv[0], &length, &value );
    testDevideID = [NSString stringWithUTF8String: (char*) value];
    
    return  NULL;
}






FREObject FREtagForUnderAgeOfConsent(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]){

    uint32_t myBoolValue;
   
    FREGetObjectAsBool(argv[0], &myBoolValue);

    if(myBoolValue != 0){
       
        tagForUnderAgeOfConsent_bool = true;
    }else {
        
        tagForUnderAgeOfConsent_bool = false;
        
    }
    
    return NULL;
}
FREObject FREtag_for_child_directed_treatment(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]){

    uint32_t myBoolValue;

    FREGetObjectAsBool(argv[0], &myBoolValue);

    if(myBoolValue != 0){
        
        tag_for_child_directed_treatment_bool = true;
    }else {
        tag_for_child_directed_treatment_bool = false;
        
    }
    
    return NULL;
}

FREObject FREtag_for_max_ad_content_rating(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]){
    
    uint32_t length = 0;
    const uint8_t *value = NULL;

    FREGetObjectAsUTF8( argv[0], &length, &value );
    tag_for_max_ad_content_rating_str = [NSString stringWithUTF8String: (char*) value];
    
    return  NULL;
}

FREObject FREmessage(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]){
    
    uint32_t length = 0;
    const uint8_t *value = NULL;

    FREGetObjectAsUTF8( argv[0], &length, &value );
    NSString*messages = [NSString stringWithUTF8String: (char*) value];
    message(messages);
    
    return  NULL;
}


void ContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctionsToSet, const FRENamedFunction** functionsToSet)
{
     *numFunctionsToSet = 15;
    
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * *numFunctionsToSet);
    
    func[0].name = (const uint8_t*) "loadInterstitial";
    func[0].functionData = NULL;
    func[0].function = FRELoadInterstitial;
    
    func[1].name = (const uint8_t*) "showInterstitial";
    func[1].functionData = NULL;
    func[1].function = FREShowInterstitial;
    
    func[2].name = (const uint8_t*) "showAdaptiveBanner";
    func[2].functionData = NULL;
    func[2].function = FRELoadBannerAd;
    
    func[3].name = (const uint8_t*) "getAdaptiveBannerHeight";
    func[3].functionData = NULL;
    func[3].function = FREgetAdaptiveBannerHeight;
    
    func[4].name = (const uint8_t*) "hideAdaptiveBanner";
    func[4].functionData = NULL;
    func[4].function = FREhideAdaptiveBanner;
    
    func[5].name = (const uint8_t*) "loadRewardedVideo";
    func[5].functionData = NULL;
    func[5].function = FREloadRewardedInterstitialAd;
    
    func[6].name = (const uint8_t*) "showRewardedVideo";
    func[6].functionData = NULL;
    func[6].function = FREshowRewardedInterstitialAd;
    
    
    func[7].name = (const uint8_t*) "showAppOpen";
    func[7].functionData = NULL;
    func[7].function = FREshowAppOpenAd;
    
    func[8].name = (const uint8_t*) "test_device_id";
    func[8].functionData = NULL;
    func[8].function = FREshowAppOpenAd;
    
    func[9].name = (const uint8_t*) "debug_geography_EEA";
    func[9].functionData = NULL;
    func[9].function = FREDebug_Geography_EEA;
    
    func[10].name = (const uint8_t*) "tag_for_under_age_of_consent";
    func[10].functionData = NULL;
    func[10].function = FREtagForUnderAgeOfConsent;
    
    func[11].name = (const uint8_t*) "tag_for_child_directed_treatment";
    func[11].functionData = NULL;
    func[11].function = FREtag_for_child_directed_treatment;
    
    func[12].name = (const uint8_t*) "tag_for_max_ad_content_rating";
    func[12].functionData = NULL;
    func[12].function = FREtag_for_max_ad_content_rating;
    
    
    func[13].name = (const uint8_t*) "tag_for_max_ad_content_rating";
    func[13].functionData = NULL;
    func[13].function = FREtag_for_max_ad_content_rating;
    
    func[14].name = (const uint8_t*) "message";
    func[14].functionData = NULL;
    func[14].function = FREmessage;
    
    *functionsToSet = func;
    
    mContext =ctx;
    
}



void ContextFinalizer(FREContext ctx)
{
    
    
    return;
}



void ExtInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, FREContextFinalizer* ctxFinalizerToSet)
{
    //NSLog(@"Entering admobExtInitializer()");
    
    *extDataToSet = NULL;
    *ctxInitializerToSet = &ContextInitializer;
    *ctxFinalizerToSet = &ContextFinalizer;
    
    //NSLog(@"Exiting admobExtInitializer()");
}



void ExtFinalizer(void* extData)
{
    return;
}






