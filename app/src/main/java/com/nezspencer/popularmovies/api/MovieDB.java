package com.nezspencer.popularmovies.api;

import com.nezspencer.popularmovies.pojo.AvailableGenre;
import com.nezspencer.popularmovies.pojo.MovieDatabase;
import com.nezspencer.popularmovies.pojo.MovieReviewResults;
import com.nezspencer.popularmovies.pojo.PreviewResults;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nezspencer on 4/13/17.
 */

public interface MovieDB {


    /*https://api.themoviedb.org/3/movie*/
    @GET("{sort}")
    Observable<MovieDatabase> getMovies(@Path("sort") String sortOrder, @Query("api_key")String apiKey);

    @GET("genre/movie/list")
    Observable<AvailableGenre> getGenres(@Query("api_key")String apiKey);

    @GET("movie/{id}/videos")
    Observable<PreviewResults> getTrailers(@Path("id") String id, @Query("api_key") String apikey);

    @GET("movie/{id}/reviews")
    Observable<MovieReviewResults> getReviews(@Path("id") String id, @Query("api_key") String apikey);

}
