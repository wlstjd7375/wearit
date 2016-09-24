package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Cart implements Parcelable {

	private int key;
	private int user;

	private int product; // 여기다가 sizekey를 넣을것
	private Date date;

	private int count;

	private String shopName;
	private String brandName;
	private String productName;
	private String productImagePath;

	//private String productThumbnailImagePath;

	//

	public Cart() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}


	public int getProductKey() {
		return product;
	}

	public void setProductKey(int productKey) {
		this.product = productKey;
	}


//	public int getProductKey() {
//		return productKey;
//	}
//
//	public void setProductKey(int product) {
//		this.productKey = product;
//	}
//
//	public Product getProdut(int productKey){
//		ProductApi.get(productKey, new Api.OnAuthDefaultListener<Product>(){
//			@Override
//			public void onSuccess(Product data){
//				product = data;
//			}
//		});
//		return this.product;
//	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImagePath() {
		return productImagePath;
	}

	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}


	//	public String getProductThumbnailImagePath() {
	//		return productThumbnailImagePath;
	//	}
	//
	//	public void setProductThumbnailImagePath(String productThumbnailImagePath) {
	//		this.productThumbnailImagePath = productThumbnailImagePath;
	//	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeInt(user);
		dest.writeInt(product);
		dest.writeSerializable(date);

		dest.writeString(shopName);
		dest.writeString(brandName);
		dest.writeString(productName);
		dest.writeString(productImagePath);
		dest.writeInt(count);

		//dest.writeString(productThumbnailImagePath);
	}

	private Cart(Parcel in) {
		key = in.readInt();
		user = in.readInt();
		product = in.readInt();
		date = (Date) in.readSerializable();

		shopName = in.readString();
		brandName = in.readString();
		productName = in.readString();
		productImagePath = in.readString();
		count = in.readInt();
		//productThumbnailImagePath = in.readString();
	}

	public static final Creator<Cart> CREATOR = new Creator<Cart>() {

		@Override
		public Cart createFromParcel(Parcel in) {
			return new Cart(in);
		}

		@Override
		public Cart[] newArray(int size) {
			return new Cart[size];
		}
	};
}
