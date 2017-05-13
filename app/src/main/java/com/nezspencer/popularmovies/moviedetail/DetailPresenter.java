package com.nezspencer.popularmovies.moviedetail;

import com.nezspencer.popularmovies.GlobalApp;
import com.nezspencer.popularmovies.InjectionClass;
import com.nezspencer.popularmovies.api.MovieDB;
import com.nezspencer.popularmovies.pojo.MovieReview;
import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.Preview;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import java.util.ArrayList;
import java.util.Arrays;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nezspencer on 5/4/17.
 */

public class DetailPresenter {


    private Observer<PreviewResults[]> trailerObserver;
    private Observer<MovieReviewResults[]> reviewObserver;
    private DetailContract detailContract;

    private boolean reviewRequestDone, trailerRequestDone;

    private ArrayList<MovieReviewResults> reviews;
    private ArrayList<PreviewResults> trailers;

    public DetailPresenter(DetailContract detailContract) {
        this.detailContract = detailContract;
        trailers = new ArrayList<>();
        reviews = new ArrayList<>();
        initializeObservers();


    }

    public void initializeObservers(){

        trailerObserver = new Observer<PreviewResults[]>() {
            @Override
            public void onCompleted() {
                trailerRequestDone = true;

                if (reviewRequestDone && trailerRequestDone){
                    detailContract.onDataFetch(trailers,reviews);
                    detailContract.stopLoadingProgress();
                }

            }

            @Override
            public void onError(Throwable e) {
                detailContract.showError(e.getMessage());
                detailContract.stopLoadingProgress();
            }

            @Override
            public void onNext(PreviewResults[] previewResults) {
                trailers.clear();
                trailers.addAll(Arrays.asList(previewResults));
            }
        };

        reviewObserver = new Observer<MovieReviewResults[]>() {
            @Override
            public void onCompleted() {
                reviewRequestDone = true;

                if (reviewRequestDone && trailerRequestDone){
                    detailContract.onDataFetch(trailers,reviews);
                    detailContract.stopLoadingProgress();
                }
            }

            @Override
            public void onError(Throwable e) {
                detailContract.showError(e.getMessage());
            }

            @Override
            public void onNext(MovieReviewResults[] movieReviewResultses) {
                reviews.clear();
                reviews.addAll(Arrays.asList(movieReviewResultses));

            }
        };
    }

    public void makeNetworkRequest(String id){
        reviewRequestDone =false;
        trailerRequestDone = false;
        detailContract.startLoadingProgress();
        fetchReviews(id);
        fetchTrailers(id);
    }

    private void fetchTrailers(String id){
        InjectionClass.getRetrofit().create(MovieDB.class).getTrailers(id, GlobalApp.API_KEY)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Preview, PreviewResults[]>() {
                    @Override
                    public PreviewResults[] call(Preview preview) {

                        return preview.getResults();
                    }
                })
                .subscribe(trailerObserver);
    }

    private void fetchReviews(String id){
        InjectionClass.getRetrofit().create(MovieDB.class).getReviews(id,GlobalApp.API_KEY)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<MovieReview, MovieReviewResults[]>() {
                    @Override
                    public MovieReviewResults[] call(MovieReview movieReview) {
                        return movieReview.getResults();
                    }
                })
                .subscribe(reviewObserver);
    }

    public void queryLocalDB(){
        detailContract.startQuery();
    }
}
