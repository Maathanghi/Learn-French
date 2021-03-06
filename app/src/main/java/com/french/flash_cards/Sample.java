package com.french.flash_cards;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class Sample implements Serializable {

    final int color;
    int index = 0;
    int status = 0;
    String total;
    private final String name;
    private static int LOCKED = 0;
    private static int INPROGRESS = 1;
    private static int DONE = 2;

    public Sample(int color, String name,int index, int iconStatus, String total) {
        this.color = color;
        this.name = name;
        this.index = index;
        this.status = iconStatus;
        this.total = total;
    }

    @BindingAdapter("bind:colorTint")
    public static void setColorTint(ImageView view,  int color) {
        DrawableCompat.setTint(view.getDrawable(), color);
        //view.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public String getName() {
        return name;
    }

    public String getTotal() {
        return total;
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
