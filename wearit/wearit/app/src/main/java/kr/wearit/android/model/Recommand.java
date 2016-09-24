package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TAEK on 2016. 1. 13..
 */
public class Recommand implements Parcelable {
    private int key;
    private String recommand;

    public Recommand(){

    }

    public void setKey(int key){
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }

    public void setRecommand(String recommand){
        this.recommand = recommand;
    }

    public String getRecommand(){
        return this.recommand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeString(recommand);
    }

    private Recommand(Parcel in) {
        key = in.readInt();
        recommand = in.readString();
         }

    public static final Creator<Recommand> CREATOR = new Creator<Recommand>() {

        @Override
        public Recommand createFromParcel(Parcel in) {
            return new Recommand(in);
        }

        @Override
        public Recommand[] newArray(int size) {
            return new Recommand[size];
        }
    };
}

