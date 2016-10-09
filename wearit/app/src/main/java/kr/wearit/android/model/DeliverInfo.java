package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-02-18.
 */
public class DeliverInfo implements Parcelable {

    private int brand;
    private int deliverPrice;
    private int basis;

    public DeliverInfo() {
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getDeliverPrice() {
        return deliverPrice;
    }

    public void setDeliverPrice(int deliverPrice) {
        this.deliverPrice = deliverPrice;
    }

    public int getBasis() {
        return basis;
    }

    public void setBasis(int basis) {
        this.basis = basis;
    }
    //

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(brand);
        dest.writeInt(deliverPrice);
        dest.writeInt(basis);
    }

    private DeliverInfo(Parcel in) {
        brand = in.readInt();
        deliverPrice = in.readInt();
        basis = in.readInt();
    }

    public static final Creator<DeliverInfo> CREATOR = new Creator<DeliverInfo>() {

        @Override
        public DeliverInfo createFromParcel(Parcel in) {
            return new DeliverInfo(in);
        }

        @Override
        public DeliverInfo[] newArray(int size) {
            return new DeliverInfo[size];
        }
    };
}