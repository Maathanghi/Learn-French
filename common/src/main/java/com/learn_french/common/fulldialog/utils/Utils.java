package com.learn_french.common.fulldialog.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

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
}
