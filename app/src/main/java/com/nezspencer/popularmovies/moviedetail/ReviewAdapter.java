package com.nezspencer.popularmovies.moviedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.pojo.MovieReviewResults;

import java.util.ArrayList;

/**
 * Created by nezspencer on 5/4/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private ArrayList<MovieReviewResults> reviewResults;
    public ReviewAdapter(ArrayList<MovieReviewResults> results) {
        reviewResults = results;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review,
                parent,false);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        String firstLetter = reviewResults.get(position).getAuthor().substring(0,1);
        holder.circledTextView.setText(firstLetter);
        holder.reviewerNameTextView.setText(reviewResults.get(position).getAuthor());
        holder.reviewTextView.setText(reviewResults.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewResults.size();
    }

    static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView circledTextView;
        TextView reviewerNameTextView;
        TextView reviewTextView;
        public ReviewHolder(View itemView) {
            super(itemView);

            circledTextView = (TextView) itemView.findViewById(R.id.tv_circle_text1);
            reviewerNameTextView = (TextView) itemView.findViewById(R.id.tv_reviewer_name);
            reviewTextView = (TextView) itemView.findViewById(R.id.tv_review);
        }
    }
}
