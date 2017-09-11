package com.example.admin.karsol_ano_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 27-04-2017.
 */
public class SubTitle implements Parcelable {

    private String name;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubTitle(String name) {
        this.name = name;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public SubTitle(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<SubTitle> CREATOR = new Creator<SubTitle>() {
        @Override
        public SubTitle createFromParcel(Parcel source) {
            return new SubTitle(source);
        }

        @Override
        public SubTitle[] newArray(int size) {
            return new SubTitle[size];
        }
    };
}