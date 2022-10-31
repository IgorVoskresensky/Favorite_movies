package ru.ivos.favoritemovies;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.ivPosterMI);
        double rating = movie.getRating().getKp();
        int backId;
        if(rating > 7){
            backId = R.drawable.cyrcle_green;
        } else if (rating > 4){
            backId = R.drawable.cyrcle_orange;
        } else {
            backId = R.drawable.cyrcle_red;
        }
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backId);
        holder.tvRatingMI.setBackground(drawable);
        holder.tvRatingMI.setText(String.valueOf(rating));

        if(position >= movieList.size() - 10 && onReachEndListener != null){
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onMovieClickListener != null) {
                    onMovieClickListener.onClickMovie(movie);
                    Log.d("tag",String.valueOf(movie.getId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    interface OnReachEndListener {

        void onReachEnd();
    }

    interface OnMovieClickListener {
        void onClickMovie(Movie movie);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivPosterMI;
        private final TextView tvRatingMI;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPosterMI = itemView.findViewById(R.id.ivPosterMI);
            tvRatingMI = itemView.findViewById(R.id.tvRatingMI);
        }
    }
}
