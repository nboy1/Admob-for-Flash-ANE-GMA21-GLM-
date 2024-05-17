package com.glm.admob;

import android.graphics.Color;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.adobe.fre.FREContext;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

public class AdaptiveBanner {
    private FREContext context;
    private FrameLayout banner_view;;
    private boolean initialLayoutComplete = false;
    int ColorValue;
    public AdView adView;
    public TextView textView;
    public void setContext(FREContext ctx) {
        this.context = ctx;
    }

    public int height;
    public String Admob_ID;
    public  String position_;
    private RequestConfigurations requestConfigurations=new RequestConfigurations();


    public  void showAdaptiveBanner(String admobID, String position) {
        Admob_ID =admobID;
       // position_=position;

        MobileAds.initialize(context.getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) { }
        });
        adView = new AdView(context.getActivity());
        textView=new TextView(context.getActivity());
        adView.setBackgroundColor(ColorValue);
        height = getAdSize().getHeightInPixels(context.getActivity());
        int total = height;
        if(initialLayoutComplete ==false){
        FrameLayout.LayoutParams pa = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, total);
        ColorValue = Color.BLACK;





            if(position.trim().equals("BOTTOM")||position.trim().equals("bottom")||position.trim().equals("Bottom")){

                pa.gravity = Gravity.BOTTOM;
            }
            else {

                pa.gravity=Gravity.TOP;
            }



            banner_view = new FrameLayout(context.getActivity());

        banner_view.setBackgroundColor(ColorValue);
        context.getActivity().addContentView(banner_view,pa);// Code above
        textView.setText("Banner Ad");
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        banner_view.addView(textView);



        banner_view.addView(adView);
        banner_view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (!initialLayoutComplete) {
                            initialLayoutComplete = true;
                            loadBanner(admobID);
                        }
                    }
                });

    }else {
            textView.setText("Banner Ad");
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            banner_view.addView(textView);
            banner_view.addView(adView);
            loadBanner(admobID);

        }


    }

    private void loadBanner(String AdmobID){
        adView.setAdUnitId(AdmobID);
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);


        adView.loadAd(requestConfigurations.request());
    }


    public AdSize getAdSize() {


        return    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context.getActivity(), AdSize.FULL_WIDTH);

    }


    public  int AdaptiveBannerHeight(){
        int adaptiveBannerHeight = banner_view.getHeight();
        return adaptiveBannerHeight;
    }
    public void Hide(){
        banner_view.removeAllViews();

    }
}



