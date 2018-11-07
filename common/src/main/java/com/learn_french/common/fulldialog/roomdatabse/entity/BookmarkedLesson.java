package com.learn_french.common.fulldialog.roomdatabse.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "lesson")
public class BookmarkedLesson implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "image_uri")
    private String imageUri;
    @ColumnInfo(name = "french_ranslation")
    private String frenchTranslation;
    @ColumnInfo(name = "english_translation")
    private String englishTranslation;
    @ColumnInfo(name = "tag")
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

