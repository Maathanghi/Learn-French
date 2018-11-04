
package com.learn_french.common.fulldialog.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AppData {

    @SerializedName("app")
    @Expose
    private ArrayList<App> app = null;

    public ArrayList<App> getApp() {
        return app;
    }

    public void setApp(ArrayList<App> app) {
        this.app = app;
    }

}
