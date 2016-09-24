package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PostComment implements Parcelable {

	private int key;
	private int post;
	private int thread;
	private int index;
	private int level;
	private Integer parent;
	private String content;
	private Integer image;
	private int user;
	private Date date;

	private String imagePath;
	//private String imageThumbnailPath;
	private String userNickname;
	private Integer userImage;
	private String userImagePath;
	//private String userImageThumbnailPath;
	private String parentUserNickname;

	//

	public PostComment() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	//	public String getImageThumbnailPath() {
	//		return imageThumbnailPath;
	//	}
	//
	//	public void setImageThumbnailPath(String imageThumbnailPath) {
	//		this.imageThumbnailPath = imageThumbnailPath;
	//	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Integer getUserImage() {
		return userImage;
	}

	public void setUserImage(Integer userImage) {
		this.userImage = userImage;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}

	//	public String getUserImageThumbnailPath() {
	//		return userImageThumbnailPath;
	//	}
	//
	//	public void setUserImageThumbnailPath(String userImageThumbnailPath) {
	//		this.userImageThumbnailPath = userImageThumbnailPath;
	//	}

	public String getParentUserNickname() {
		return parentUserNickname;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeInt(post);
		dest.writeInt(thread);
		dest.writeInt(index);
		dest.writeInt(level);
		dest.writeValue(parent);
		dest.writeString(content);
		dest.writeValue(image);
		dest.writeInt(user);
		dest.writeSerializable(date);

		dest.writeString(imagePath);
		//dest.writeString(imageThumbnailPath);
		dest.writeString(userNickname);
		dest.writeValue(userImage);
		dest.writeString(userImagePath);
		//dest.writeString(userImageThumbnailPath);
		dest.writeString(parentUserNickname);
	}

	private PostComment(Parcel in) {
		key = in.readInt();
		post = in.readInt();
		thread = in.readInt();
		index = in.readInt();
		level = in.readInt();
		parent = (Integer) in.readValue(Integer.class.getClassLoader());
		content = in.readString();
		image = (Integer) in.readValue(Integer.class.getClassLoader());
		user = in.readInt();
		date = (Date) in.readSerializable();

		imagePath = in.readString();
		//imageThumbnailPath = in.readString();
		userNickname = in.readString();
		userImage = (Integer) in.readValue(Integer.class.getClassLoader());
		userImagePath = in.readString();
		//userImageThumbnailPath = in.readString();
		parentUserNickname = in.readString();
	}

	public static final Creator<PostComment> CREATOR = new Creator<PostComment>() {

		@Override
		public PostComment createFromParcel(Parcel in) {
			return new PostComment(in);
		}

		@Override
		public PostComment[] newArray(int size) {
			return new PostComment[size];
		}
	};
}
