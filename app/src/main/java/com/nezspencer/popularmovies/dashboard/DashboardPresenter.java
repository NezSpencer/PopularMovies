package com.nezspencer.popularmovies.dashboard;

import android.util.Log;
import android.util.SparseArray;

import com.nezspencer.popularmovies.GlobalApp;
import com.nezspencer.popularmovies.InjectionClass;
import com.nezspencer.popularmovies.api.MovieDB;
import com.nezspencer.popularmovies.pojo.AvailableGenre;
import com.nezspencer.popularmovies.pojo.AvailableGenreGenres;
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

    private DashboardContract.MovieDashboard dashboard;
    private boolean isGenreDone, isMoviesDone;


    private Observer<MovieDatabaseResults[]> movieListObserver;
    private Observer<AvailableGenreGenres[]> genreObserver;
    private String key;

    public DashboardPresenter(DashboardContract.MovieDashboard movieDashboard, String apikey) {
        dashboard =movieDashboard;
        key = apikey;
        movieListObserver = new Observer<MovieDatabaseResults[]>() {
            @Override
            public void onCompleted() {
                isMoviesDone = true;
                Log.e("LOGGER"," movies done");
                if (isMoviesDone && isGenreDone)
                {
                    dashboard.dismissProgress();
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e !=null && e.getMessage() != null)
                {
                    dashboard.showError(e.getMessage());
                    isMoviesDone = true;
                    if (isMoviesDone && isGenreDone)
                    {
                        dashboard.dismissProgress();
                    }

                    e.printStackTrace();
                    Log.e("error",e.getMessage());
                }

            }

            @Override
            public void onNext(MovieDatabaseResults[] movieResults) {
                dashboard.displayLoadedMovies(new ArrayList<MovieDatabaseResults>(Arrays.asList(movieResults)));
            }
        };


        genreObserver = new Observer<AvailableGenreGenres[]>(){
            @Override
            public void onCompleted() {
                isGenreDone = true;
                Log.e("LOGGER"," genre done");
                if (isMoviesDone && isGenreDone)
                {
                    dashboard.dismissProgress();
                }
            }

            @Override
            public void onError(Throwable e) {
                isGenreDone = true;
                Log.e("LOGGER"," genre error");
                if (isMoviesDone && isGenreDone)
                {
                    dashboard.dismissProgress();
                }

                dashboard.showError(e.getMessage());
            }

            @Override
            public void onNext(AvailableGenreGenres[] availableGenreGenres) {
                converToSparseArray(availableGenreGenres);
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

        InjectionClass.getRetrofit().create(MovieDB.class).getMovies(sortOrder,key)
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

    public void getGenres(){
        InjectionClass.getRetrofit().create(MovieDB.class).getGenres(key)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<AvailableGenre, AvailableGenreGenres[]>() {
                    @Override
                    public AvailableGenreGenres[] call(AvailableGenre availableGenre) {
                        return availableGenre.getGenres();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(genreObserver);
    }

    public void converToSparseArray(AvailableGenreGenres[] genres){

        SparseArray<String> sparseArray =new SparseArray<>();
        for (AvailableGenreGenres genre: genres){
            sparseArray.put(genre.getId(),genre.getName());
        }
        GlobalApp.setGenreMap(sparseArray);
    }

    public void showFavoriteMovies(){
        dashboard.fetchFavoritesFromDB();
    }
}
