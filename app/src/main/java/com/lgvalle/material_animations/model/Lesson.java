package com.lgvalle.material_animations.model;

import android.graphics.drawable.Drawable;

/**
 * Created by anand on 05/08/2018.
 */

public class Lesson {

    public Lesson(String mFrenchWord,String mEngloishWord,Drawable mImage){
        frenchWord = mFrenchWord;
        englishWord = mEngloishWord;
        image = mImage;
    }
    public String getFrenchWord() {
        return frenchWord;
    }

    public void setFrenchWord(String frenchWord) {
        this.frenchWord = frenchWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    String frenchWord;
    String englishWord;
    Drawable image;
}
