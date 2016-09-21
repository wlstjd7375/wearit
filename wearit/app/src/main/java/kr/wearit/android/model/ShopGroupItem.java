package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopGroupItem implements Parcelable {

	private int shop;
	private String shopName;
	private String shopImagePath;
	private String shopLogoPath;

	//

	public int getShop() {
		return shop;
	}

	public void setShop(int shop) {
		this.shop = shop;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopImagePath() {
		return shopImagePath;
	}

	public void setShopImagePath(String shopImagePath) {
		this.shopImagePath = shopImagePath;
	}

	public String getShopLogoPath() {
		return shopLogoPath;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(shop);
		dest.writeString(shopName);
		dest.writeString(shopImagePath);
		dest.writeString(shopLogoPath);
	}

	private ShopGroupItem(Parcel in) {
		shop = in.readInt();
		shopName = in.readString();
		shopImagePath = in.readString();
		shopLogoPath = in.readString();
	}

	public static final Creator<ShopGroupItem> CREATOR = new Creator<ShopGroupItem>() {

		@Override
		public ShopGroupItem createFromParcel(Parcel in) {
			return new ShopGroupItem(in);
		}

		@Override
		public ShopGroupItem[] newArray(int size) {
			return new ShopGroupItem[size];
		}
	};
}
