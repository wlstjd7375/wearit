package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Collection implements Parcelable {

	private int key;
	private ArrayList<Resource> images;

	//

	public Collection() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public ArrayList<Resource> getImages() {
		return images;
	}

	public void setImages(ArrayList<Resource> images) {
		this.images = images;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeList(images);
	}

	@SuppressWarnings("unchecked")
	private Collection(Parcel in) {
		key = in.readInt();
		images = in.readArrayList(Resource.class.getClassLoader());
	}

	public static final Creator<Collection> CREATOR = new Creator<Collection>() {

		@Override
		public Collection createFromParcel(Parcel in) {
			return new Collection(in);
		}

		@Override
		public Collection[] newArray(int size) {
			return new Collection[size];
		}
	};
}
