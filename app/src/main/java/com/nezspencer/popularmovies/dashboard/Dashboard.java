package com.nezspencer.popularmovies.dashboard;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nezspencer.popularmovies.GlobalApp;
import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.database.LocalMovieContract;
import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Dashboard extends AppCompatActivity implements DashboardContract.MovieDashboard,
        LoaderManager.LoaderCallbacks<ArrayList<MovieDatabaseResults>>{

    @Bind(R.id.rv_movie_list)RecyclerView movieListRecycler;
    @Bind(R.id.toolbar)Toolbar toolbar;
    private ProgressDialog progressDialog;

    private MovieListAdapter movieListAdapter;
    private ArrayList<MovieDatabaseResults> movieList;
    private static DashboardPresenter presenter;
    private static String sortOrdering="popular";

    private static final int QUERY_LOADER = 102;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        presenter = new DashboardPresenter(this,getString(R.string.API_KEY));
        GlobalApp.shouldDisplayFavoriteMovies= false;


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        presenter.getGenres();
        presenter.getMovies(sortOrdering);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        progressDialog.show();

        movieList =new ArrayList<>();
        movieListAdapter =new MovieListAdapter(this,movieList);
        movieListRecycler.setAdapter(movieListAdapter);
    }

    @Override
    public void displayLoadedMovies(ArrayList<MovieDatabaseResults> movies) {
        movieList.clear();
        movieList.addAll(movies);
        movieListAdapter.notifyDataSetChanged();
        Log.e("checker",""+ movies.toString());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public static void initiateSort(String sortOrder){
        presenter.getMovies(sortOrder);
        sortOrdering = sortOrder;
    }

    @Override
    public void showLoadingProgress() {
        if (progressDialog !=null && !progressDialog.isShowing())
            progressDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.sort_popularity)
        {
            initiateSort("popular");
            GlobalApp.shouldDisplayFavoriteMovies = false;
            return true;
        }

        else if (item.getItemId() == R.id.sort_top_rated)
        {
            GlobalApp.shouldDisplayFavoriteMovies = false;
            initiateSort("top rated");
            return true;
        }

        else if (item.getItemId() == R.id.favourite)
        {
            GlobalApp.shouldDisplayFavoriteMovies = true;
            presenter.showFavoriteMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }


    @Override
    public void fetchFavoritesFromDB() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<MovieDatabaseResults>> loader = loaderManager.getLoader(QUERY_LOADER);

        if (loader == null)
            loader = loaderManager.initLoader(QUERY_LOADER,null,this);
        else
            loader = loaderManager.restartLoader(QUERY_LOADER,null,this);

    }

    @Override
    public Loader<ArrayList<MovieDatabaseResults>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieDatabaseResults>>(Dashboard.this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressDialog.setMessage("fetching movies from db");
                showLoadingProgress();
                forceLoad();
            }

            @Override
            public ArrayList<MovieDatabaseResults> loadInBackground() {
                return queryDb();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieDatabaseResults>> loader, ArrayList<MovieDatabaseResults> data) {

        dismissProgress();
        displayLoadedMovies(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieDatabaseResults>> loader) {

    }

    public ArrayList<MovieDatabaseResults> queryDb(){

        MovieDatabaseResults movie;
        ArrayList<MovieDatabaseResults> savedMovies = new ArrayList<>();

        Cursor cursor = getContentResolver().query(
                LocalMovieContract.FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                LocalMovieContract.FavoriteMovies.SORT_DEFAULT
        );

        while (cursor.moveToNext()){
            movie = new MovieDatabaseResults();
            int id = cursor.getInt(cursor.getColumnIndex(LocalMovieContract.FavoriteMovies
                    .COLUMN_MOVIE_ID));
            String overview = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_OVERVIEW));
            String title = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_TITLE));
            String movieGenre = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_GENRE));
            String date = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_DATE));
            String language = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_LANGUAGE));
            double rating = cursor.getDouble(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_RATING));
            int voteCount = cursor.getInt(cursor.getColumnIndex(LocalMovieContract.FavoriteMovies
                    .COLUMN_VOTE_COUNT));
            String backdrop = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_BACKDROP_PATH));
            String poster = cursor.getString(cursor.getColumnIndex(LocalMovieContract
                    .FavoriteMovies.COLUMN_IMAGE_PATH));

            //set values to movieItem
            movie.setId(id);
            movie.setOverview(overview);
            movie.setOriginal_title(title);
            movie.setRelease_date(date);
            movie.setOriginal_language(language);
            movie.setVote_average(rating);
            movie.setVote_count(voteCount);
            movie.setBackdrop_path(backdrop);
            movie.setPoster_path(poster);
            movie.setTitle(movieGenre);

            savedMovies.add(movie);
        }

        return savedMovies;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgress();
    }
}

