package com.glm.admob;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import java.util.HashMap;
import java.util.Map;

public class ExtensionCalls extends FREContext {



    //test Device id
    private static final String test_device_id="test_device_id";
    public String _TEST_DEVICE_ID;
    // PERSONALIZED ADS
    private static final String personalized_ads="personalized_ads";
    boolean _PERSONALIZED_ADS=false;

    //test Requesting Consent from European Users
    private static final String debug_geography_EEA="debug_geography_EEA";
    //public boolean _debug_geography_EEA=false;


    //   TAG_FOR_UNDER_AGE_OF_CONSENT value
    private static final String tag_for_under_age_of_consent="tag_for_under_age_of_consent";
    public boolean _TAG_FOR_UNDER_AGE_OF_CONSENT=true;

    // TAG_FOR_CHILD_DIRECTED_TREATMENT call
    private static final String tag_for_child_directed_treatment="tag_for_child_directed_treatment";
    public boolean _TAG_FOR_CHILD_DIRECTED_TREATMENT=true;

    //  TAG_FOR_MAX_AD_CONTENT_RATING value
    private static final String tag_for_max_ad_content_rating="tag_for_max_ad_content_rating";
    public String _TAG_FOR_MAX_AD_CONTENT_RATING;


    private RequestConfigurations requestConfigurations=new RequestConfigurations();

    //App open Ads
    private static final String showAppOpen = "showAppOpen";
    public AppOpen appOpen= new AppOpen();


    //AdaptiveBanner
    private static final String showAdaptiveBanner="showAdaptiveBanner";
    public static final String getAdaptiveBannerHeight = "getAdaptiveBannerHeight";
    private static final String hideAdaptiveBanner="hideAdaptiveBanner";
    boolean Position = false;
    private AdaptiveBanner adaptiveBanner = new AdaptiveBanner();


    //Interstitial
    private static final String loadInterstitial="loadInterstitial";
    private static final String showInterstitial="showInterstitial";
    private Interstitial interstitial_ad=new Interstitial();


    //rewarded
    private static final String loadRewardedVideo="loadRewardedVideo";
    private static final String showRewardedVideo="showRewardedVideo";
    public RewardedVideo rewardedVideo = new RewardedVideo();


    //App Review
    private static final String loadAppReview="loadAppReview";
    private static final String showAppReview="showAppReview";
    public AppReview appReview = new AppReview();



    public static final String message="message";




    public void initialize() {



    }



    @Override
    public void dispose()
    {
    }
    @Override
    public Map<String, FREFunction> getFunctions()
    {

        //////// debugging
        Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();

        
        functionMap.put(ExtensionCalls.message, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                String Msg = getString(arg1,0);
                requestConfigurations.banner(Msg);
                return null;
            }
        });


        ////////get TEST_DEVICE_ID
        functionMap.put(ExtensionCalls.test_device_id, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                String TEST_DEVICE_ID = getString(arg1,0);
                _TEST_DEVICE_ID =TEST_DEVICE_ID;
                return null;
            }
        });


        ////////PERSONALIZED_ADS or NON_PERSONALIZED_ADS
        functionMap.put(ExtensionCalls.personalized_ads, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                boolean PERSONALIZED_ADS = true;
                _PERSONALIZED_ADS =PERSONALIZED_ADS;
                return null;
            }
        });


        ////////DEBUG_GEOGRAPHY_EEA
        functionMap.put(ExtensionCalls.debug_geography_EEA, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                requestConfigurations.DEBUG_GEOGRAPHY_EEA();
                return null;
            }
        });


        ////////get TAG_FOR_UNDER_AGE_OF_CONSENT value
        functionMap.put(ExtensionCalls.tag_for_under_age_of_consent, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                Boolean TAG_FOR_UNDER_AGE_OF_CONSENT = getBoolean(arg1,0);
                _TAG_FOR_UNDER_AGE_OF_CONSENT=TAG_FOR_UNDER_AGE_OF_CONSENT;
                return null;
            }
        });


        ////////get TAG_FOR_CHILD_DIRECTED_TREATMENT value
        functionMap.put(ExtensionCalls.tag_for_child_directed_treatment, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                Boolean TAG_FOR_CHILD_DIRECTED_TREATMENT = getBoolean(arg1,0);
                _TAG_FOR_CHILD_DIRECTED_TREATMENT=TAG_FOR_CHILD_DIRECTED_TREATMENT;
                return null;
            }
        });


        ////////get TAG_FOR_MAX_AD_CONTENT_RATING value
        functionMap.put(ExtensionCalls.tag_for_max_ad_content_rating, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(  arg0  ));
                String TAG_FOR_MAX_AD_CONTENT_RATING = getString(arg1,0);
                _TAG_FOR_MAX_AD_CONTENT_RATING=TAG_FOR_MAX_AD_CONTENT_RATING;
                return null;
            }
        });


        ////Admob App Open Ads
        functionMap.put(ExtensionCalls.showAppOpen, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                appOpen.setContext(cc(  arg0  ));
                String appOpenKey = getString(arg1, 0);
                set_values();
                appOpen.countDown();
                appOpen.countDown2();
                appOpen.showAppOpenAd(appOpenKey);
                return null;
            }
        });


        ////Admob App Review
        functionMap.put(ExtensionCalls.loadAppReview, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
               appReview.setContext(cc(  arg0  ));
              appReview.countDown();
               appReview.requestReview();
                return null;
            }
        });

        functionMap.put(ExtensionCalls.showAppReview, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
             //   appReview.setContext(cc(  arg0  ));
               appReview.launchReview();
                return null;
            }
        });


        //////Admob Adaptive Banner Ads
        functionMap.put(ExtensionCalls.showAdaptiveBanner, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                adaptiveBanner.setContext(cc(  arg0  ));
                String AdaptiveBannerKey = getString(arg1, 0);
                String Position = getString(arg1, 1);
                requestConfigurations.setContext(cc(arg0));
                set_values();
                  //  requestConfigurations._adRequest();
                    adaptiveBanner.showAdaptiveBanner(AdaptiveBannerKey,Position);
                  //  requestConfigurations.banner(PositionStr);
                return null;
            }
        });

        //////  Adaptive Banner Ads
        functionMap.put(ExtensionCalls.getAdaptiveBannerHeight, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                int _adaptiveBannerHeight = adaptiveBanner.AdaptiveBannerHeight();
                try {
                    return FREObject.newObject(_adaptiveBannerHeight);
                } catch (FREWrongThreadException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        functionMap.put(ExtensionCalls.hideAdaptiveBanner, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(arg0));
                set_values();
                adaptiveBanner.Hide();
                return null;
            }
        });


        //////Admob Interstitial Ads
        functionMap.put(ExtensionCalls.loadInterstitial, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(arg0));
                set_values();
                interstitial_ad.setContext(cc(  arg0  ));
                String InterstitialKey = getString(arg1, 0);
                interstitial_ad.countDown();
                interstitial_ad.LoadInterstitial(InterstitialKey);
                return null;
            }
        });
        functionMap.put(ExtensionCalls.showInterstitial, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(arg0));
                set_values();
                interstitial_ad.ShowInterstitial();
                return null;
            }
        });


        //////Admob RewardedVideo Ads
        functionMap.put(ExtensionCalls.loadRewardedVideo, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(arg0));
                set_values();
                rewardedVideo.setContext(cc(  arg0  ));
                String RewardedVideoKey = getString(arg1, 0);
                rewardedVideo.LoadRewardedVideo(RewardedVideoKey);
                return null;
            }
        });
        functionMap.put(ExtensionCalls.showRewardedVideo, new FREFunction() {
            @Override
            public FREObject call(FREContext arg0, FREObject[] arg1) {
                requestConfigurations.setContext(cc(arg0));
                set_values();
                rewardedVideo.ShowRewardedVideo();
                return null;
            }
        });



        return functionMap;


    }
    public FREContext cc( FREContext arg0 ){
        try {
            return   arg0  ;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }
    public FREObject call(FREContext arg0, FREObject[] arg1) {
        try {
            return this.getContext(ExtensionCalls.this, arg1);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    protected FREObject getContext(ExtensionCalls ctx, FREObject[] ps) {
        return null;
    }
    protected int getInt(FREObject[] ps, int index) {
        if (index < 0 || index > ps.length - 1) {
            return 0;
        }
        if (ps[index] == null) {
            return 0;
        }
        try {
            return ps[index].getAsInt();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FRETypeMismatchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FREInvalidObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FREWrongThreadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    protected boolean getBoolean(FREObject[] ps, int index) {
        if (index < 0 || index > ps.length - 1) {
            return false;
        }
        if (ps[index] == null) {
            return false;
        }
        try {
            return ps[index].getAsBool();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FRETypeMismatchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FREInvalidObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FREWrongThreadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    protected String getString(FREObject[] ps, int index) {
        if (index < 0 || index > ps.length - 1) {
            return null;
        }
        if (ps[index] == null) {
            return null;
        }
        try {
            return ps[index].getAsString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FRETypeMismatchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FREInvalidObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FREWrongThreadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void set_values(){

        requestConfigurations.Set_value(
                _TEST_DEVICE_ID,
                _PERSONALIZED_ADS,
                _TAG_FOR_CHILD_DIRECTED_TREATMENT,
                _TAG_FOR_UNDER_AGE_OF_CONSENT,
                _TAG_FOR_MAX_AD_CONTENT_RATING);
    }
}
