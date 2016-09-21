package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ksmai on 2016-01-21.
 */
public class ProductSize implements Parcelable {

    private int key;
    private int product;
    private String size;
    private boolean soldout;


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isSoldout() {
        return soldout;
    }

    public void setSoldout(boolean soldout) {
        this.soldout = soldout;
    }


    public static final Creator<ProductSize> CREATOR = new Creator<ProductSize>() {
        @Override
        public ProductSize createFromParcel(Parcel in) {
            return new ProductSize(in);
        }

        @Override
        public ProductSize[] newArray(int size) {
            return new ProductSize[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    private ProductSize(Parcel in) {

        key = in.readInt();
        product = in.readInt();
        size = in.readString();
        soldout = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(key);
        dest.writeInt(product);
        dest.writeString(size);
        dest.writeByte((byte) (soldout ? 1 : 0));
    }
}
