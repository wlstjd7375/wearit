package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Notice implements Parcelable {

	private int key;
	private String title;
	private String content;
	private int hit;
	private Date date;

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeInt(hit);
		dest.writeSerializable(date);
	}

	private Notice(Parcel in) {
		key = in.readInt();
		title = in.readString();
		content = in.readString();
		hit = in.readInt();
		date = (Date) in.readSerializable();
	}

	public static final Creator<Notice> CREATOR = new Creator<Notice>() {

		@Override
		public Notice createFromParcel(Parcel in) {
			return new Notice(in);
		}

		@Override
		public Notice[] newArray(int size) {
			return new Notice[size];
		}
	};
}
