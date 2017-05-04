package com.nezspencer.popularmovies.moviedetail;

import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;

/**
 * Created by nezspencer on 5/4/17.
 */

public interface DetailContract {

    void startLoading();

    void stopLoading();

    void onLoadFinished(ArrayList<PreviewResults> trailers, ArrayList<MovieReviewResults> reviews);

    void showError(String message);
}
