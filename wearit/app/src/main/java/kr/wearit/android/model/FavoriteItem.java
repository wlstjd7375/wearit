package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteItem implements Parcelable {

	private int key;
	private int favorite;
	private int user;

	//

	public FavoriteItem() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	// Parcelable

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeInt(favorite);
		dest.writeInt(user);
	}

	private FavoriteItem(Parcel in) {
		key = in.readInt();
		favorite = in.readInt();
		user = in.readInt();
	}

	public static final Creator<FavoriteItem> CREATOR = new Creator<FavoriteItem>() {

		@Override
		public FavoriteItem createFromParcel(Parcel in) {
			return new FavoriteItem(in);
		}

		@Override
		public FavoriteItem[] newArray(int size) {
			return new FavoriteItem[size];
		}
	};
}
