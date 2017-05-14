package com.nezspencer.popularmovies.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private static ArrayList<PreviewResults> previews;
    private Context context;
    public MovieTrailerAdapter(Context context,ArrayList<PreviewResults> previews) {
        this.previews = previews;
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trailer_item,
                parent,false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

        final Uri videoUri= Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendQueryParameter("v",previews.get(position).getKey())
                .build();
        /*try {
            holder.trailerVideo.setVideoURI(videoUri);
        }
        catch (Exception e){

        }*/

        /*holder.trailerVideo.start();*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,videoUri);
                if (youtubeIntent.resolveActivity(context.getPackageManager()) != null)
                    context.startActivity(youtubeIntent);
            }
        });
    }

    public void updateList(ArrayList<PreviewResults> list){
        previews.clear();
        previews.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.e("LOGGER"," adapter trailer size = "+previews.size());
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
