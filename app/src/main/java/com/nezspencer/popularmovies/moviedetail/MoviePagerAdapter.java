package com.nezspencer.popularmovies.moviedetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by nezspencer on 5/11/17.
 */

public class MoviePagerAdapter extends FragmentStatePagerAdapter {

    int count;

    public MoviePagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        count = tabCount;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MovieOverviewFragment();
            case 1:
                return new ExtraDetailsFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (position == 0){
            MovieOverviewFragment fragment = (MovieOverviewFragment)super.instantiateItem
                    (container,position);
            return fragment;
        }
        else {
            ExtraDetailsFragment fragment = (ExtraDetailsFragment)super.instantiateItem
                    (container,position);
            return fragment;
        }
    }*/
}