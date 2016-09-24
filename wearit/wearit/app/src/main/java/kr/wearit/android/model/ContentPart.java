package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContentPart implements Parcelable {

	public enum Type {
		TEXT(1), IMAGE(2), MOVIE(3);

		public final int value;

		private Type(int value) {
			this.value = value;
		}

		public static Type valueOf(int value) {
			for (Type item : values())
				if (item.value == value)
					return item;

			throw new AssertionError();
		}
	}

	//

	public static ContentPart createText(int key) {
		ContentPart item = new ContentPart();
		item.setKey(key);
		item.setTypeEnum(Type.TEXT);

		return item;
	}

	public static ContentPart createImage(int key, Resource image) {
		ContentPart item = new ContentPart();
		item.setKey(key);
		item.setTypeEnum(Type.IMAGE);
		item.setImage(image.getKey());
		item.setImageObject(image);

		return item;
	}

	//

	private Integer key;
	private int type;
	private String text;
	private Integer image;
	private Resource imageObject;
	private String movie;

	//

	public ContentPart() {
	}

	//

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Type getTypeEnum() {
		return Type.valueOf(type);
	}

	public void setTypeEnum(Type type) {
		this.type = type.value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public Resource getImageObject() {
		return imageObject;
	}

	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	//

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("type = ").append(type);

		return string.toString();
	}

	// Parcelable

	public ContentPart forAdd() {
		ContentPart item = new ContentPart();
		item.type = type;
		item.text = text;
		item.image = image;

		return item;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(key);
		dest.writeInt(type);
		dest.writeString(text);
		dest.writeValue(image);
		dest.writeParcelable(imageObject, flags);
		dest.writeString(movie);
	}

	private ContentPart(Parcel in) {
		key = (Integer) in.readValue(Integer.class.getClassLoader());
		type = in.readInt();
		text = in.readString();
		image = (Integer) in.readValue(Integer.class.getClassLoader());
		imageObject = in.readParcelable(Resource.class.getClassLoader());
		movie = in.readString();
	}

	public static final Creator<ContentPart> CREATOR = new Creator<ContentPart>() {

		@Override
		public ContentPart createFromParcel(Parcel in) {
			return new ContentPart(in);
		}

		@Override
		public ContentPart[] newArray(int size) {
			return new ContentPart[size];
		}
	};
}
