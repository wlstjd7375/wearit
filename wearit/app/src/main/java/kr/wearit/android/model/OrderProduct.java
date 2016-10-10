package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-02-02.
 */
public class OrderProduct implements Parcelable{

    private int key;
    private int count;
    private int product;
    private String size;
    private String name;
    private String code;
    private int price;
    private int deliverprice;
    private String imagepath;
    private int brand;
    private String brandname;
    private String shopname;
    private int order;
    private int cart;
    private int sequence;
    public boolean sale;
    private int sale_price;
    private int sale_rate;
    private int itemTotalPrice;
    private String deliverStatus;
    private String waybill;
    private String courier;
    private String courierName;

    public OrderProduct() {

    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size; }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDeliverprice() { return deliverprice; }

    public void setDeliverprice(int deliverprice) { this.deliverprice = deliverprice; }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public int getSale_rate() { return sale_rate; }

    public void setSale_rate(int sale_rate) { this.sale_rate = sale_rate; }

    public int getSale_price() { return sale_price; }

    public void setSale_price(int sale_price) { this.sale_price = sale_price; }

    public int getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(int itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCourierName() { return courierName; }

    public void setCourierName(String courierName) { this.courierName = courierName; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(count);
        dest.writeInt(product);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(size);
        dest.writeInt(sale ? 1 : 0);
        dest.writeInt(sale_price);
        dest.writeInt(price);
        dest.writeInt(deliverprice);
        dest.writeString(imagepath);
        dest.writeString(brandname);
        dest.writeInt(brand);
        dest.writeString(shopname);
        dest.writeInt(order);
        dest.writeInt(cart);
        dest.writeInt(sequence);
        dest.writeInt(itemTotalPrice);
        dest.writeString(deliverStatus);
        dest.writeString(waybill);
        dest.writeString(courier);
        dest.writeString(courierName);
    }

    private OrderProduct(Parcel in) {
        key = in.readInt();
        count = in.readInt();
        product = in.readInt();
        name = in.readString();
        code = in.readString();
        size = in.readString();
        sale = in.readInt() == 1;
        sale_price = in.readInt();
        price = in.readInt();
        deliverprice = in.readInt();
        imagepath = in.readString();
        brand = in.readInt();
        brandname = in.readString();
        shopname = in.readString();
        order = in.readInt();
        cart = in.readInt();
        sequence = in.readInt();
        itemTotalPrice = in.readInt();
        deliverStatus = in.readString();
        waybill = in.readString();
        courier = in.readString();
        courierName = in.readString();
    }

    public static final Creator<OrderProduct> CREATOR = new Creator<OrderProduct>() {

        @Override
        public OrderProduct createFromParcel(Parcel in) {
            return new OrderProduct(in);
        }

        @Override
        public OrderProduct[] newArray(int size) {
            return new OrderProduct[size];
        }
    };
}
