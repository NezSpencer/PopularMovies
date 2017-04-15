package com.nezspencer.popularmovies;

import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.ArrayList;

/**
 * Created by nezspencer on 4/13/17.
 */

public interface MovieContract {

    interface MovieDashboard{

        void displayLoadedMovies(ArrayList<MovieDatabaseResults> movies);

        void showError(String message);

        void showLoadingProgress();


    }
}
