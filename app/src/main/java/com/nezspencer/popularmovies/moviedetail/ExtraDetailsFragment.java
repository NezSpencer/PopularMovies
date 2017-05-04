package com.nezspencer.popularmovies.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nezspencer.popularmovies.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 5/3/17.
 */

public class ExtraDetailsFragment extends Fragment {


    @Bind(R.id.movie_image2)ImageView backdropImage;
    @Bind(R.id.rv_trailer)RecyclerView trailerRecycler;
    @Bind(R.id.rv_review_list)RecyclerView reviewRecycler;
    @Bind(R.id.tv_click_more)TextView moreReviews;
    @Bind(R.id.fab_favorite)FloatingActionButton favFab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_details,container,false);

        ButterKnife.bind(this,view);
        return view;
    }
}
