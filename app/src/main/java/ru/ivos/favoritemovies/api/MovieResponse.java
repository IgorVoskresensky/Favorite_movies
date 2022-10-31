package ru.ivos.favoritemovies.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.ivos.favoritemovies.entities.Movie;

public class MovieResponse {

    @SerializedName("docs")
    private List<Movie> movies;

    public MovieResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
