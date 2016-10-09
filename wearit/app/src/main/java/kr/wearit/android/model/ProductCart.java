package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ksmai on 2016-01-21.
 */
public class ProductCart implements Parcelable {
    private int key;
    private int count;
    private String size;
    private String name;
    private boolean stock;
    private boolean sale;
    private int sale_rate;
    private int sale_price;
    private int deliverprice;
    private int price;
    private boolean best;
    private String imagepath;
    private String brandname;
    private int brand;
    private Date date;
    private boolean checked;
    private int product;

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public int getSale_rate() {
        return sale_rate;
    }

    public void setSale_rate(int sale_rate) {
        this.sale_rate = sale_rate;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getDeliverprice() { return deliverprice; }

    public void setDeliverprice(int deliverprice) { this.deliverprice = deliverprice; }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getBrand() {
        return brand;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(key);
        dest.writeInt(count);
        dest.writeString(size);
        dest.writeString(name);
        dest.writeInt(stock ? 1 : 0);
        dest.writeInt(sale ? 1 : 0);
        dest.writeInt(sale_rate);
        dest.writeInt(sale_price);
        dest.writeInt(price);
        dest.writeInt(deliverprice);
        dest.writeInt(best ? 1 : 0);
        dest.writeString(imagepath);
        dest.writeString(brandname);
        dest.writeInt(brand);
        dest.writeSerializable(date);
        dest.writeInt(product);
    }

    private ProductCart(Parcel in) {

        key = in.readInt();
        count = in.readInt();
        size= in.readString();
        name= in.readString();
        stock = in.readInt() == 1;
        sale = in.readInt() == 1;
        sale_rate = in.readInt();
        sale_price = in.readInt();
        price = in.readInt();
        deliverprice = in.readInt();
        best = in.readInt() == 1;
        imagepath= in.readString();
        brandname= in.readString();
        brand = in.readInt();
        date = (Date) in.readSerializable();
        product = in.readInt();

    }

    public static final Creator<ProductCart> CREATOR = new Creator<ProductCart>() {
        @Override
        public ProductCart createFromParcel(Parcel in) {
            return new ProductCart(in);
        }

        @Override
        public ProductCart[] newArray(int size) {
            return new ProductCart[size];
        }
    };

}
