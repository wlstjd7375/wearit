package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ksmai on 2016-02-10.
 */
public class Phone implements Parcelable {

    private String phonenum;
    private String email;
    private String userid;
    private String price;
    private int order;
    private String height;
    private String productname;

    public Phone() {
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return userid;
    }

    public void setUser(String userid) {
        this.userid = userid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }


    //

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phonenum);
        dest.writeString(email);
        dest.writeString(userid);
        dest.writeString(price);
        dest.writeInt(order);
        dest.writeString(height);
        dest.writeString(productname);
    }

    private Phone(Parcel in) {
        phonenum = in.readString();
        email = in.readString();
        userid = in.readString();
        price = in.readString();
        order = in.readInt();
        height = in.readString();
        productname = in.readString();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {

        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };
}