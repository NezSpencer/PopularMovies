package com.nezspencer.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nezspencer on 5/8/17.
 */

public class LocalMovieDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="popularmovies.db";
    private static final int DATABASE_VERSION=1;

    public LocalMovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE "+LocalMovieContract.FavoriteMovies.TABLE_NAME+
                " ( "+LocalMovieContract.FavoriteMovies._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                LocalMovieContract.FavoriteMovies.COLUMN_TITLE+" TEXT NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_MOVIE_ID+" INTEGER NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_OVERVIEW+" TEXT NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_LANGUAGE+" TEXT NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_GENRE+" TEXT NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_RATING+" REAL NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_VOTE_COUNT+" INTEGER NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_IMAGE_PATH+" TEXT NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_BACKDROP_PATH+" TEXT NOT NULL, "+
                LocalMovieContract.FavoriteMovies.COLUMN_DATE+" TEXT NOT NULL );";

        String SQL_CREATE_TABLE_REVIEW = "CREATE TABLE "+LocalMovieContract.Reviews.TABLE_NAME+
                " ( "+LocalMovieContract.Reviews._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                LocalMovieContract.Reviews.COLUMN_AUTHOR+" TEXT NOT NULL, "+
                LocalMovieContract.Reviews.COLUMN_MOVIE_ID+" INTEGER NOT NULL, "+
                LocalMovieContract.Reviews.COLUMN_REVIEWID+" TEXT NOT NULL, "+
                LocalMovieContract.Reviews.COLUMN_CONTENT+" TEXT NOT NULL, "+
                LocalMovieContract.Reviews.COLUMN_URL+" TEXT NOT NULL );";

        String SQL_CREATE_TABLE_TRAILER = "CREATE TABLE "+LocalMovieContract.Trailers.TABLE_NAME+
                " ( "+LocalMovieContract.Trailers._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                LocalMovieContract.Trailers.COLUMN_KEY+" TEXT NOT NULL, "+
                LocalMovieContract.Trailers.COLUMN_MOVIE_ID+" INTEGER NOT NULL, "+
                LocalMovieContract.Trailers.COLUMN_TRAILERID+" TEXT NOT NULL, "+
                LocalMovieContract.Trailers.COLUMN_TYPE+" TEXT NOT NULL );";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_REVIEW);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TRAILER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+LocalMovieContract.FavoriteMovies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+LocalMovieContract.Reviews.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+LocalMovieContract.Trailers.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
