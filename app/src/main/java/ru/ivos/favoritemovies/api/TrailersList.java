package ru.ivos.favoritemovies.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.ivos.favoritemovies.entities.Trailer;

public class TrailersList {

    @SerializedName("trailers")
    private List<Trailer> trailers;

    public TrailersList(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
