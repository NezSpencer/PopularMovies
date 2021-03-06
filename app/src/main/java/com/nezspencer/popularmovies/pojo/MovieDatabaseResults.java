package com.nezspencer.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDatabaseResults implements Parcelable {
    public static final Creator<MovieDatabaseResults> CREATOR = new Creator<MovieDatabaseResults>() {
        @Override
        public MovieDatabaseResults createFromParcel(Parcel source) {
            MovieDatabaseResults var = new MovieDatabaseResults();
            var.overview = source.readString();
            var.original_language = source.readString();
            var.original_title = source.readString();
            var.video = source.readByte() != 0;
            var.title = source.readString();

            var.genre_ids = source.createIntArray();
            var.poster_path = source.readString();
            var.backdrop_path = source.readString();
            var.release_date = source.readString();
            var.popularity = source.readDouble();
            var.vote_average = source.readDouble();
            var.id = source.readInt();
            var.adult = source.readByte() != 0;
            var.vote_count = source.readInt();
            return var;
        }

        @Override
        public MovieDatabaseResults[] newArray(int size) {
            return new MovieDatabaseResults[size];
        }
    };
    private String overview;
    private String original_language;
    private String original_title;
    private boolean video;
    private String title;
    private int[] genre_ids;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private double popularity;
    private double vote_average;
    private int id;
    private boolean adult;
    private int vote_count;

    public String getOverview() {
        return this.overview;
    }

    public String getOriginal_title() {
        return this.original_title;
    }


    public String getPoster_path() {
        return this.poster_path;
    }

    public String getRelease_date() {
        return this.release_date;
    }


    public double getVote_average() {
        return this.vote_average;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeByte(video ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeIntArray(this.genre_ids);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.release_date);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.id);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeInt(this.vote_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getTitle() {
        return title;
    }
}
