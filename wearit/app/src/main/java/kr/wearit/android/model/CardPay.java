package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-02-12.
 */

public class CardPay implements Parcelable {

    private int userid;

    private String paytype;
    private String price;
    private int ordernum;
    private String productname;

    public CardPay() {
    }

    public int getOrdernum() { return ordernum; }

    public void setOrdernum(int ordernum) { this.ordernum = ordernum; }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public int getUser() {
        return userid;
    }

    public void setUser(int userid) {
        this.userid = userid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        dest.writeInt(ordernum);
        dest.writeInt(userid);
        dest.writeString(paytype);
        dest.writeString(price);
        dest.writeString(productname);
    }

    private CardPay(Parcel in) {
        ordernum = in.readInt();
        userid = in.readInt();
        paytype = in.readString();
        price = in.readString();
        productname = in.readString();
    }

    public static final Creator<CardPay> CREATOR = new Creator<CardPay>() {

        @Override
        public CardPay createFromParcel(Parcel in) {
            return new CardPay(in);
        }

        @Override
        public CardPay[] newArray(int size) {
            return new CardPay[size];
        }
    };
}