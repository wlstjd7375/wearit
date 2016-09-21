package kr.wearit.android.model;

import android.os.Parcel;

import java.util.List;

public class ProductCategory implements Key.Name<ProductCategory> {

	public static final ProductCategory ALL = new ProductCategory(-1, "전체");

	//

	private int key;
	private String name;
	private int order;
	private Integer parent;

	private List<ProductCategory> children;
	private int grandparent;
	//

	public ProductCategory() {
	}

	private ProductCategory(int key, String name) {
		this.key = key;
		this.name = name;
	}

	//

	@Override
	public int getKey() {
		return key;
	}

	@Override
	public void setKey(int key) {
		this.key = key;
	}

	@Override
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

	public List<ProductCategory> getChildren() {
		return children;
	}

	public void setChildren(List<ProductCategory> children) {
		this.children = children;
	}

	public void setGrandParent(int grandparent){
		this.grandparent = grandparent;
	}

	public int getGrandparent(){
		return this.grandparent;
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
		dest.writeValue(parent);
		dest.writeTypedList(children);
		dest.writeInt(grandparent);
	}

	private ProductCategory(Parcel in) {
		key = in.readInt();
		name = in.readString();
		order = in.readInt();
		parent = (Integer) in.readValue(Integer.class.getClassLoader());
		in.readTypedList(children, CREATOR);
		grandparent = in.readInt();
	}

	public static final Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {

		@Override
		public ProductCategory createFromParcel(Parcel in) {
			return new ProductCategory(in);
		}

		@Override
		public ProductCategory[] newArray(int size) {
			return new ProductCategory[size];
		}
	};
}
