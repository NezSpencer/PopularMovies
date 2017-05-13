package com.nezspencer.popularmovies.dashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nezspencer.popularmovies.Constants;
import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.moviedetail.MovieDetailPage;
import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.ArrayList;

/**
 * Created by nezspencer on 4/14/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ArrayList<MovieDatabaseResults> movies;
    private static String baseUrl= "http://image.tmdb.org/t/p/w185";
    private Context context;
    public MovieListAdapter(Context context,ArrayList<MovieDatabaseResults> movieList) {
        movies = movieList;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item,
                parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        /*baseUrl+movies.get(position).getPoster_path();*/
        String imageUrl = Uri.parse(baseUrl).buildUpon().appendEncodedPath(movies.get(position)
                .getPoster_path()).toString();
        Log.e("checker",imageUrl);
        Glide.with(context).load(imageUrl)
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent =new Intent(context,MovieDetailPage.class);
                detailIntent.putExtra(Constants.KEY_DETAIL_MOVIE,movies.get(position));
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("checker",""+movies.size());
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image_poster);


        }
    }
}
