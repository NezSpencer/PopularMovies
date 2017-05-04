package com.nezspencer.popularmovies.moviedetail;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;

/**
 * Created by nezspencer on 5/4/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieHolder> {

    private static final String BASE_YOUTUBE_URL="http://www.youtube.com/watch";
    private ArrayList<PreviewResults> previews;
    public MovieTrailerAdapter(ArrayList<PreviewResults> previews) {
        this.previews = previews;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trailer_item,
                parent,false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

        Uri videoUri= Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendQueryParameter("v",previews.get(position).getKey())
                .build();
        holder.trailerVideo.setVideoURI(videoUri);
        holder.trailerVideo.start();
    }

    @Override
    public int getItemCount() {
        return previews.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder{

        VideoView trailerVideo;

        public MovieHolder(View itemView) {
            super(itemView);
            trailerVideo = (VideoView) itemView.findViewById(R.id.vv_trailer);

        }
    }
}
