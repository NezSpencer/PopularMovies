package com.nezspencer.popularmovies;

import com.nezspencer.popularmovies.pojo.MovieDatabase;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nezspencer on 4/13/17.
 */

public interface MovieDB {

    /*api key = 489a8a13513ae376d847aa187080cb30*/
    /*https://api.themoviedb.org/3/movie*/
    @GET("{sort}?api_key=489a8a13513ae376d847aa187080cb30")
    Observable<MovieDatabase> getMovies(@Path("sort") String sortOrder);


}
