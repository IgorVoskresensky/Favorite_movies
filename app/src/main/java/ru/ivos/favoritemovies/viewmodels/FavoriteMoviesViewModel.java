package ru.ivos.favoritemovies.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.ivos.favoritemovies.db.Dao;
import ru.ivos.favoritemovies.entities.Movie;
import ru.ivos.favoritemovies.db.MovieDatabase;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private final Dao dao;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        dao = MovieDatabase.getInstance(application).getDao();
    }

    public LiveData<List<Movie>> getMovie() {
        return dao.getAllFavoriteMovies();
    }
}
