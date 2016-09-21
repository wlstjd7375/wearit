package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopConcept implements Parcelable {

	private int key;
	private String name;
	private int order;

	//

	public ShopConcept() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(name);
		dest.writeInt(order);
	}

	private ShopConcept(Parcel in) {
		key = in.readInt();
		name = in.readString();
		order = in.readInt();
	}

	public static final Creator<ShopConcept> CREATOR = new Creator<ShopConcept>() {

		@Override
		public ShopConcept createFromParcel(Parcel in) {
			return new ShopConcept(in);
		}

		@Override
		public ShopConcept[] newArray(int size) {
			return new ShopConcept[size];
		}
	};
}
