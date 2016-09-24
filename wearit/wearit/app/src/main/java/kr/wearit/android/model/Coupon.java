package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cho on 2015-12-31.
 */
public class Coupon implements Parcelable {

    private Integer key;
    private String name;
    private String id;
    private String description;
    private Integer status_usage;
    private String status_usage_str;
    private String date1;
    private String date2;
    private Integer status_date;
    private String status_date_str;
    private Integer min_cost;
    private Integer sale_cost;
    private Integer sale_rate;
    private String date;
    private ArrayList<Shop> shops;


    public Coupon(){

    }


    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus_usage() {
        return status_usage;
    }

    public void setStatus_usage(Integer status_usage) {
        this.status_usage = status_usage;
    }

    public String getStatus_usage_str() {
        return status_usage_str;
    }

    public void setStatus_usage_str(String status_usage_str) {
        this.status_usage_str = status_usage_str;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public Integer getStatus_date() {
        return status_date;
    }

    public void setStatus_date(Integer status_date) {
        this.status_date = status_date;
    }

    public String getStatus_date_str() {
        return status_date_str;
    }

    public void setStatus_date_str(String status_date_str) {
        this.status_date_str = status_date_str;
    }

    public Integer getMin_cost() {
        return min_cost;
    }

    public void setMin_cost(Integer min_cost) {
        this.min_cost = min_cost;
    }

    public Integer getSale_cost() {
        return sale_cost;
    }

    public void setSale_cost(Integer sale_cost) {
        this.sale_cost = sale_cost;
    }

    public Integer getSale_rate() {
        return sale_rate;
    }

    public void setSale_rate(Integer sale_rate) {
        this.sale_rate = sale_rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeInt(key);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(description);
        dest.writeInt(status_usage);
        dest.writeString(status_usage_str);
        dest.writeString(date1);
        dest.writeString(date2);
        dest.writeInt(status_date);
        dest.writeString(status_date_str);
        dest.writeInt(min_cost);
        dest.writeInt(sale_cost);
        dest.writeInt(sale_rate);
        dest.writeString(date);

        dest.writeTypedList(shops);
    }

    private Coupon(Parcel in) {


        key = in.readInt();
        name = in.readString();
        id = in.readString();
        description = in.readString();
        status_usage = in.readInt();
        status_usage_str = in.readString();
        date1 = in.readString();
        date2 = in.readString();
        status_date = in.readInt();
        status_date_str = in.readString();
        min_cost = in.readInt();
        sale_cost = in.readInt();
        sale_rate = in.readInt();
        date = in.readString();

        in.readTypedList(shops = new ArrayList<Shop>(), Shop.CREATOR);
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {

        @Override
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
