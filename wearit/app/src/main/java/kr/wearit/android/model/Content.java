package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Content implements Parcelable {

	private Integer key;
	private ArrayList<ContentPart> parts;



	//

	public Content() {
	}

	//

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public List<ContentPart> getParts() {
		return parts;
	}

	public void setParts(ArrayList<ContentPart> parts) {
		this.parts = parts;
	}

	public void add(ContentPart part) {
		if (parts == null)
			parts = new ArrayList<ContentPart>();

		parts.add(part);
	}

	//

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		for (ContentPart part : parts)
			string.append(part.toString());

		return string.toString();
	}

	// Parcelable

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeTypedList(parts);
	}

	private Content(Parcel in) {
		key = in.readInt();
		in.readTypedList(parts = new ArrayList<ContentPart>(), ContentPart.CREATOR);
	}

	public static final Creator<Content> CREATOR = new Creator<Content>() {

		@Override
		public Content createFromParcel(Parcel in) {
			return new Content(in);
		}

		@Override
		public Content[] newArray(int size) {
			return new Content[size];
		}
	};
}
