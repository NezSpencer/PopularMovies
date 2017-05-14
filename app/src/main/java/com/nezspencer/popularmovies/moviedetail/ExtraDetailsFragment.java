package com.nezspencer.popularmovies.moviedetail;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
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
    @Bind(R.id.scroller)ScrollView scrollView;

    public static ExtraDetailsFragment mInstance;



    private MovieTrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_details,container,false);
        ButterKnife.bind(this,view);

        mInstance = this;
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        reviewRecycler.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        trailerRecycler.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

            String backdropUrl = GlobalApp.imageBaseUrl + GlobalApp.movieItem.getBackdrop_path();
            Glide.with(this).load(backdropUrl).error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .into(backdropImage);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("LOGGER"," vc");
        initializeViews();
    }

    public void initializeViews(){

        trailerAdapter = new MovieTrailerAdapter(getActivity(),
                MovieDetailPage.mInstance.trailerList.size() ==0?
                        GlobalApp.trailers :
                        MovieDetailPage.mInstance.trailerList);
        reviewAdapter = new ReviewAdapter(
                MovieDetailPage.mInstance.reviewResultList.size() == 0?
        GlobalApp.reviews: MovieDetailPage.mInstance.reviewResultList);
        Log.e("LOGGER"," trailer112 size is "+MovieDetailPage.mInstance.trailerList.size());

        trailerRecycler.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder.itemView.hasFocus())
                    holder.itemView.clearFocus();
            }
        });

        reviewRecycler.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder.itemView.hasFocus())
                    holder.itemView.clearFocus();
            }
        });
        trailerRecycler.setAdapter(trailerAdapter);
        reviewRecycler.setAdapter(reviewAdapter);

        /*trailerAdapter.updateList(MovieDetailPage.mInstance.trailerList);
        reviewAdapter.updateList(MovieDetailPage.mInstance.reviewResultList);*/


    }

    public static Bitmap getBackdropBitmap(){
        return ((BitmapDrawable)mInstance.backdropImage.getDrawable()).getBitmap();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.e("LOGGER"," config d");


        /*trailerRecycler.clearFocus();
        reviewRecycler.clearFocus();
        scrollView.clearFocus();*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        trailerRecycler.clearFocus();
        reviewRecycler.clearFocus();
        scrollView.clearFocus();
    }


}
