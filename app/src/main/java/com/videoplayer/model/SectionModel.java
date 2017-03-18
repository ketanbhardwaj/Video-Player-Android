package com.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ketan on 3/17/17.
 */

public class SectionModel implements Parcelable {

    private String name;

    public SectionModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected SectionModel(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<SectionModel> CREATOR = new Parcelable.Creator<SectionModel>() {
        @Override
        public SectionModel createFromParcel(Parcel in) {
            return new SectionModel(in);
        }

        @Override
        public SectionModel[] newArray(int size) {
            return new SectionModel[size];
        }
    };


}
