package com.lgvalle.material_animations;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

/**
 * Created by lgvalle on 04/09/15.
 */
public class Sample implements Serializable {

    final int color;
    int index = 0;
    static int status = 0;
    private final String name;
    private  boolean iconStatus = false;
    private static int LOCKED = 0;
    private static int INPROGRESS = 1;
    private static int DONE = 2;

    public Sample(int color, String name,int index, int status) {
        this.color = color;
        this.name = name;
        this.index = index;
        this.status = status;
    }

    @BindingAdapter("bind:colorTint")
    public static void setColorTint(ImageView view,  int color) {
        DrawableCompat.setTint(view.getDrawable(), color);
        //view.setColorFilter(color, PorterDuff.Mode.SRC_IN);
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

    public static boolean getStatus(int reqStatus) {
        if(status == reqStatus){
            return true;
        }
        return false;
    }

    public boolean isLocked() {
        iconStatus = getStatus(LOCKED);
        return iconStatus;
    }

    public boolean isProgress() {
        iconStatus = getStatus(INPROGRESS);
        return iconStatus;
    }

    public boolean isDone() {
        iconStatus = getStatus(DONE);
        return iconStatus;
    }



}
