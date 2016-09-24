package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

	private int key;
	private String id;
	private Integer user;
	private Integer os;
	//

	public Device() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getOs() { return os; }

	public void setOs(Integer os) { this.os = os; }
	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(id);
		dest.writeValue(user);
		dest.writeValue(os);
	}

	private Device(Parcel in) {
		key = in.readInt();
		id = in.readString();
		user = (Integer) in.readValue(Integer.class.getClassLoader());
		os = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Creator<Device> CREATOR = new Creator<Device>() {

		@Override
		public Device createFromParcel(Parcel in) {
			return new Device(in);
		}

		@Override
		public Device[] newArray(int size) {
			return new Device[size];
		}
	};
}
