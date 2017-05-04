package com.nezspencer.popularmovies.moviedetail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nezspencer.popularmovies.Constants;
import com.nezspencer.popularmovies.GlobalApp;
import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 5/2/17.
 */

public class MovieOverviewFragment extends Fragment {

    @Bind(R.id.rb_user_rating)RatingBar ratingBar;
    @Bind(R.id.movie_image)ImageView moviePoster;
    @Bind(R.id.tv_title)TextView movieTitleTextView;
    @Bind(R.id.tv_release_date)TextView movieReleaseTextView;
    @Bind(R.id.tv_summary)TextView movieSummaryTextView;
    @Bind(R.id.tv_genre)TextView movieGenre;
    @Bind(R.id.tv_synopsis)TextView synopsisTextView;
    @Bind(R.id.tv_rating_score)TextView ratingAverage;
    @Bind(R.id.tv_language)TextView originalLanguage;
    @Bind(R.id.tv_number_pple_rated)TextView ratingFrequency; //number of pple that voted

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_detail,container,false);

        ButterKnife.bind(this,view);

        MovieDatabaseResults movie;
        String baseUrl= "http://image.tmdb.org/t/p/w500/";
        if (getArguments() != null && getArguments().containsKey(Constants.KEY_DETAIL_MOVIE))
        {
            Bundle bundle = getArguments().getBundle(Constants.KEY_DETAIL_MOVIE);
            movie = bundle.getParcelable(Constants.KEY_DETAIL_MOVIE);
            String url = baseUrl+movie.getPoster_path();
            Glide.with(this).load(url)
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .into(moviePoster);


            movieReleaseTextView.setText(formatDate(movie.getRelease_date()));
            ratingAverage.setText(String.valueOf(movie.getVote_average()));
            movieSummaryTextView.setText(movie.getOverview());
            movieTitleTextView.setText(movie.getOriginal_title());
            ratingBar.setRating((float) movie.getVote_average());
            ratingFrequency.setText(movie.getVote_count());
            originalLanguage.setText(new Locale(movie.getOriginal_language()).getDisplayLanguage());
            movieGenre.setText("");
            for (int id: movie.getGenre_ids()){
                movieGenre.append(GlobalApp.getGenre(id));

                if (movie.getGenre_ids()[movie.getGenre_ids().length - 1] != id)
                    movieGenre.append(", ");

            }

        }

        return  view;
    }

    public void setFontToTextViews(){
        movieReleaseTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Regular.ttf"));
        movieTitleTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Regular.ttf"));
        movieSummaryTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Light.ttf"));
        ratingAverage.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Regular.ttf"));
        synopsisTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Regular.ttf"));
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
