package com.nezspencer.popularmovies.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nezspencer.popularmovies.Constants;
import com.nezspencer.popularmovies.R;
import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 4/13/17.
 */

public class MovieDetailPage extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
DetailContract {



    @Bind(R.id.detail_view_pager)ViewPager viewPager;
    @Bind(R.id.detail_tab_layout)TabLayout movieTabLayout;
    private MoviePagerAdapter moviePagerAdapter;
    private DetailPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        presenter = new DetailPresenter(this);

        if (getIntent().hasExtra(Constants.KEY_DETAIL_MOVIE))
        {
            Bundle bundle = getIntent().getBundleExtra(Constants.KEY_DETAIL_MOVIE);
            setUpViewPager(bundle);
        }


    }

    public void setUpViewPager(Bundle bundle){
        movieTabLayout.addTab(movieTabLayout.newTab().setText(getString(R.string.overview)));
        movieTabLayout.addTab(movieTabLayout.newTab().setText(R.string.trailer_review));
        movieTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(),movieTabLayout
                .getTabCount(), bundle);
        movieTabLayout.setOnTabSelectedListener(this);

        viewPager.setAdapter(moviePagerAdapter);
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
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onLoadFinished(ArrayList<PreviewResults> trailers, ArrayList<MovieReviewResults> reviews) {

    }

    @Override
    public void showError(String message) {

    }

    public static class MoviePagerAdapter extends FragmentStatePagerAdapter {

        int count;
        Bundle bundle;
        public MoviePagerAdapter(FragmentManager fm, int tabCount, Bundle bundle) {
            super(fm);
            count = tabCount;
            this.bundle = bundle;

        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    MovieOverviewFragment overviewFragment = new MovieOverviewFragment();
                    overviewFragment.setArguments(bundle);
                    return overviewFragment;
                case 1:
                    ExtraDetailsFragment extraDetailsFragment = new ExtraDetailsFragment();
                    extraDetailsFragment.setArguments(bundle);
                    return extraDetailsFragment;

            }
            return null;
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}


