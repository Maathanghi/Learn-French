
package com.learn_french.common.fulldialog.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lesson implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageUri")
    @Expose
    private String imageUri;
    @SerializedName("frenchTranslation")
    @Expose
    private String frenchTranslation;
    @SerializedName("englishTranslation")
    @Expose
    private String englishTranslation;
    @SerializedName("tag")
    @Expose
    private String tag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }

    public void setFrenchTranslation(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public void setEnglishTranslation(String englishTranslation) {
        this.englishTranslation = englishTranslation;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
