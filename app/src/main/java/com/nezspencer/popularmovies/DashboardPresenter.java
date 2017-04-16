package com.nezspencer.popularmovies;

import android.util.Log;

import com.nezspencer.popularmovies.api.MovieDB;
import com.nezspencer.popularmovies.pojo.MovieDatabase;
import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.ArrayList;
import java.util.Arrays;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nezspencer on 4/14/17.
 */

public class DashboardPresenter {

    private MovieContract.MovieDashboard dashboard;

    /**Insert your Api key for MovieDB here**/
    private static final String API_KEY="489a8a13513ae376d847aa187080cb30";


    private Observer<MovieDatabaseResults[]> movieListObserver;
    public DashboardPresenter(MovieContract.MovieDashboard movieDashboard) {
        dashboard =movieDashboard;
        movieListObserver = new Observer<MovieDatabaseResults[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e !=null && e.getMessage() != null)
                {
                    dashboard.showError(e.getMessage());
                    e.printStackTrace();
                    Log.e("error",e.getMessage());
                }

            }

            @Override
            public void onNext(MovieDatabaseResults[] movieResults) {
                dashboard.displayLoadedMovies(new ArrayList<MovieDatabaseResults>(Arrays.asList(movieResults)));
            }
        };
    }

    public void getMovies(String sortCategory){
        dashboard.showLoadingProgress();
        String sortOrder;
        if (sortCategory.equalsIgnoreCase("top rated"))
        {
            sortOrder ="top_rated";
        }
        else {
            sortOrder = "popular";
        }

        InjectionClass.getRetrofit().create(MovieDB.class).getMovies(sortOrder,API_KEY)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<MovieDatabase, MovieDatabaseResults[]>() {
                    @Override
                    public MovieDatabaseResults[] call(MovieDatabase movieDatabase) {
                        return movieDatabase.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieListObserver);
    }
}
