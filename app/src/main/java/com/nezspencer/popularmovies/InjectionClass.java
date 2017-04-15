package com.nezspencer.popularmovies;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nezspencer on 4/14/17.
 */

public class InjectionClass {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static final String API_KEY="489a8a13513ae376d847aa187080cb30";

    public static Retrofit getRetrofit(){
        if (retrofit == null)
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public static OkHttpClient provideOkHttpClient(){
        if (okHttpClient == null)
            okHttpClient= new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
            .build();

        return okHttpClient;
    }


}
