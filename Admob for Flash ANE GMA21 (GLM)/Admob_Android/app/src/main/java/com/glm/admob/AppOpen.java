package com.glm.admob;

import android.content.res.Resources;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.adobe.fre.FREContext;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class AppOpen {

    private FREContext context;
    public AppOpenAd appOpenAd;
    private RequestConfigurations requestConfigurations=new RequestConfigurations();

    int orientation;
    String admobID_;
    boolean loaded =false;
    boolean timer=false;
    boolean loaded2 =false;
    boolean timer2=false;
    public AdRequest adRequest;
    public void countDown() {
        loaded=false;
        timer=false;
         new CountDownTimer(3000, 1) {

            public void onTick(long millisUntilFinished) {
                String str=("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer=true;
            }
        }.start();
    }

    public void countDown2() {
        loaded2=false;
        timer2=false;
        new CountDownTimer(10000, 1) {

            public void onTick(long millisUntilFinished) {
                String str=("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer2=true;
            }
        }.start();
    }
    public int getOrientation(){


        int height= Resources.getSystem().getDisplayMetrics().heightPixels;
        int width= Resources.getSystem().getDisplayMetrics().widthPixels;

        if(height/width>1){orientation=1;}
        if(height/width <1){orientation=2;}
        return orientation;
     }

    public void setContext(FREContext ctx) {
        this.context = ctx;
    }

    public void showAppOpenAd(String admobID) {

        admobID_=admobID;
        if (appOpenAd!=null){
            appOpenAd.show(context.getActivity());
        }else
        {

            if(timer==false)
            {
                AppOpenAd.load(
                        context.getActivity(), admobID, requestConfigurations.request(),
                        getOrientation(),
                        new AppOpenAd.AppOpenAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull AppOpenAd ad) {
                                // Called when an app open ad has loaded.


                                appOpenAd = ad;
                                loaded=true;
                                if(timer==false){appOpenAd.show(context.getActivity());}
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Called when an app open ad has failed to load.
                                // Log.d(LOG_TAG, loadAdError.getMessage());
                                //  isLoadingAd = false;
                                // new  AppOpenAd.load(@NonNull AppOpenAd ad){}
                                // admobSwap.loadAppOpen(admobID,param);
                                if (loaded==false&&timer==false) {
                                    appOpenAd=null;
                                    showAppOpenAd(admobID_);
                                }
                            }

                        });

            }else if(timer2==false) {

                AppOpenAd.load(
                        context.getActivity(), admobID, requestConfigurations.request(),
                        getOrientation(),
                        new AppOpenAd.AppOpenAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull AppOpenAd ad) {
                                // Called when an app open ad has loaded.


                                appOpenAd = ad;
                                loaded2=true;

                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                                if (loaded2==false&&timer2==false) {
                                    appOpenAd=null;
                                    showAppOpenAd(admobID_);
                                }
                            }

                        });
            }



        }

    }

}



