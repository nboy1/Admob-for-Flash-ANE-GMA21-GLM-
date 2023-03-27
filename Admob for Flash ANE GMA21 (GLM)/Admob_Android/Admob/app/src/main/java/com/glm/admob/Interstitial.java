package com.glm.admob;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import com.adobe.fre.FREContext;


public class Interstitial   {
    public String  Admob_Id;
    boolean loaded =false;
    boolean timer=false;
    private InterstitialAd interstitial;
    private FREContext context;
    private RequestConfigurations requestConfigurations=new RequestConfigurations();


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

    public void setContext(FREContext ctx) {
        this.context = ctx;

    }


    public void LoadInterstitial(String admobID )    {
        countDown();
        Admob_Id=admobID;
        InterstitialAd.load(context.getActivity(), admobID, requestConfigurations.request(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd Ad) {
                        Admob_Id=admobID;
                        context.dispatchStatusEventAsync(Admob_Id,Admob_Id);
                        loaded=true;
                        interstitial = Ad;

                     }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        loaded=false;
                        interstitial = null;
                        if(timer==false)
                        {
                            LoadInterstitial(Admob_Id);
                        }
                     }

                });

    }



    public void ShowInterstitial(){
        if(interstitial!=null&&loaded==true){

            interstitial.show(context.getActivity());
            LoadInterstitial(Admob_Id);

        }
        else {
            LoadInterstitial(Admob_Id);
        }

    }







}

