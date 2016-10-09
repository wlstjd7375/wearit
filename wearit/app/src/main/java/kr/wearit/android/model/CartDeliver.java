package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-02-18.
 */
public class CartDeliver implements Parcelable {

    private ArrayList<ProductCart> cart;
    private ArrayList<DeliverInfo> deliverInfo;
    private ArrayList<DeliverInfo> visitDeliverInfo;

    public CartDeliver() {
    }

    public ArrayList<ProductCart> getCart() {
        return cart;
    }

    public void setCart(ArrayList<ProductCart> cart) {
        this.cart = cart;
    }

    public ArrayList<DeliverInfo> getVisitDeliverInfo() { return visitDeliverInfo; }

    public void setVisitDeliverInfo(ArrayList<DeliverInfo> visitDeliverInfo) {
        this.visitDeliverInfo = visitDeliverInfo;
    }

    public ArrayList<DeliverInfo> getDeliverInfo() {
        return deliverInfo;
    }

    public void setDeliverInfo(ArrayList<DeliverInfo> deliverInfo) {
        this.deliverInfo = deliverInfo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(cart);
        dest.writeTypedList(deliverInfo);
        dest.writeTypedList(visitDeliverInfo);
    }

    private CartDeliver(Parcel in) {
        in.readTypedList(cart = new ArrayList<ProductCart>(), ProductCart.CREATOR);
        in.readTypedList(deliverInfo = new ArrayList<DeliverInfo>(), DeliverInfo.CREATOR);
        in.readTypedList(visitDeliverInfo = new ArrayList<DeliverInfo>(), DeliverInfo.CREATOR);
    }

    public static final Creator<CartDeliver> CREATOR = new Creator<CartDeliver>() {

        @Override
        public CartDeliver createFromParcel(Parcel in) {
            return new CartDeliver(in);
        }

        @Override
        public CartDeliver[] newArray(int size) {
            return new CartDeliver[size];
        }
    };
}