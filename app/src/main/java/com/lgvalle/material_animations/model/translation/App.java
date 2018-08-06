
package com.lgvalle.material_animations.model.translation;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lesson")
    @Expose
    private List<Lesson> lesson = null;
    @SerializedName("quiz")
    @Expose
    private List<Quiz> quiz = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lesson> getLesson() {
        return lesson;
    }

    public void setLesson(List<Lesson> lesson) {
        this.lesson = lesson;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

}
