package com.nezspencer.popularmovies;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 4/13/17.
 */

public class MovieDetailPage extends AppCompatActivity {

    @Bind(R.id.movie_image)ImageView moviePoster;
    @Bind(R.id.tv_title)TextView movieTitleTextView;
    @Bind(R.id.tv_release_date)TextView movieReleaseTextView;
    @Bind(R.id.tv_rating_score)TextView movieRatingTextView;
    @Bind(R.id.tv_summary)TextView movieSummaryTextView;
    @Bind(R.id.tv_synopsis)TextView synopsisTextView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        MovieDatabaseResults movie;
        String baseUrl= "http://image.tmdb.org/t/p/w185/";
        if (getIntent().hasExtra("movie"))
        {
            movie = getIntent().getParcelableExtra("movie");
            String url = baseUrl+movie.getPoster_path();
            Glide.with(this).load(url)
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .into(moviePoster);


            movieReleaseTextView.setText(formatDate(movie.getRelease_date()));
            movieRatingTextView.setText(String.valueOf(movie.getVote_average()));
            movieSummaryTextView.setText(movie.getOverview());
            movieTitleTextView.setText(movie.getOriginal_title());
        }

        setFontToTextViews();



    }

    public void setFontToTextViews(){
        movieReleaseTextView.setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf"));
        movieTitleTextView.setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf"));
        movieSummaryTextView.setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf"));
        movieRatingTextView.setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf"));
        synopsisTextView.setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf"));
    }

    public static String formatDate(String dateString){
         String [] dates = dateString.split("-");
        String month;

        switch (dates[1]){
            case "01":
                month ="January";
                break;
            case "02":
                month="February";
                break;
            case "03":
                month ="March";
                break;
            case "04":
                month="April";
                break;
            case "05":
                month ="May";
                break;
            case "06":
                month="June";
                break;
            case "07":
                month ="July";
                break;
            case "08":
                month="August";
                break;
            case "09":
                month ="September";
                break;
            case "10":
                month="October";
                break;
            case "11":
                month ="November";
                break;
            case "12":
                month="December";
                break;
            default:
                month="";

        }

        return dates[2]+" "+month+" "+dates[0];
    }
}
