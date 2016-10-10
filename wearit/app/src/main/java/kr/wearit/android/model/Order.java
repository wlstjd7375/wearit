package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016-01-24.
 */
public class Order implements Parcelable {

    private int key;
    private int user;//user key

    private Date date;

    private ArrayList<OrderProduct> products;

    private int productkey;

    private String size;

    private int cart;

    private String ordernum;

    private String ordertype;

    private int productCount;

    private String imagePath;
    private String name;
    private String brand;

    private String status;
    private String paytype;

    //주문자 정보
    private String ordername;
    private String orderphone;
    private String ordermail;

    private String receivername;
    private String receiverphone;
    private String postcode;
    private String address1;
    private String address2;

    private String request;

    private Integer coupon;
    private int couponprice;

    private int price;

    private int billtype;

    private String billnumber;

    //private String productThumbnailImagePath;

    //

    public Order() {
    }

    //

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }


    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }


    public int getProductkey() {
        return productkey;
    }

    public void setProductkey(int productkey) {
        this.productkey = productkey;
    }

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size; }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public int getCart(){
        return this.cart;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<OrderProduct> products) {
        this.products = products;
    }

    public String getStatus() { return status; }

    public void setStatus(String state) { this.status = state; }

    public String getPaytype() { return paytype; }

    public void setPaytype(String paytype) { this.paytype = paytype; }

    //	public String getProductThumbnailImagePath() {
    //		return productThumbnailImagePath;
    //	}
    //
    //	public void setProductThumbnailImagePath(String productThumbnailImagePath) {
    //		this.productThumbnailImagePath = productThumbnailImagePath;
    //	}

    //

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOrdername() {
        return ordername;
    }

    public String getOrderphone() {
        return orderphone;
    }

    public String getOrdermail() {
        return ordermail;
    }

    public String getReceivername() {
        return receivername;
    }

    public String getReceiverphone() {
        return receiverphone;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getRequest() {
        return request;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public int getCouponprice() { return couponprice; }

    public void setCouponprice(int couponprice) { this.couponprice = couponprice; }

    public int getPrice() {
        return price;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public void setOrderphone(String orderphone) {
        this.orderphone = orderphone;
    }

    public void setOrdermail(String ordermail) {
        this.ordermail = ordermail;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public void setReceiverphone(String receiverphone) {
        this.receiverphone = receiverphone;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBilltype() {
        return billtype;
    }

    public void setBillType(int billtype) {
        this.billtype = billtype;
    }

    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(user);
        dest.writeInt(cart);
        dest.writeInt(productkey);
        dest.writeString(ordernum);
        dest.writeString(ordertype);
        dest.writeString(status);
        dest.writeInt(price);
        dest.writeSerializable(date);
        dest.writeString(paytype);
        dest.writeInt(billtype);
        dest.writeString(billnumber);
        dest.writeValue(coupon);
        dest.writeInt(couponprice);
        dest.writeString(ordername);
        dest.writeString(ordermail);
        dest.writeString(orderphone);
        dest.writeString(receiverphone);
        dest.writeString(receivername);
        dest.writeString(request);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeString(postcode);
        dest.writeTypedList(products);
        dest.writeString(imagePath);
        dest.writeInt(productCount);
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(brand);
    }

    private Order(Parcel in) {
        key = in.readInt();
        user = in.readInt();
        cart = in.readInt();
        productkey = in.readInt();
        ordernum = in.readString();
        ordertype = in.readString();
        status = in.readString();
        price = in.readInt();
        date = (Date) in.readSerializable();
        paytype = in.readString();
        billtype = in.readInt();
        billnumber = in.readString();
        coupon = (Integer) in.readValue(Integer.class.getClassLoader());
        couponprice = in.readInt();
        ordername = in.readString();
        ordermail = in.readString();
        orderphone = in.readString();
        receiverphone = in.readString();
        receivername = in.readString();
        request = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        postcode = in.readString();
        in.readTypedList(products = new ArrayList<OrderProduct>(), OrderProduct.CREATOR);
        imagePath = in.readString();
        productCount = in.readInt();
        name = in.readString();
        size = in.readString();
        brand = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {

        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}