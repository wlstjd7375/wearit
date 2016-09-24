package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-07-06.
 */
public class ShopVisitInfo implements Parcelable {
    private int key;
    private int visit;

    public ShopVisitInfo() {
    }
    //
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(visit);
    }

    private ShopVisitInfo(Parcel in) {
        key = in.readInt();
        visit = in.readInt();
    }

    public static final Creator<ShopVisitInfo> CREATOR = new Creator<ShopVisitInfo>() {

        @Override
        public ShopVisitInfo createFromParcel(Parcel in) {
            return new ShopVisitInfo(in);
        }

        @Override
        public ShopVisitInfo[] newArray(int size) {
            return new ShopVisitInfo[size];
        }
    };

}