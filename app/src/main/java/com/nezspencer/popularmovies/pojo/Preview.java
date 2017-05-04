package com.nezspencer.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Preview implements Parcelable {
    public static final Creator<Preview> CREATOR = new Creator<Preview>() {
        @Override
        public Preview createFromParcel(Parcel source) {
            Preview var = new Preview();
            var.id = source.readInt();
            var.results = source.createTypedArray(PreviewResults.CREATOR);
            return var;
        }

        @Override
        public Preview[] newArray(int size) {
            return new Preview[size];
        }
    };
    private int id;
    private PreviewResults[] results;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PreviewResults[] getResults() {
        return this.results;
    }

    public void setResults(PreviewResults[] results) {
        this.results = results;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedArray(this.results, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
