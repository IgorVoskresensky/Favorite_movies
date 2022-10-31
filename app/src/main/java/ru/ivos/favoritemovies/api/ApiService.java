package ru.ivos.favoritemovies.api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String TOKEN = "7FES654-3WQMVAK-NA7ZW66-Q87BCG0";

    @GET("movie?token=7FES654-3WQMVAK-NA7ZW66-Q87BCG0&field=rating.kp&search=3-8&sortField=votes.kp&sortType=-1&limit=30")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie?token=7FES654-3WQMVAK-NA7ZW66-Q87BCG0&field=id")
    Single<TrailerResponse> loadTrailers(@Query("search") int id);

    @GET("review?token=7FES654-3WQMVAK-NA7ZW66-Q87BCG0&field=movieId")
    Single<ReviewResponse> loadReview(@Query("search") int id);
}
