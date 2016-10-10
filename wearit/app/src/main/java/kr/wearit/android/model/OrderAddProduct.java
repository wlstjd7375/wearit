package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-10-10.
 */

public class OrderAddProduct implements Parcelable {
    int key;
    int order;
    ArrayList<Product> products;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(order);
        dest.writeTypedList(products);
    }

    private OrderAddProduct(Parcel in) {
        key = in.readInt();
        order = in.readInt();
        in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);
    }

    public static final Creator<OrderAddProduct> CREATOR = new Creator<OrderAddProduct>() {

        @Override
        public OrderAddProduct createFromParcel(Parcel in) {
            return new OrderAddProduct(in);
        }

        @Override
        public OrderAddProduct[] newArray(int size) {
            return new OrderAddProduct[size];
        }
    };
}
