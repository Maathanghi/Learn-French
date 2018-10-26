package com.french.flash_cards;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.french.flash_cards.utils.TypefaceUtil;

import io.fabric.sdk.android.Fabric;


/**
 * Created by anand on 31/07/2018.
 */

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/grandhotel.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/sansationregular.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/sansationregular.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/sansationregular.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/sansationregular.ttf");

        Fabric.with(this, new Crashlytics());
    }


}