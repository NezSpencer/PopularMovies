package com.nezspencer.popularmovies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Dashboard extends AppCompatActivity implements MovieContract.MovieDashboard{

    @Bind(R.id.rv_movie_list)RecyclerView movieListRecycler;
    private ProgressDialog progressDialog;

    private MovieListAdapter movieListAdapter;
    private ArrayList<MovieDatabaseResults> movieList;
    private static DashboardPresenter presenter;
    private static String sortOrdering="popular";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        presenter = new DashboardPresenter(this);

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
        progressDialog.dismiss();
        movieList.clear();
        movieList.addAll(movies);
        movieListAdapter.notifyDataSetChanged();
        Log.e("checker",""+ movies.toString());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
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
            return true;
        }

        else if (item.getItemId() == R.id.sort_top_rated)
        {
            initiateSort("top rated");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
