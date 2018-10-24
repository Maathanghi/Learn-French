package com.french.flash_cards;

import android.app.Application;
import android.content.Context;

import com.french.flash_cards.utils.TypefaceUtil;


/**
 * Created by anand on 31/07/2018.
 */

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/grandhotel.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/quicksandRegular.otf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/quicksandRegular.otf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/quicksandRegular.otf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/quicksandRegular.otf");
    }


}