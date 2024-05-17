package com.glm.admob;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Time;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.adobe.fre.FREContext;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RequestConfigurations {
    private FREContext context;
    String _TEST_DEVICE_ID;
    boolean _PERSONALIZED_ADS ;
     boolean is_tag_CHILD_DIRECTED_TREATMENT =true ;
    boolean is_tag_UNDER_AGE_OF_CONSENT =true;
    String is_tag_MAX_AD_CONTENT_RATING ;
    AdRequest adRequest;
    private ConsentInformation consentInformation;
    public ConsentForm consentForm_;
    private FrameLayout banner_view;;
    public TextView textView;
    public String errors;
    public String errors2;
public boolean aBoolean=false;
    public void setContext(FREContext ctx) {
        this.context = ctx;

    }
    RequestConfiguration.Builder configurationBuilder =
            MobileAds.getRequestConfiguration().toBuilder();

    RequestConfiguration requestConfiguration = MobileAds.getRequestConfiguration()
            .toBuilder().build();

    public  void Set_value(
            String TEST_DEVICE_ID,
            boolean PERSONALIZED_ADS,
             Boolean tag_CHILD_DIRECTED_TREATMENT,
            Boolean tag_UNDER_AGE_OF_CONSENT,
            String tag_MAX_AD_CONTENT_RATING
                                )
    {
        _TEST_DEVICE_ID=TEST_DEVICE_ID;
        _PERSONALIZED_ADS=PERSONALIZED_ADS;
           is_tag_CHILD_DIRECTED_TREATMENT =tag_CHILD_DIRECTED_TREATMENT;
          is_tag_UNDER_AGE_OF_CONSENT =tag_UNDER_AGE_OF_CONSENT;
          is_tag_MAX_AD_CONTENT_RATING =tag_MAX_AD_CONTENT_RATING;

    }


    public AdRequest request() {





        if(_TEST_DEVICE_ID!=null&&!_TEST_DEVICE_ID.isEmpty()){
            List<String> testDeviceIDs = new ArrayList<>();
            testDeviceIDs.add(_TEST_DEVICE_ID);
            requestConfiguration.toBuilder().setTestDeviceIds(testDeviceIDs)
                    .build();
        }


        if(is_tag_CHILD_DIRECTED_TREATMENT==true||is_tag_UNDER_AGE_OF_CONSENT==true)
        {



            configurationBuilder.setTagForChildDirectedTreatment(requestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
                    .build();
            configurationBuilder.setTagForUnderAgeOfConsent(requestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE)
                    .build();
            // errors= "configurationBuilder==true1";
            //banner();
            if(is_tag_MAX_AD_CONTENT_RATING=="G"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_G)
                        .build();
            }
            if(is_tag_MAX_AD_CONTENT_RATING=="PG"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_PG)
                        .build();
            }
            if(is_tag_MAX_AD_CONTENT_RATING=="T"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_T)
                        .build();
            }
            if(is_tag_MAX_AD_CONTENT_RATING=="MA"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_MA)
                        .build();
            }


        }

        if(is_tag_CHILD_DIRECTED_TREATMENT==false||is_tag_UNDER_AGE_OF_CONSENT==false){

            configurationBuilder.setTagForChildDirectedTreatment(
                            requestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE)
                    .build();
            configurationBuilder.setTagForUnderAgeOfConsent(
                            requestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE)
                    .build();

            if(is_tag_MAX_AD_CONTENT_RATING=="G"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_G)
                        .build();
            }
            if(is_tag_MAX_AD_CONTENT_RATING=="PG"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_PG)
                        .build();
            }
            if(is_tag_MAX_AD_CONTENT_RATING=="T"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_T)
                        .build();
            }
            if(is_tag_MAX_AD_CONTENT_RATING=="MA"){
                configurationBuilder.setMaxAdContentRating(requestConfiguration.MAX_AD_CONTENT_RATING_MA)
                        .build();
            }

            UserMessagingPlatforms();

        }

        MobileAds.setRequestConfiguration(requestConfiguration);
        adRequest = new AdRequest.Builder().build();
        return adRequest;



    }



  public void UserMessagingPlatforms(){

            // Set tag for underage of consent. Here false means users are not underage.
            ConsentRequestParameters params = new ConsentRequestParameters
                    .Builder()
                    .setTagForUnderAgeOfConsent(false)
                    .build();

            consentInformation = UserMessagingPlatform.getConsentInformation(context.getActivity());
            consentInformation.requestConsentInfoUpdate(
                    context.getActivity(),
                    params,
                    new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                        @Override
                        public void onConsentInfoUpdateSuccess() {
                            // The consent information state was updated.
                            // You are now ready to check if a form is available.
                          //  errors="onConsentInfoUpdateSuccess";
                            //banner();
                            load();
                        }
                    },
                    new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                        @Override
                        public void onConsentInfoUpdateFailure(FormError formError) {
                            // Handle the error.
                           // errors= formError.getMessage();
                            //banner();
                        }
                    });
            return;
        }

    public void load(){
        UserMessagingPlatform.loadConsentForm(
                context.getActivity(), new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        consentForm_ = consentForm;
                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm_.show(
                                    context.getActivity(),
                                    new ConsentForm.OnConsentFormDismissedListener() {
                                        @Override
                                        public void onConsentFormDismissed(FormError formError) {
                                            // Handle dismissal by reloading form.
                                           // errors= formError.getMessage();
                                          //  banner();
                                          //  load();
                                        }
                                    });
                        }
                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        // Handle the error.
                      //  errors= formError.getMessage();
                       // banner();
                    }
                });

        return;

    }

    ///////////////////DEBUG_GEOGRAPHY_EEA ////////////////
    public void DEBUG_GEOGRAPHY_EEA(){

        if (_TEST_DEVICE_ID==null||_TEST_DEVICE_ID.isEmpty()){
            String android_id = Settings.Secure.getString(context.getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            String deviceId = md5(android_id).toUpperCase();
            _TEST_DEVICE_ID=deviceId;
        }
        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(context.getActivity())
                .setDebugGeography(ConsentDebugSettings
                        .DebugGeography
                        .DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId(_TEST_DEVICE_ID)
                .build();
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setConsentDebugSettings(debugSettings)
                .build();
        consentInformation = UserMessagingPlatform.getConsentInformation(context.getActivity());
        consentInformation.requestConsentInfoUpdate(
                context.getActivity(),
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        errors="test onConsentInfoUpdateSuccess";
                       // banner();
                        load();
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.
                         errors= formError.getMessage();
                     }
                });
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Logger.logStackTrace(TAG,e);
        }
        return "";
    }
    public void banner(String  message){

        textView=new TextView(context.getActivity());
        FrameLayout.LayoutParams pa = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 50);
        // pa.weight = 1.0f;


        pa.gravity = Gravity.BOTTOM;
        banner_view = new FrameLayout(context.getActivity());

        banner_view.setBackgroundColor(  Color.GRAY
        );
        context.getActivity().addContentView(banner_view,pa);// Code above
        textView.setText(message);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        banner_view.addView(textView);
    }
}
