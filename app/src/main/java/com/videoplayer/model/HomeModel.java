package com.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.videoplayer.model.response.MovieResultResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ketan on 3/17/17.
 */

public class HomeModel implements Parcelable {

    private int type;

    private SectionModel sectionModel;

    private List<MovieResultResponse> movieModelList;

    public HomeModel(int type, SectionModel sectionModel, List<MovieResultResponse> movieModelList) {
        this.type = type;
        this.sectionModel = sectionModel;
        this.movieModelList = movieModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SectionModel getSectionModel() {
        return sectionModel;
    }

    public void setSectionModel(SectionModel sectionModel) {
        this.sectionModel = sectionModel;
    }

    public List<MovieResultResponse> getMovieModelList() {
        return movieModelList;
    }

    public void setMovieModelList(List<MovieResultResponse> movieModelList) {
        this.movieModelList = movieModelList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected HomeModel(Parcel in) {
        type = in.readInt();
        sectionModel = in.readParcelable(SectionModel.class.getClassLoader());
        in.readList(movieModelList, null);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeParcelable(sectionModel, i);
        parcel.writeList(movieModelList);
    }

    public static final Parcelable.Creator<HomeModel> CREATOR = new Parcelable.Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };

}
