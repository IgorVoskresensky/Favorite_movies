package ru.ivos.favoritemovies.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.ivos.favoritemovies.entities.Review;

public class ReviewResponse {
    @SerializedName("docs")
    private List<Review> reviewList;

    public ReviewResponse(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
