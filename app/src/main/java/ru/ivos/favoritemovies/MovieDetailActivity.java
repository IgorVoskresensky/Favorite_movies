package ru.ivos.favoritemovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";

    private MovieDetailViewModel viewModel;

    private ImageView ivPosterMD;
    private TextView tvTitleMD;
    private TextView tvYearMD;
    private TextView tvDescMD;
    private ImageView ivStarMD;

    private RecyclerView recyclerView;
    private TrailersAdapter adapter;

    private RecyclerView rvReviewListMD;
    private ReviewAdapter reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        adapter = new TrailersAdapter();
        recyclerView.setAdapter(adapter);

        reviewAdapter = new ReviewAdapter();
        rvReviewListMD.setAdapter(reviewAdapter);

        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(ivPosterMD);
        tvTitleMD.setText(movie.getName());
//        tvYearMD.setText(movie.getYear());
        tvDescMD.setText(movie.getDescription());

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                Log.d("tag", trailers.toString());
                adapter.setTrailers(trailers);
            }
        });

        adapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        viewModel.loadReviews(movie.getId());
        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
                Log.d("tag", reviews.toString());
            }
        });

        Dao dao = MovieDatabase.getInstance(getApplication()).getDao();
        dao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {

                    }
                });
        Drawable starOff = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                android.R.drawable.star_big_on);

        viewModel.getFavoriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if (movieFromDb == null){
                    ivStarMD.setImageDrawable(starOff);
                    ivStarMD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewModel.insertMovie(movie);
                        }
                    });
                } else {
                    ivStarMD.setImageDrawable(starOn);
                    ivStarMD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewModel.deleteMovie(movie.getId());
                        }
                    });
                }
            }
        });

    }

    private void initViews() {
        ivPosterMD = findViewById(R.id.ivPosterMD);
        tvTitleMD = findViewById(R.id.tvTitleMD);
        tvYearMD = findViewById(R.id.tvYearMD);
        tvDescMD = findViewById(R.id.tvDescMD);
        recyclerView = findViewById(R.id.rvMovieListMD);
        rvReviewListMD = findViewById(R.id.rvReviewListMD);
        ivStarMD = findViewById(R.id.ivStarMD);
    }


    public static Intent newIntent(Context context, Movie  movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}