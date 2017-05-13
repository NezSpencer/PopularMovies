package com.nezspencer.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by nezspencer on 5/8/17.
 */

public class LocalMovieProvider extends ContentProvider {

    private LocalMovieDatabase db;
    private static UriMatcher MATCHER;
    private static final int MOVIES = 100;
    private static final int MOVIE_WITH_ID = 101;
    private static final int REVIEWS = 200;
    private static final int REVIEWS_WITH_ID = 201;
    private static final int TRAILERS = 300;
    private static final int TRAILERS_WITH_ID = 301;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        MATCHER.addURI(LocalMovieContract.AUTHORITY,LocalMovieContract.PATH_MOVIES,MOVIES);
        MATCHER.addURI(LocalMovieContract.AUTHORITY,LocalMovieContract.PATH_MOVIES +"/#",MOVIE_WITH_ID);
        MATCHER.addURI(LocalMovieContract.AUTHORITY,LocalMovieContract.PATH_REVIEWS,REVIEWS);
        MATCHER.addURI(LocalMovieContract.AUTHORITY,LocalMovieContract.PATH_REVIEWS+"/#",REVIEWS_WITH_ID);
        MATCHER.addURI(LocalMovieContract.AUTHORITY,LocalMovieContract.PATH_TRAILERS,TRAILERS);
        MATCHER.addURI(LocalMovieContract.AUTHORITY,LocalMovieContract.PATH_TRAILERS+"/#",
                TRAILERS_WITH_ID);
    }
    @Override
    public boolean onCreate() {
        db = new LocalMovieDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        final SQLiteDatabase database = db.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (MATCHER.match(uri)){

            case MOVIES:
                builder.setTables(LocalMovieContract.FavoriteMovies.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = LocalMovieContract.FavoriteMovies.SORT_DEFAULT;
                break;

            case MOVIE_WITH_ID:
                builder.setTables(LocalMovieContract.FavoriteMovies.TABLE_NAME);
                builder.appendWhere(LocalMovieContract.FavoriteMovies._ID+ " = "+
                        uri.getLastPathSegment());
                break;

            case REVIEWS:
                builder.setTables(LocalMovieContract.Reviews.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = LocalMovieContract.Reviews.SORT_DEFAULT;
                break;

            case REVIEWS_WITH_ID:
                builder.setTables(LocalMovieContract.Reviews.TABLE_NAME);
                builder.appendWhere(LocalMovieContract.Reviews._ID+ " = "+
                        uri.getLastPathSegment());
                break;

            case TRAILERS:
                builder.setTables(LocalMovieContract.Trailers.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = LocalMovieContract.Trailers.SORT_DEFAULT;
                break;

            case TRAILERS_WITH_ID:
                builder.setTables(LocalMovieContract.Trailers.TABLE_NAME);
                builder.appendWhere(LocalMovieContract.Trailers._ID+ " = "+
                        uri.getLastPathSegment());
                break;


            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }
        cursor = builder.query(database,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri))
        {
            case MOVIES:
                return LocalMovieContract.FavoriteMovies.CONTENT_TYPE;

            case MOVIE_WITH_ID:
                return LocalMovieContract.FavoriteMovies.CONTENT_ITEM_TYPE;

            case REVIEWS:
                return LocalMovieContract.Reviews.CONTENT_TYPE;

            case REVIEWS_WITH_ID:
                return LocalMovieContract.Reviews.CONTENT_ITEM_TYPE;

            case TRAILERS:
                return LocalMovieContract.Trailers.CONTENT_TYPE;

            case TRAILERS_WITH_ID:
                return LocalMovieContract.Trailers.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;
        final SQLiteDatabase database = db.getWritableDatabase();
        Uri returnUri;

        switch (MATCHER.match(uri)){

            case MOVIES:
                id = database.insert(LocalMovieContract.FavoriteMovies.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0){
                    //correct
                    returnUri = ContentUris.withAppendedId(uri,id);
                }
                else throw new SQLException("Unable to insert row into "+uri);
                break;


            case REVIEWS:
                id = database.insert(LocalMovieContract.Reviews.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0)
                {
                    returnUri = ContentUris.withAppendedId(uri,id);
                }
                else throw new SQLException("Unable to insert row into "+uri);
                break;

            case TRAILERS:
                id = database.insert(LocalMovieContract.Trailers.TABLE_NAME,
                        null,
                        contentValues);

                if (id > 0){

                    returnUri = ContentUris.withAppendedId(uri,id);
                }

                else throw new SQLException("Unable to insert row into "+uri);
                break;

                default:
                    throw new UnsupportedOperationException("Unknown Uri "+uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] selectionArgs) {

        int numOfRowsDeleted;
        final SQLiteDatabase database = db.getWritableDatabase();
        String idStr;
        String where;

        switch (MATCHER.match(uri)){

            case MOVIES:
                numOfRowsDeleted = database.delete(LocalMovieContract.FavoriteMovies.TABLE_NAME,
                        whereClause,
                        selectionArgs);
                break;

            case MOVIE_WITH_ID:
                idStr = uri.getLastPathSegment();
                where = LocalMovieContract.FavoriteMovies._ID + " = "+ idStr;
                if (!TextUtils.isEmpty(whereClause))
                    where += " AND "+ whereClause;

                numOfRowsDeleted = database.delete(LocalMovieContract.FavoriteMovies.TABLE_NAME,
                        where,selectionArgs);
                break;

            case REVIEWS:
                numOfRowsDeleted = database.delete(LocalMovieContract.Reviews.TABLE_NAME,
                        whereClause,selectionArgs);
                break;

            case REVIEWS_WITH_ID:
                idStr = uri.getLastPathSegment();
                where = LocalMovieContract.Reviews._ID +" = "+ idStr;
                if (!TextUtils.isEmpty(whereClause)){
                    where += " AND "+whereClause;
                }
                numOfRowsDeleted = database.delete(LocalMovieContract.Reviews.TABLE_NAME,
                        where,
                        selectionArgs);
                break;

            case TRAILERS:
                numOfRowsDeleted = database.delete(LocalMovieContract.Trailers.TABLE_NAME,
                        whereClause,
                        selectionArgs);
                break;

            case TRAILERS_WITH_ID:
                idStr = uri.getLastPathSegment();
                where = LocalMovieContract.Trailers._ID +" = "+ idStr;
                if (!TextUtils.isEmpty(whereClause)){
                    where += " AND "+ whereClause;
                }

                numOfRowsDeleted = database.delete(LocalMovieContract.Trailers.TABLE_NAME,
                        where,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }

        if (numOfRowsDeleted > 0)
            getContext().getContentResolver().notifyChange(uri,null);
        return numOfRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String
            selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase database = db.getWritableDatabase();
        int numberOfRowsUpdated;
        String idStr;
        String where;

        switch (MATCHER.match(uri)){

            case MOVIES:
                numberOfRowsUpdated = database.update(LocalMovieContract.FavoriteMovies.TABLE_NAME,
                        contentValues,selection,selectionArgs);
                break;

            case MOVIE_WITH_ID:
                idStr = uri.getLastPathSegment();
                where = LocalMovieContract.FavoriteMovies._ID +" = "+ idStr;
                if (!TextUtils.isEmpty(selection))
                    where += " AND "+ selection;

                numberOfRowsUpdated = database.update(LocalMovieContract.FavoriteMovies.TABLE_NAME,
                        contentValues,
                        where,
                        selectionArgs);
                break;

            case REVIEWS:
                numberOfRowsUpdated = database.update(LocalMovieContract.Reviews.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;

            case REVIEWS_WITH_ID:
                idStr = uri.getLastPathSegment();
                where = LocalMovieContract.Reviews._ID +" = "+ idStr;

                if (!TextUtils.isEmpty(selection))
                    where += " AND "+selection;
                numberOfRowsUpdated = database.update(LocalMovieContract.Reviews.TABLE_NAME,
                        contentValues,
                        where,
                        selectionArgs);
                break;

            case TRAILERS:
                numberOfRowsUpdated = database.update(LocalMovieContract.Trailers.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;

            case TRAILERS_WITH_ID:
                idStr = uri.getLastPathSegment();
                where = LocalMovieContract.Trailers._ID +" = "+ idStr;

                if (!TextUtils.isEmpty(selection))
                    where += " AND "+selection;
                numberOfRowsUpdated = database.update(LocalMovieContract.Trailers.TABLE_NAME,
                        contentValues,
                        where,
                        selectionArgs);

            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return numberOfRowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase database = db.getWritableDatabase();
        int numberOfRowsInserted =0;

        switch (MATCHER.match(uri)){

            case MOVIES:
                database.beginTransaction();



                    try {
                        for (ContentValues value : values) {
                            long id = database.insert(LocalMovieContract.FavoriteMovies.TABLE_NAME,
                                    null,
                                    value);
                            if (id != -1)
                                numberOfRowsInserted++;
                        }
                        database.setTransactionSuccessful();
                    }
                    finally {
                        database.endTransaction();
                    }

                    if (numberOfRowsInserted > 0)
                        getContext().getContentResolver().notifyChange(uri,null);
                return numberOfRowsInserted;

            case REVIEWS:
                database.beginTransaction();



                try {
                    for (ContentValues value : values) {
                        long id = database.insert(LocalMovieContract.Reviews.TABLE_NAME,
                                null,
                                value);
                        if (id != -1)
                            numberOfRowsInserted++;
                    }
                    database.setTransactionSuccessful();
                }
                finally {
                    database.endTransaction();
                }

                if (numberOfRowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri,null);
                return numberOfRowsInserted;

            case TRAILERS:
                database.beginTransaction();



                try {
                    for (ContentValues value : values) {
                        long id = database.insert(LocalMovieContract.Trailers.TABLE_NAME,
                                null,
                                value);
                        if (id != -1)
                            numberOfRowsInserted++;
                    }
                    database.setTransactionSuccessful();
                }
                finally {
                    database.endTransaction();
                }

                if (numberOfRowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri,null);
                return numberOfRowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }

    }
}
