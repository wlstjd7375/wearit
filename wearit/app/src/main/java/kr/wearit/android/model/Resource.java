package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import kr.wearit.android.util.ImageUtil;

public class Resource implements Parcelable {

	private int key;
	private long size;
	private String path;
	private String description;
	private int imageWidth;
	private int imageHeight;
	private String imagePath;

	//private int imageThumbnailWidth;
	//private int imageThumbnailHeight;
	//private String imageThumbnailPath;

	//

	public Resource() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public double getImageRatio() {
		return ImageUtil.getRatio(imageWidth, imageHeight);
	}

	//	public int getImageThumbnailWidth() {
	//		return imageThumbnailWidth;
	//	}
	//
	//	public void setImageThumbnailWidth(int imageThumbnailWidth) {
	//		this.imageThumbnailWidth = imageThumbnailWidth;
	//	}
	//
	//	public int getImageThumbnailHeight() {
	//		return imageThumbnailHeight;
	//	}
	//
	//	public void setImageThumbnailHeight(int imageThumbnailHeight) {
	//		this.imageThumbnailHeight = imageThumbnailHeight;
	//	}
	//
	//	public String getImageThumbnailPath() {
	//		return imageThumbnailPath;
	//	}
	//
	//	public void setImageThumbnailPath(String imageThumbnailPath) {
	//		this.imageThumbnailPath = imageThumbnailPath;
	//	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeLong(size);
		dest.writeString(path);
		dest.writeString(description);
		dest.writeInt(imageWidth);
		dest.writeInt(imageHeight);
		dest.writeString(imagePath);
		//		dest.writeInt(imageThumbnailWidth);
		//		dest.writeInt(imageThumbnailHeight);
		//		dest.writeString(imageThumbnailPath);
	}

	protected Resource(Parcel in) {
		key = in.readInt();
		size = in.readLong();
		path = in.readString();
		description = in.readString();
		imageWidth = in.readInt();
		imageHeight = in.readInt();
		imagePath = in.readString();
		//		imageThumbnailWidth = in.readInt();
		//		imageThumbnailHeight = in.readInt();
		//		imageThumbnailPath = in.readString();
	}

	public static final Creator<Resource> CREATOR = new Creator<Resource>() {

		@Override
		public Resource createFromParcel(Parcel in) {
			return new Resource(in);
		}

		@Override
		public Resource[] newArray(int size) {
			return new Resource[size];
		}
	};
}
