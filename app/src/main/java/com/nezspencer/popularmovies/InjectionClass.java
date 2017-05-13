package com.nezspencer.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nezspencer on 4/14/17.
 */

public class InjectionClass {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static SharedPreferences sharedPreferences;
    private static Context context;

    public static Retrofit getRetrofit(){
        if (retrofit == null)
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
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

    public static Context getAppContext(){
        if (context == null)
            context = GlobalApp.getContext();
        return context;
    }

    public static SharedPreferences getSharedPreferences(){

        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences;
    }

    public static void saveToPreferences(String key, Object value){

        if (value instanceof String)
        {
            getSharedPreferences().edit()
                    .putString(key,(String)value)
                    .apply();
        }
    }

    public static String getStringFromPreferences(String key){
        return getSharedPreferences().getString(key,null);
    }


}
