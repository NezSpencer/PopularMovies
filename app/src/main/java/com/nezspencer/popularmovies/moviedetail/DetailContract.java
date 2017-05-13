package com.nezspencer.popularmovies.moviedetail;

import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;

/**
 * Created by nezspencer on 5/4/17.
 */

public interface DetailContract {

    void startLoadingProgress();

    void stopLoadingProgress();

    void onDataFetch(ArrayList<PreviewResults> trailers, ArrayList<MovieReviewResults> reviews);

    void showError(String message);

    void startQuery();
}
