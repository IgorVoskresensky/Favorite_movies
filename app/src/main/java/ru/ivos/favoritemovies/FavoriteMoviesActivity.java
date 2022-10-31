package ru.ivos.favoritemovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavoriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        RecyclerView rvFavoriteMoviesFM = findViewById(R.id.rvFavoriteMoviesFM);
        rvFavoriteMoviesFM.setLayoutManager(new GridLayoutManager(this, 2));
        MoviesAdapter adapter = new MoviesAdapter();
        rvFavoriteMoviesFM.setAdapter(adapter);

        adapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onClickMovie(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavoriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });

        FavoriteMoviesViewModel viewModel = new ViewModelProvider(this)
                .get(FavoriteMoviesViewModel.class);

        viewModel.getMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovieList(movies);
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavoriteMoviesActivity.class);
    }
}