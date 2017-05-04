package com.nezspencer.popularmovies;

import android.app.Application;
import android.util.SparseArray;

/**
 * Created by nezspencer on 5/4/17.
 */

public class GlobalApp extends Application {

    private static SparseArray<String> genreMap=new SparseArray<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setGenreMap(SparseArray<String> sparseArray){
        genreMap = sparseArray;
    }
    public static String getGenre(int key){
        return genreMap.get(key);
    }
}
