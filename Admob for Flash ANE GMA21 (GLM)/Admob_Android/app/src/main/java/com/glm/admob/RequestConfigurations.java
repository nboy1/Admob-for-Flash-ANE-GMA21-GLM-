package com.glm.admob;

import android.graphics.Color;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.adobe.fre.FREContext;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class RequestConfigurations {

    private FREContext context;

    String _TEST_DEVICE_ID;
    boolean _PERSONALIZED_ADS;
    boolean is_tag_CHILD_DIRECTED_TREATMENT = true;
    boolean is_tag_UNDER_AGE_OF_CONSENT = true;
    String is_tag_MAX_AD_CONTENT_RATING;

    AdRequest adRequest;

    private ConsentInformation consentInformation;
    public ConsentForm consentForm_;

    private FrameLayout banner_view;
    public TextView textView;

    public String errors;
    public String errors2;
    public boolean aBoolean = false;

    public void setContext(FREContext ctx) {
        this.context = ctx;
    }


    public void Set_value(
            String TEST_DEVICE_ID,
            boolean PERSONALIZED_ADS,
            Boolean tag_CHILD_DIRECTED_TREATMENT,
            Boolean tag_UNDER_AGE_OF_CONSENT,
            String tag_MAX_AD_CONTENT_RATING
    ) {
        _TEST_DEVICE_ID = TEST_DEVICE_ID;
        _PERSONALIZED_ADS = PERSONALIZED_ADS;
        is_tag_CHILD_DIRECTED_TREATMENT = tag_CHILD_DIRECTED_TREATMENT;
        is_tag_UNDER_AGE_OF_CONSENT = tag_UNDER_AGE_OF_CONSENT;
        is_tag_MAX_AD_CONTENT_RATING = tag_MAX_AD_CONTENT_RATING;
    }

    // ================= REQUEST =================
    public AdRequest request() {

        RequestConfiguration.Builder builder =
                MobileAds.getRequestConfiguration().toBuilder();

        // ---------- TEST DEVICE ----------
        if (_TEST_DEVICE_ID != null && !_TEST_DEVICE_ID.isEmpty()) {
            List<String> testDeviceIDs = new ArrayList<>();
            testDeviceIDs.add(_TEST_DEVICE_ID);
            builder.setTestDeviceIds(testDeviceIDs);
        }

        // ---------- CHILD / UNDER AGE ----------
        builder.setTagForChildDirectedTreatment(
                is_tag_CHILD_DIRECTED_TREATMENT
                        ? RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
                        : RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE
        );

        builder.setTagForUnderAgeOfConsent(
                is_tag_UNDER_AGE_OF_CONSENT
                        ? RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE
                        : RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE
        );

        // ---------- MAX CONTENT RATING ----------
        if ("G".equals(is_tag_MAX_AD_CONTENT_RATING)) {
            builder.setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G);
        } else if ("PG".equals(is_tag_MAX_AD_CONTENT_RATING)) {
            builder.setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_PG);
        } else if ("T".equals(is_tag_MAX_AD_CONTENT_RATING)) {
            builder.setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_T);
        } else if ("MA".equals(is_tag_MAX_AD_CONTENT_RATING)) {
            builder.setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_MA);
        }

        //  APPLY CONFIG
        MobileAds.setRequestConfiguration(builder.build());

        // ---------- CONSENT ----------
        if (!is_tag_CHILD_DIRECTED_TREATMENT || !is_tag_UNDER_AGE_OF_CONSENT) {
            UserMessagingPlatforms();
        }

        adRequest = new AdRequest.Builder().build();
        return adRequest;
    }

    // ================= UMP =================
    public void UserMessagingPlatforms() {

        ConsentRequestParameters params =
                new ConsentRequestParameters.Builder()
                        .setTagForUnderAgeOfConsent(false)
                        .build();

        consentInformation =
                UserMessagingPlatform.getConsentInformation(context.getActivity());

        consentInformation.requestConsentInfoUpdate(
                context.getActivity(),
                params,
                () -> load(),
                formError -> errors = formError.getMessage()
        );
    }

    public void load() {

        UserMessagingPlatform.loadConsentForm(
                context.getActivity(),
                consentForm -> {
                    consentForm_ = consentForm;
                    if (consentInformation.getConsentStatus()
                            == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm_.show(context.getActivity(), formError -> {
                            if (formError != null)
                                errors = formError.getMessage();
                        });
                    }
                },
                formError -> errors2 = formError.getMessage()
        );
    }

    // ================= DEBUG EEA =================
    public void DEBUG_GEOGRAPHY_EEA() {

        if (_TEST_DEVICE_ID == null || _TEST_DEVICE_ID.isEmpty()) {
            String android_id = Settings.Secure.getString(
                    context.getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
            _TEST_DEVICE_ID = md5(android_id).toUpperCase();
        }

        ConsentDebugSettings debugSettings =
                new ConsentDebugSettings.Builder(context.getActivity())
                        .setDebugGeography(
                                ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                        .addTestDeviceHashedId(_TEST_DEVICE_ID)
                        .build();

        ConsentRequestParameters params =
                new ConsentRequestParameters.Builder()
                        .setConsentDebugSettings(debugSettings)
                        .build();

        consentInformation =
                UserMessagingPlatform.getConsentInformation(context.getActivity());

        consentInformation.requestConsentInfoUpdate(
                context.getActivity(),
                params,
                () -> load(),
                formError -> errors = formError.getMessage()
        );
    }

    // ================= MD5 =================
    public static String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] messageDigest = digest.digest(s.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    // ================= DEBUG BANNER =================
    public void banner(String message) {

        textView = new TextView(context.getActivity());
        FrameLayout.LayoutParams pa =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 50);

        pa.gravity = Gravity.BOTTOM;

        banner_view = new FrameLayout(context.getActivity());
        banner_view.setBackgroundColor(Color.GRAY);

        context.getActivity().addContentView(banner_view, pa);

        textView.setText(message);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);

        banner_view.addView(textView);
    }
}
