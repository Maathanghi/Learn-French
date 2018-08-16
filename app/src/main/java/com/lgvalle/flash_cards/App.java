package com.lgvalle.flash_cards;

import android.app.Application;

import com.lgvalle.flash_cards.utils.TypefaceUtil;

/**
 * Created by anand on 31/07/2018.
 */

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/grandHotel.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

    }
}