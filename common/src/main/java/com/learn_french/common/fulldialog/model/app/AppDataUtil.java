package com.learn_french.common.fulldialog.model.app;

import android.content.Context;

import com.learn_french.common.fulldialog.viewpager.AppHelper;

import java.util.ArrayList;

public class AppDataUtil {

    private static AppDataUtil single_instance = null;
    public static String appJsonURL = "app.json";
    private ArrayList<App> appData;

    public static AppDataUtil getInstance()
    {
        if (single_instance == null)
            single_instance = new AppDataUtil();

        return single_instance;
    }

    public ArrayList<Lesson> getCategoryList(){
        //AppHelper.getAppDataFromJson();
        return null;
    }

    public ArrayList<App> getAppList(Context ctx){
        if(appData == null){
            appData = AppHelper.getAppDataFromJson(appJsonURL, ctx);
        }
         return appData;
    }

    public App getLesson(String title, ArrayList<App> appData){
        for(App app : appData) {
            if(app.getTitle().equalsIgnoreCase(title)) {
                return app;
            }
        }
        return null;
    }
}
