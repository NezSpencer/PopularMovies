package com.nezspencer.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDatabase implements Parcelable {
    public static final Creator<MovieDatabase> CREATOR = new Creator<MovieDatabase>() {
        @Override
        public MovieDatabase createFromParcel(Parcel source) {
            MovieDatabase var = new MovieDatabase();
            var.page = source.readInt();
            var.total_pages = source.readInt();
            var.results = source.createTypedArray(MovieDatabaseResults.CREATOR);
            var.total_results = source.readInt();
            return var;
        }

        @Override
        public MovieDatabase[] newArray(int size) {
            return new MovieDatabase[size];
        }
    };
    private int page;
    private int total_pages;
    private MovieDatabaseResults[] results;
    private int total_results;



    public MovieDatabaseResults[] getResults() {
        return this.results;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.total_pages);
        dest.writeTypedArray(this.results, flags);
        dest.writeInt(this.total_results);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
