
package com.lgvalle.material_animations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("posterUri")
    @Expose
    private String posterUri;
    @SerializedName("overview")
    @Expose
    private String overview;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
