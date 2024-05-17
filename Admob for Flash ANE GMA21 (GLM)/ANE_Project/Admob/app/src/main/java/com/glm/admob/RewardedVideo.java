package com.glm.admob;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.adobe.fre.FREContext;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardedVideo {
    public FREContext context;
    private RewardedAd rewardedVideoAd;

    public String  Rewarded_Video_Key;
    boolean loaded =false;
    boolean timer=false;

    private RequestConfigurations requestConfigurations=new RequestConfigurations();

   public void setContext(FREContext ctx) {
        this.context = ctx;

    }
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
    public void LoadRewardedVideo(String RewardedVideoKey){
        Rewarded_Video_Key=RewardedVideoKey;
        countDown();


        if (rewardedVideoAd == null) {
            RewardedAd.load(
                    context.getActivity(),
                    Rewarded_Video_Key,
                    requestConfigurations.request(),
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull RewardedAd Ad) {
                            rewardedVideoAd = Ad;
                             loaded=true;
                            context.dispatchStatusEventAsync("onAdLoaded", "events");

                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            rewardedVideoAd = null;
                             context.dispatchStatusEventAsync("onAdFailedToLoad", "events");

                            if(timer==false)
                            {
                            LoadRewardedVideo(RewardedVideoKey);
                            }
                        }
                    });


        }
    }
    public void ShowRewardedVideo(){


            rewardedVideoAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                             rewardedVideoAd = null;
                            LoadRewardedVideo(Rewarded_Video_Key);
                            context.dispatchStatusEventAsync("onAdShowedFullScreenContent", "events");

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.

                            rewardedVideoAd = null;
                            context.dispatchStatusEventAsync("onAdFailedToShowFullScreenContent", "events");

                            LoadRewardedVideo(Rewarded_Video_Key);

                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Don't forget to set the ad reference to null so you
                            // don't show the ad a second time.
                            rewardedVideoAd = null;
                            context.dispatchStatusEventAsync("onAdDismissedFullScreenContent", "events");

                            LoadRewardedVideo(Rewarded_Video_Key);

                        }
                    });
            rewardedVideoAd.show(
                    context.getActivity(),
                    new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.


                            context.dispatchStatusEventAsync("onUserEarnedReward", "events");


                        }
                    });

    }



}
