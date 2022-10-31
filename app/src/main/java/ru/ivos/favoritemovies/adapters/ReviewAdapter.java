package ru.ivos.favoritemovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ivos.favoritemovies.R;
import ru.ivos.favoritemovies.entities.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private static final String POSITIVE_TYPE = "Позитивный";
    private static final String NEGATIVE_TYPE = "Негативный";
    private static final String NEUTRAL_TYPE = "Нейтральный";

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.tvAuthorRI.setText(review.getAuthor());
        holder.tvReviewRI.setText(review.getReview());

        String type = review.getType();
        int backId = android.R.color.holo_green_light;
        switch (type){
            case NEUTRAL_TYPE:
                backId = android.R.color.holo_orange_light;
                break;
            case NEGATIVE_TYPE:
                backId = android.R.color.holo_red_light;
                break;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), backId);
        holder.llRI.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout llRI;
        private TextView tvAuthorRI;
        private TextView tvReviewRI;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            llRI = itemView.findViewById(R.id.llRI);
            tvAuthorRI = itemView.findViewById(R.id.tvAuthorRI);
            tvReviewRI = itemView.findViewById(R.id.tvReviewRI);
        }
    }
}
