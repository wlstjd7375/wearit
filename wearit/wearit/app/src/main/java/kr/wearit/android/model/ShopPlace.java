package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ShopPlace implements Parcelable {

	private int key;
	private String name;
	private int order;
	private Integer parent;

	private ArrayList<ShopPlace> children;

	//

	public ShopPlace() {
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

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public List<ShopPlace> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ShopPlace> children) {
		this.children = children;
	}

	//

	public int getChildrenCount() {
		return children != null ? children.size() : 0;
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
		dest.writeInt(parent);
		dest.writeTypedList(children);
	}

	private ShopPlace(Parcel in) {
		key = in.readInt();
		name = in.readString();
		order = in.readInt();
		parent = in.readInt();
		in.readTypedList(children, CREATOR);
	}

	public static final Creator<ShopPlace> CREATOR = new Creator<ShopPlace>() {

		@Override
		public ShopPlace createFromParcel(Parcel in) {
			return new ShopPlace(in);
		}

		@Override
		public ShopPlace[] newArray(int size) {
			return new ShopPlace[size];
		}
	};
}
