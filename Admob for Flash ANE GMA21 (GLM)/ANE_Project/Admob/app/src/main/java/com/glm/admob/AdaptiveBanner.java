package com.glm.admob;

import android.graphics.Color;
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

public class AdaptiveBanner {
    private FREContext context;
    private FrameLayout banner_view;
    private boolean isInitialized = false;
    private AdView adView;
    private TextView textView;

    private RequestConfigurations requestConfigurations = new RequestConfigurations();

    public void setContext(FREContext ctx) {
        this.context = ctx;
    }

    public void showAdaptiveBanner(final String admobID, String position) {
        // 1. Initialize SDK
        if (!isInitialized) {
            MobileAds.initialize(this.context.getActivity());
            isInitialized = true;
        }

        // 2. Avoid duplicate banners
        if (banner_view != null) return;

        // 3. Prepare the AdView FIRST
        adView = new AdView(this.context.getActivity());
        adView.setAdUnitId(admobID);
        adView.setAdSize(getAdSize());

        // 4. Setup Layout Params
        int heightPx = getAdSize().getHeightInPixels(this.context.getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                heightPx
        );
        params.gravity = position.equalsIgnoreCase("BOTTOM") ? Gravity.BOTTOM : Gravity.TOP;

        // 5. Create the Container
        banner_view = new FrameLayout(this.context.getActivity());
        banner_view.setBackgroundColor(Color.BLACK);

        // 6. Add Debug Text
        textView = new TextView(this.context.getActivity());
        textView.setText("Loading Ad...");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        banner_view.addView(textView);

        // 7. Add AdView to Container
        banner_view.addView(adView);

        // 8. Add Container to Activity
        context.getActivity().addContentView(banner_view, params);

        // 9. Load the Ad
        adView.loadAd(requestConfigurations.request());

        // Bring to front to ensure it's over the AIR Stage
        banner_view.bringToFront();
    }

    public AdSize getAdSize() {
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                this.context.getActivity(),
                AdSize.FULL_WIDTH
        );
    }

    public int AdaptiveBannerHeight() {
         // Return the height of the adaptive ad
        return getAdSize().getHeightInPixels(this.context.getActivity());
    }

    public void Hide() {
        if (banner_view != null) {
            context.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (banner_view.getParent() != null) {
                        ((ViewGroup) banner_view.getParent()).removeView(banner_view);
                    }
                    banner_view = null;
                }
            });
        }
    }
}