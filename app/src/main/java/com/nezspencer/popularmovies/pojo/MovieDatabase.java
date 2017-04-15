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

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public MovieDatabaseResults[] getResults() {
        return this.results;
    }

    public void setResults(MovieDatabaseResults[] results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
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
