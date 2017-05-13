package com.nezspencer.popularmovies.moviedetail;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.nezspencer.popularmovies.Constants;
import com.nezspencer.popularmovies.GlobalApp;
import com.nezspencer.popularmovies.InjectionClass;
import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.database.LocalMovieContract;
import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nezspencer on 4/13/17.
 */

public class MovieDetailPage extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
DetailContract, LoaderManager.LoaderCallbacks<String> {



    @Bind(R.id.detail_view_pager)ViewPager viewPager;
    @Bind(R.id.detail_tab_layout)TabLayout movieTabLayout;
    @Bind(R.id.fab_favorite)FloatingActionButton fabFavorite;
    @Bind(R.id.toolbar)Toolbar toolbar;


    private MoviePagerAdapter moviePagerAdapter;
    private DetailPresenter presenter;
    private ProgressDialog progressDialog;

    public  ArrayList<MovieReviewResults> reviewResultList;
    public static MovieDetailPage mInstance;
    public  ArrayList<PreviewResults> trailerList;


    public static final String KEY_PATH="pathss";
    public static final int SAVE_LOADER_ID = 10;
    public static final int DELETE_LOADER_ID = 20;
    public static final int QUERY_LOADER_ID = 30;
    private String key;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        mInstance = this;
        presenter = new DetailPresenter(this);

        setSupportActionBar(toolbar);




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        trailerList = new ArrayList<>();
        reviewResultList = new ArrayList<>();

        if (getIntent().hasExtra(Constants.KEY_DETAIL_MOVIE))
        {
            GlobalApp.movieItem = getIntent().getParcelableExtra(Constants.KEY_DETAIL_MOVIE);


            String id = String.valueOf(GlobalApp.movieItem.getId());

            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setTitle(GlobalApp.movieItem.getOriginal_title());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }


            checkIfSavedToDB();


            if (GlobalApp.shouldDisplayFavoriteMovies)
                presenter.queryLocalDB();
            else
            {
                if (trailerList.size() == 0 && reviewResultList.size() == 0)
                    presenter.makeNetworkRequest(id);
            }


        }


    }

    public void checkIfSavedToDB(){
        key = InjectionClass.getStringFromPreferences(""+GlobalApp.movieItem.getId());

        fabFavorite.setImageResource(key == null? R.drawable.ic_star_border_black_24dp
        : R.drawable.ic_star_black_24dp);
    }

    public void setUpViewPager(){
        movieTabLayout.removeAllTabs();
        movieTabLayout.addTab(movieTabLayout.newTab().setText(getString(R.string.overview)));
        movieTabLayout.addTab(movieTabLayout.newTab().setText(R.string.trailer_review));
        movieTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(),movieTabLayout
                .getTabCount());
        movieTabLayout.setOnTabSelectedListener(this);

        viewPager.setAdapter(moviePagerAdapter);
    }

    /*public String saveImageToPhone(Bitmap image, String name){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("popularMovies", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return mypath.getAbsolutePath();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
            return null;
        }
    }*/

    @OnClick(R.id.fab_favorite)
    public void makeFavorite(){

        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATH,GlobalApp.movieItem.getOriginal_title());
        Loader<String[]> favoriteLoader;
        LoaderManager loaderManager = getSupportLoaderManager();
        if (key == null)
        {
            Log.e("LOGGER"," fab saving data");
            favoriteLoader = loaderManager.getLoader(SAVE_LOADER_ID);
            if (favoriteLoader == null)
                loaderManager.initLoader(SAVE_LOADER_ID,bundle,this);
            else
                loaderManager.restartLoader(SAVE_LOADER_ID,bundle,this);
        }
        else {
            Log.e("LOGGER"," fab deleting data");
            favoriteLoader = loaderManager.getLoader(DELETE_LOADER_ID);

            if (favoriteLoader == null)
                loaderManager.initLoader(DELETE_LOADER_ID,bundle,this);
            else
                loaderManager.restartLoader(DELETE_LOADER_ID,bundle,this);
        }


    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void startLoadingProgress() {
        if (progressDialog!= null && !progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void stopLoadingProgress() {

        if (progressDialog!= null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onDataFetch(ArrayList<PreviewResults> trailers, ArrayList<MovieReviewResults> reviews) {
        trailerList = trailers;
        reviewResultList = reviews;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpViewPager();
            }
        });

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public Loader<String> onCreateLoader(final int id, final Bundle args) {
        return new AsyncTaskLoader<String>(MovieDetailPage.this) {


            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                String progressMessage;
                if (id == SAVE_LOADER_ID)
                {
                    progressMessage = "adding movie to favorites";
                }
                else if (id == QUERY_LOADER_ID){
                    progressMessage = "loading...";
                }
                else {
                    progressMessage = "deleting movie from favorites";
                }
                progressDialog.setMessage(progressMessage);
                /*startLoadingProgress();*/
                forceLoad();
            }

            @Override
            public String loadInBackground() {

                Log.e("LOGGER"," 111 saving to DB");
                if (id == SAVE_LOADER_ID)
                    saveToDB();
                else if (id == QUERY_LOADER_ID)
                    queryDB();
                else
                    deleteFromDB();
                Log.e("LOGGER"," save to DB completed");
                return GlobalApp.movieItem.getOriginal_title();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {


        stopLoadingProgress();
        if (data != null){
            if (loader.getId() == SAVE_LOADER_ID)
            {
                Toast.makeText(MovieDetailPage.this, data+" added to favorites",Toast.LENGTH_SHORT)
                        .show();
                fabFavorite.setImageResource(R.drawable.ic_star_black_24dp);
            }
            else if (loader.getId() == QUERY_LOADER_ID)
            {
                Toast.makeText(MovieDetailPage.this, " success!",Toast.LENGTH_SHORT)
                        .show();
                fabFavorite.setImageResource(R.drawable.ic_star_black_24dp);
            }

            else {
                Toast.makeText(MovieDetailPage.this, data+" deleted from favorites",Toast
                        .LENGTH_SHORT).show();
                fabFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        }

        else {
            Toast.makeText(MovieDetailPage.this, " failed! try again",Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    public void saveToDB(){

        Log.e("LOGGER"," saving to DB");

        ContentValues contentValues = new ContentValues();
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_TITLE,
            GlobalApp.movieItem.getOriginal_title());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_OVERVIEW,
            GlobalApp.movieItem.getOverview());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_DATE,
            GlobalApp.movieItem.getRelease_date());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_RATING,
            GlobalApp.movieItem.getVote_average());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_MOVIE_ID,
                GlobalApp.movieItem.getId());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_IMAGE_PATH,
            GlobalApp.movieItem.getPoster_path());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_VOTE_COUNT,
            GlobalApp.movieItem.getVote_count());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_BACKDROP_PATH,
            GlobalApp.movieItem.getBackdrop_path());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_LANGUAGE,
            GlobalApp.movieItem.getOriginal_language());
        contentValues.put(LocalMovieContract.FavoriteMovies.COLUMN_GENRE,MovieOverviewFragment.getGenre());

        getContentResolver().insert(LocalMovieContract.FavoriteMovies.CONTENT_URI,contentValues);
        Log.e("LOGGER"," saved movie to DB");

        ContentValues[] reviewList = new ContentValues[reviewResultList.size()];
        ContentValues reviewValue;

        for (int i =0; i < reviewResultList.size(); i++){
            reviewValue = new ContentValues();
            reviewValue.put(LocalMovieContract.Reviews.COLUMN_AUTHOR,reviewResultList.get(i).getAuthor());
            reviewValue.put(LocalMovieContract.Reviews.COLUMN_REVIEWID,reviewResultList.get(i).getId());
            reviewValue.put(LocalMovieContract.Reviews.COLUMN_CONTENT,reviewResultList.get(i).getContent());
            reviewValue.put(LocalMovieContract.Reviews.COLUMN_URL,reviewResultList.get(i).getUrl());
            reviewValue.put(LocalMovieContract.Reviews.COLUMN_MOVIE_ID,GlobalApp.movieItem.getId());

            reviewList[i] = reviewValue;
        }

        getContentResolver().bulkInsert(LocalMovieContract.Reviews.CONTENT_URI, reviewList);

        Log.e("LOGGER"," saved reviews to DB");

        ContentValues trailerValue;
        ContentValues[] listTrailer = new ContentValues[trailerList.size()];

        for (int i = 0; i < trailerList.size(); i++){
            trailerValue = new ContentValues();
            trailerValue.put(LocalMovieContract.Trailers.COLUMN_KEY, trailerList.get(i).getKey());
            trailerValue.put(LocalMovieContract.Trailers.COLUMN_TRAILERID,trailerList.get(i).getId());
            trailerValue.put(LocalMovieContract.Trailers.COLUMN_TYPE,trailerList.get(i).getType());
            trailerValue.put(LocalMovieContract.Trailers.COLUMN_MOVIE_ID,GlobalApp.movieItem.getId());

            listTrailer[i] = trailerValue;
        }

        getContentResolver().bulkInsert(LocalMovieContract.Trailers.CONTENT_URI, listTrailer);


        //save id to sharedPreferences
        InjectionClass.saveToPreferences(""+GlobalApp.movieItem.getId(),
                GlobalApp.movieItem.getOriginal_title());
        Log.e("LOGGER"," saved trailer to DB");

    }

    public void deleteFromDB(){

        Log.e("LOGGER"," deleting from DB");
        String where = LocalMovieContract.FavoriteMovies.COLUMN_MOVIE_ID+ " =? ";
        String trailerWhere = LocalMovieContract.Trailers.COLUMN_MOVIE_ID+ " =? ";
        String reviewWhere = LocalMovieContract.Reviews.COLUMN_MOVIE_ID+ " =? ";
        String[] args = new String []{""+GlobalApp.movieItem.getId()};

        getContentResolver().delete(
                LocalMovieContract.FavoriteMovies.CONTENT_URI,
                where,
                args
        );

        getContentResolver().delete(LocalMovieContract.Reviews.CONTENT_URI,
                reviewWhere,args);

        getContentResolver().delete(LocalMovieContract.Trailers.CONTENT_URI,
                trailerWhere,
                args);

        Log.e("LOGGER"," deleted from DB");
    }

    public void queryDB(){

        String trailerWhere = LocalMovieContract.Trailers.COLUMN_MOVIE_ID+ " =? ";
        String reviewWhere = LocalMovieContract.Reviews.COLUMN_MOVIE_ID+ " =? ";
        String[] args = new String []{""+GlobalApp.movieItem.getId()};

        Cursor reviewCursor = getContentResolver().query(
                LocalMovieContract.Reviews.CONTENT_URI,
                null,
                reviewWhere,
                args,
                LocalMovieContract.Reviews.SORT_DEFAULT
        );

        Cursor trailerCursor = getContentResolver().query(
                LocalMovieContract.Trailers.CONTENT_URI,
                null,
                trailerWhere,
                args,
                LocalMovieContract.Trailers.SORT_DEFAULT
        );

        MovieReviewResults review;
        ArrayList<MovieReviewResults> reviews = new ArrayList<>();
        while (reviewCursor.moveToNext()){

            review = new MovieReviewResults();
            String revId = reviewCursor.getString(reviewCursor.getColumnIndex(LocalMovieContract
                    .Reviews.COLUMN_REVIEWID));
            String author = reviewCursor.getString(reviewCursor.getColumnIndex(LocalMovieContract
                    .Reviews.COLUMN_AUTHOR));
            String content = reviewCursor.getString(reviewCursor.getColumnIndex
                    (LocalMovieContract.Reviews.COLUMN_CONTENT));
            String url = reviewCursor.getString(reviewCursor.getColumnIndex(LocalMovieContract
                    .Reviews.COLUMN_URL));

            review.setAuthor(author);
            review.setContent(content);
            review.setUrl(url);
            review.setId(revId);

            reviews.add(review);
        }

        PreviewResults preview;
        ArrayList<PreviewResults> previews = new ArrayList<>();

        while (trailerCursor.moveToNext()){

            preview = new PreviewResults();
            String key = trailerCursor.getString(trailerCursor.getColumnIndex(LocalMovieContract
                    .Trailers.COLUMN_KEY));
            String id = trailerCursor.getString(trailerCursor.getColumnIndex(LocalMovieContract
                    .Trailers.COLUMN_TRAILERID));
            String type = trailerCursor.getString(trailerCursor.getColumnIndex(LocalMovieContract
                    .Trailers.COLUMN_TYPE));

            preview.setId(id);
            preview.setType(type);
            preview.setKey(key);

            previews.add(preview);
        }

        onDataFetch(previews,reviews);

    }

    @Override
    public void startQuery() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATH,GlobalApp.movieItem.getOriginal_title());
        Loader<String[]> favoriteLoader;
        LoaderManager loaderManager = getSupportLoaderManager();
        favoriteLoader = loaderManager.getLoader(QUERY_LOADER_ID);
        if (favoriteLoader == null)
            loaderManager.initLoader(QUERY_LOADER_ID,bundle,this);
        else
            loaderManager.restartLoader(QUERY_LOADER_ID,bundle,this);
    }


}


