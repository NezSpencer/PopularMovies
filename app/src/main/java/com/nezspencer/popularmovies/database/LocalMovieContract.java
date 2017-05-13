package com.nezspencer.popularmovies.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nezspencer on 5/6/17.
 */

public class LocalMovieContract {

    public static final String AUTHORITY="com.nezspencer.popularmovies";
    public static final String PATH_MOVIES = FavoriteMovies.TABLE_NAME;
    public static final String PATH_REVIEWS = Reviews.TABLE_NAME;
    public static final String PATH_TRAILERS = Trailers.TABLE_NAME;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final class FavoriteMovies implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES)
                .build();


        public static final String TABLE_NAME="favorites";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_GENRE = "genre_ids";
        public static final String COLUMN_IMAGE_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_RATING = "vote_average";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String SORT_DEFAULT = COLUMN_TITLE+" ASC";

    }

    public static final class Reviews implements BaseColumns{


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS)
                .build();


        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_REVIEWID = "reviewId";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String SORT_DEFAULT = COLUMN_AUTHOR+" ASC";
    }

    public static final class Trailers implements BaseColumns{


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS)
                .build();

        public static final String TABLE_NAME = "trailers";
        public static final String COLUMN_TRAILERID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd."+AUTHORITY+"."+TABLE_NAME;

        public static final String SORT_DEFAULT = COLUMN_TRAILERID+" ASC";
    }
}
