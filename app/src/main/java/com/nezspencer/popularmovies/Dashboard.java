package com.nezspencer.popularmovies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.nezspencer.popularmovies.pojo.MovieDatabaseResults;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dashboard extends AppCompatActivity implements MovieContract.MovieDashboard{

    @Bind(R.id.fab_sort)FloatingActionButton sortListFab;
    @Bind(R.id.rv_movie_list)RecyclerView movieListRecycler;
    private ProgressDialog progressDialog;

    private MovieListAdapter movieListAdapter;
    private ArrayList<MovieDatabaseResults> movieList;
    private static DashboardPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        presenter = new DashboardPresenter(this);

        presenter.getMovies(App.SortOrder);
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

    @OnClick(R.id.fab_sort)
    public void selectSortType(){
        SortCategoryFragment.newInstance(App.SortOrder).show(getSupportFragmentManager(),"sort");
    }

    public static void initiateSort(String sortOrder){
        presenter.getMovies(sortOrder);
        App.SortOrder = sortOrder;
    }

    @Override
    public void showLoadingProgress() {
        if (progressDialog !=null && !progressDialog.isShowing())
            progressDialog.show();

    }
}
