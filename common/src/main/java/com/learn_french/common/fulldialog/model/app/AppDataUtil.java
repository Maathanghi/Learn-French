package com.learn_french.common.fulldialog.model.app;

import android.content.Context;

import com.learn_french.common.fulldialog.viewpager.AppHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppDataUtil {

    private static AppDataUtil single_instance = null;
    public static String appJsonURL = "app.json";
    private ArrayList<App> appData;
    private Map<String, List<Lesson>> myCategory = null;


    public static AppDataUtil getInstance()
    {
        if (single_instance == null)
            single_instance = new AppDataUtil();

        return single_instance;
    }

    public Map<String, List<Lesson>> getMyCategoryList(Context ctx){
        if(null == myCategory){
            myCategory = getCategoryList(ctx);
        }
        return myCategory;
    }

    public ArrayList<Lesson> getMyCategoryListByName(Context ctx, String category){
        if(null == myCategory){
            myCategory = getCategoryList(ctx);
        }
        ArrayList<Lesson> list = (ArrayList<Lesson>) myCategory.get(category);
        return list;
    }

    private Map<String, List<Lesson>> getCategoryList(Context ctx){
        myCategory = new HashMap<>();
        for(App currentX : getAppList(ctx)) {
            if(!currentX.getTitle().startsWith("Header-")){
                ArrayList<Lesson> lessons = currentX.getLesson();
                for(Lesson lesson : lessons) {
                    if(null != lesson.getTag()){
                        String[] tags = lesson.getTag().split(";");
                        addCategory(tags, lesson);
                    }
                }
            }
        }
        return myCategory;
    }



    private void addCategory(String name[], Lesson lesson){
        for (String s: name) {
            addListbyCategoryName(s, lesson);
        }
    }

    private void addListbyCategoryName(String name, Lesson lesson){
        List<Lesson> lessonList = myCategory.get(name);
        if(null == lessonList){
            lessonList = new ArrayList<>();
            lessonList.add(lesson);
            myCategory.put(name,lessonList);
        }else{
            lessonList.add(lesson);
            myCategory.put(name,lessonList);
        }

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
