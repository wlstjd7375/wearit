package kr.wearit.android.model;

import android.os.Parcel;

public class CollectionImage extends Resource {

	public CollectionImage() {
	}

	//

	@SuppressWarnings("unused")
	private CollectionImage(Parcel in) {
		super(in);
	}

	/*
	public CollectionImage() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}

	private CollectionImage(Parcel in) {
	}

	public static final Parcelable.Creator<CollectionImage> CREATOR = new Parcelable.Creator<CollectionImage>() {

		public CollectionImage createFromParcel(Parcel in) {
			return new CollectionImage(in);
		}

		public CollectionImage[] newArray(int size) {
			return new CollectionImage[size];
		}
	};
	*/
}
