package com.learn_french.common.fulldialog.model;

import android.databinding.BindingAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import java.io.Serializable;


public class Sample implements Serializable {

    final int color;
    int index = 0;
    int status = 0;
    private final String name;
    private static int LOCKED = 0;
    private static int INPROGRESS = 1;
    private static int DONE = 2;

    public Sample(int color, String name, int index, int iconStatus) {
        this.color = color;
        this.name = name;
        this.index = index;
        this.status = iconStatus;
    }

    public String getName() {
        return name;
    }
    public String getIndex() {
        return index+"";
    }

    public int getColor() {
        return color;
    }

    public boolean getStatus(int reqStatus) {
        if(status == reqStatus){
            return true;
        }
        return false;
    }

    public boolean isLocked() {
        return  getStatus(LOCKED);
    }

    public boolean isProgress() {
        return  getStatus(INPROGRESS);
    }

    public boolean isDone() {
        return getStatus(DONE);
    }



}
