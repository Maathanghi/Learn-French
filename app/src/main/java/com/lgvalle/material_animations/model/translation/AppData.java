
package com.lgvalle.material_animations.model.translation;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
