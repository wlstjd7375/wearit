package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015-12-29.
 */
public class BrandBanner implements Parcelable {
    private int key;
    private int brand;
    private String image_path;
    private String thumb_image_path;

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getBrand() {
        return this.brand;
    }

    public void setImage_path(String image_path){
        this.image_path = image_path;
    }

    public String getImage_path(){
        return this.image_path;
    }

    public void setThumb_image_path(String thumb_image_path){
        this.thumb_image_path = thumb_image_path;
    }

    public String getThumb_image_path(){
        return this.thumb_image_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(brand);
        dest.writeString(image_path);
        dest.writeString(thumb_image_path);
    }

    private BrandBanner(Parcel in) {
        key = in.readInt();
        brand = in.readInt();
        image_path = in.readString();
        thumb_image_path = in.readString();
    }

    public static final Creator<BrandBanner> CREATOR = new Creator<BrandBanner>() {

        @Override
        public BrandBanner createFromParcel(Parcel in) {
            return new BrandBanner(in);
        }

        @Override
        public BrandBanner[] newArray(int size) {
            return new BrandBanner[size];
        }
    };
}
