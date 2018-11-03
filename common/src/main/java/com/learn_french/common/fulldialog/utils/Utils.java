package com.learn_french.common.fulldialog.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.learn_french.common.R;

public class Utils {

    public static void animateIntent(View viewStart, Activity activity, String transitionName, Class toActivity) {

        // Ordinary Intent for launching a new activity
        Intent intent = new Intent(activity, toActivity);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        viewStart,   // Starting view
                        transitionName    // The String
                );
        //Start the Intent
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void startFadeInAnimation(View view, Context ctx) {
        Animation startAnimation = AnimationUtils.loadAnimation(ctx, R.anim.fade_in_animation);
        view.startAnimation(startAnimation);
    }

    public static void startFadeOutAnimation(View view, Context ctx) {
        Animation startAnimation = AnimationUtils.loadAnimation(ctx, R.anim.fade_out_animation);
        view.startAnimation(startAnimation);
    }

    public static void revealActivity(int x, int y, View rootLayout, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            activity.finish();
        }
    }

    public static void unRevealActivity(int revealX, int revealY, final Activity activity, final View rootLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    activity.finish();
                }
            });
            circularReveal.start();
        }
    }

}
