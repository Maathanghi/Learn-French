package com.learn_french.common.fulldialog.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
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
}
