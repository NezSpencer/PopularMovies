package com.nezspencer.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AvailableGenreGenres implements Parcelable {
    public static final Creator<AvailableGenreGenres> CREATOR = new Creator<AvailableGenreGenres>() {
        @Override
        public AvailableGenreGenres createFromParcel(Parcel source) {
            AvailableGenreGenres var = new AvailableGenreGenres();
            var.name = source.readString();
            var.id = source.readInt();
            return var;
        }

        @Override
        public AvailableGenreGenres[] newArray(int size) {
            return new AvailableGenreGenres[size];
        }
    };
    private String name;
    private int id;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
