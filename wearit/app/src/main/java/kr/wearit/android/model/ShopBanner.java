package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-01-04.
 */
public class ShopBanner  implements Parcelable {
    private int key;
    private int shop;
    private String image_path;
    private String shop_name;

    public void setKey(int key){
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public int getShop(){
        return this.shop;
    }

    public void setImage_path(String image_path){
        this.image_path = image_path;
    }

    public String getImage_path(){
        return this.image_path;
    }

    public void setShop_name(String shop_name){
        this.shop_name = shop_name;
    }

    public String getShop_name(){
        return this.shop_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(shop);

        dest.writeString(image_path);
        dest.writeString(shop_name);

    }

    private ShopBanner(Parcel in) {
        key = in.readInt();
        shop = in.readInt();

        image_path = in.readString();
        shop_name = in.readString();
    }

    public static final Creator<ShopBanner> CREATOR = new Creator<ShopBanner>() {

        @Override
        public ShopBanner createFromParcel(Parcel in) {
            return new ShopBanner(in);
        }

        @Override
        public ShopBanner[] newArray(int size) {
            return new ShopBanner[size];
        }
    };
}