package ru.ivos.favoritemovies.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.ivos.favoritemovies.api.ApiFactory;
import ru.ivos.favoritemovies.db.Dao;
import ru.ivos.favoritemovies.entities.Movie;
import ru.ivos.favoritemovies.db.MovieDatabase;
import ru.ivos.favoritemovies.entities.Review;
import ru.ivos.favoritemovies.api.ReviewResponse;
import ru.ivos.favoritemovies.entities.Trailer;
import ru.ivos.favoritemovies.api.TrailerResponse;

public class MovieDetailViewModel extends AndroidViewModel {

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Dao dao;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        dao = MovieDatabase.getInstance(application).getDao();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public LiveData<Movie> getFavoriteMovie(int movieId){
        return dao.getFavoriteMovie(movieId);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void loadReviews(int id){
        Disposable disposable = ApiFactory.apiService.loadReview(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviewList();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviewsList) throws Throwable {
                        reviews.setValue(reviewsList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadTrailers(int id){
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailersList) throws Throwable {
                        trailers.setValue(trailersList);
                        Log.d("tag", trailersList.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    public void  insertMovie(Movie movie){
        Disposable disposable = dao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    public void  deleteMovie(int movieId){
        Disposable disposable = dao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();

        compositeDisposable.add(disposable);
    }
}
