package com.nezspencer.popularmovies;

import android.app.Application;
import android.content.Context;
import android.util.SparseArray;

import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;
import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nezspencer on 5/4/17.
 */

public class GlobalApp extends Application {

    public static final String imageBaseUrl= "http://image.tmdb.org/t/p/w500/";
    public static MovieDatabaseResults movieItem = new MovieDatabaseResults();
    private static GlobalApp context;
    public static ArrayList<PreviewResults> trailers = new ArrayList<>();
    public static ArrayList<MovieReviewResults> reviews = new ArrayList<>();

    public static boolean shouldDisplayFavoriteMovies;

    private static CompositeSubscription subscriptions = new CompositeSubscription();

    private static SparseArray<String> genreMap=new SparseArray<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static void setGenreMap(SparseArray<String> sparseArray){
        genreMap = sparseArray;
    }
    public static String getGenre(int key){
        return genreMap.get(key);
    }

    public static SparseArray<String> getGenres(){
        return genreMap;
    }

    public static CompositeSubscription getSubscriptions() {
        return subscriptions;
    }

    public static void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }

    public static Context getContext() {
        return context;
    }


}
