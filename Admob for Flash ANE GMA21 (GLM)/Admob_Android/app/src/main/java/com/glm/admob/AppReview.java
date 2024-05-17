package com.glm.admob;

import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adobe.fre.FREContext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;

public class AppReview {

    private FREContext context;
    public void setContext(FREContext ctx) {
        context = ctx;
    }
    ReviewInfo reviewInfo;
    ReviewManager manager;
   // private FREContext context;
 //  ReviewManager manager = ReviewManagerFactory.create(context.getActivity());
    boolean loaded;
    boolean timer;
    public void countDown() {
         loaded = false;
         timer = false;
        new CountDownTimer(3000, 1) {

            public void onTick(long millisUntilFinished) {
                String str=("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer=true;
            }
        }.start();
    }

  public void requestReview() {

     if(timer==false)
     {
       manager = ReviewManagerFactory.create(context.getActivity());
      Task<ReviewInfo> request = manager.requestReviewFlow();
      request.addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
              // We can get the ReviewInfo object
               reviewInfo = task.getResult();
          //    Toast.makeText(context.getActivity(), "isSuccessful", Toast.LENGTH_SHORT).show();
          } else {


              requestReview();
              // There was some problem, log or handle the error code.
             // @ReviewErrorCode int reviewErrorCode = task.getException()).getErrorCode();
          }
      });
     }

  }

    public void launchReview() {
        // manager = ReviewManagerFactory.create(context.getActivity());
       // Toast.makeText(context.getActivity(), "inside launch", Toast.LENGTH_SHORT).show();

        Task<Void> flow = manager.launchReviewFlow(context.getActivity(), reviewInfo);
        flow.addOnCompleteListener(task -> {
           // Toast.makeText(context.getActivity(), "addOnCompleteListener", Toast.LENGTH_SHORT).show();

            // The flow has finished. The API does not indicate whether the user
            // reviewed or not, or even whether the review dialog was shown. Thus, no
            // matter the result, we continue our app flow.
        });
        countDown();
        requestReview();

    }



}