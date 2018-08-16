package com.french.flash_cards.utils;

import android.content.Context;

import com.french.flash_cards.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 05/08/2018.
 */

public class AppConstants {

    // static variable single_instance of type Singleton
    private static AppConstants single_instance = null;


    // static method to create instance of Singleton class
    public static AppConstants getInstance()
    {
        if (single_instance == null)
            single_instance = new AppConstants();

        return single_instance;
    }



}
