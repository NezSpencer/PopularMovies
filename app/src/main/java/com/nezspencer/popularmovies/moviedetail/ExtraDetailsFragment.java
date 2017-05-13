package com.nezspencer.popularmovies.moviedetail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nezspencer.popularmovies.GlobalApp;
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

    public static ExtraDetailsFragment mInstance;



    private MovieTrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_details,container,false);
        ButterKnife.bind(this,view);

        mInstance = this;

            String backdropUrl = GlobalApp.imageBaseUrl + GlobalApp.movieItem.getBackdrop_path();
            Glide.with(this).load(backdropUrl).error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .into(backdropImage);

        initializeViews();
        return view;
    }

    public void initializeViews(){

        trailerAdapter = new MovieTrailerAdapter(getActivity(),MovieDetailPage.mInstance.trailerList);
        reviewAdapter = new ReviewAdapter(MovieDetailPage.mInstance.reviewResultList);

        trailerRecycler.setAdapter(trailerAdapter);
        reviewRecycler.setAdapter(reviewAdapter);

    }

    public static Bitmap getBackdropBitmap(){
        return ((BitmapDrawable)mInstance.backdropImage.getDrawable()).getBitmap();
    }
}
