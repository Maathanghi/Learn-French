package com.lgvalle.material_animations.utils;

import android.content.Context;

import com.lgvalle.material_animations.R;
import com.lgvalle.material_animations.model.Lesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 05/08/2018.
 */

public class AppConstants {

    // static variable single_instance of type Singleton
    private static AppConstants single_instance = null;
    List<Lesson> lesson1;
    List<Lesson> lesson2;
    List<Lesson> lesson3;

    // static method to create instance of Singleton class
    public static AppConstants getInstance()
    {
        if (single_instance == null)
            single_instance = new AppConstants();

        return single_instance;
    }

    public List<Lesson> getLevel1(Context ctx){
        lesson1 = new ArrayList<>();
        lesson1.add(new Lesson("bonjour","hello",ctx.getResources().getDrawable(R.drawable.level1_lesson1_apple)));
        lesson1.add(new Lesson("sava","okay?",ctx.getResources().getDrawable(R.drawable.level1_lesson2_elephant)));
        return lesson1;
    }

    public List<Lesson> getLevel2(Context ctx){
        lesson2 = new ArrayList<>();
        lesson2.add(new Lesson("bonjour","hello",ctx.getResources().getDrawable(R.drawable.circle_24dp)));
        lesson2.add(new Lesson("sava","okay?",ctx.getResources().getDrawable(R.drawable.circle_24dp)));
        lesson2.add(new Lesson("sava","okay?",ctx.getResources().getDrawable(R.drawable.circle_24dp)));
        return lesson2;
    }

    public List<Lesson> getLevel3(Context ctx){
        lesson3 = new ArrayList<>();
        lesson3.add(new Lesson("bonjour","hello",ctx.getResources().getDrawable(R.drawable.circle_24dp)));
        lesson3.add(new Lesson("sava","okay?",ctx.getResources().getDrawable(R.drawable.circle_24dp)));
        lesson3.add(new Lesson("sava","okay?",ctx.getResources().getDrawable(R.drawable.circle_24dp)));
        return lesson3;
    }



}
