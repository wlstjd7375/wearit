package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ShopGroup implements Parcelable {

	private ArrayList<ShopGroupItem> list;

	//

	public ArrayList<ShopGroupItem> getList() {
		return list;
	}

	public void setList(ArrayList<ShopGroupItem> list) {
		this.list = list;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(list);
	}

	private ShopGroup(Parcel in) {
		in.readTypedList(list = new ArrayList<ShopGroupItem>(), ShopGroupItem.CREATOR);
	}

	public static final Creator<ShopGroup> CREATOR = new Creator<ShopGroup>() {

		@Override
		public ShopGroup createFromParcel(Parcel in) {
			return new ShopGroup(in);
		}

		@Override
		public ShopGroup[] newArray(int size) {
			return new ShopGroup[size];
		}
	};
}
