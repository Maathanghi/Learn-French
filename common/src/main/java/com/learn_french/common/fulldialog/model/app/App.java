
package com.learn_french.common.fulldialog.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class App implements Serializable {
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lesson")
    @Expose
    private ArrayList<Lesson> lesson = null;
    @SerializedName("quiz")
    @Expose
    private ArrayList<Quiz> quiz = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Lesson> getLesson() {
        return lesson;
    }

    public void setLesson(ArrayList<Lesson> lesson) {
        this.lesson = lesson;
    }

    public ArrayList<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(ArrayList<Quiz> quiz) {
        this.quiz = quiz;
    }

}
