package com.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ketan on 3/17/17.
 */

public class MovieModel implements Parcelable {

    private String title;

    private String banner;

    public MovieModel(String title, String banner) {
        this.title = title;
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        banner = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(banner);
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

}
