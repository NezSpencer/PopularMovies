package com.nezspencer.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AvailableGenre implements Parcelable {
    public static final Creator<AvailableGenre> CREATOR = new Creator<AvailableGenre>() {
        @Override
        public AvailableGenre createFromParcel(Parcel source) {
            AvailableGenre var = new AvailableGenre();
            var.genres = source.createTypedArray(AvailableGenreGenres.CREATOR);
            return var;
        }

        @Override
        public AvailableGenre[] newArray(int size) {
            return new AvailableGenre[size];
        }
    };
    private AvailableGenreGenres[] genres;

    public AvailableGenreGenres[] getGenres() {
        return this.genres;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.genres, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
