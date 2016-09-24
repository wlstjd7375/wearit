package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Post implements Parcelable {
	private Integer key;
	private int board;
	private Integer thread;
	private Integer index;
	private Integer level;
	private Integer parent;
	private String title;
	@Deprecated
	private String description;
	@Deprecated
	private Integer image;
	private Integer shop;
	private Integer user;
	private int hit;
	private Date date;

	private String contentImagePath;
	private String userNickname;
	private Integer comment;

	@Deprecated
	private Resource imageObject;
	private Content contentObject;
	private Shop shopObject;
	private User userObject;

	//

	public Post() {
	}

	//

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public int getBoard() {
		return board;
	}

	public void setBoard(int board) {
		this.board = board;
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Deprecated
	public String getDescription() {
		return description;
	}

	@Deprecated
	public void setDescription(String description) {
		this.description = description;
	}

	@Deprecated
	public Integer getImage() {
		return image;
	}

	@Deprecated
	public void setImage(Integer image) {
		this.image = image;
	}

	public Integer getShop() {
		return shop;
	}

	public void setShop(Integer shop) {
		this.shop = shop;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
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

	public String getContentImagePath() {
		return contentImagePath;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Integer getComment() {
		return comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}

	//

	@Deprecated
	public Resource getImageObject() {
		return imageObject;
	}

	@Deprecated
	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}

	public Content getContentObject() {
		return contentObject;
	}

	public void setContentObject(Content contentObject) {
		this.contentObject = contentObject;
	}

	public Shop getShopObject() {
		return shopObject;
	}

	public void setShopObject(Shop shopObject) {
		this.shopObject = shopObject;
	}

	public User getUserObject() {
		return userObject;
	}

	public void setUserObject(User userObject) {
		this.userObject = userObject;
	}

	//

	public boolean isNew() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -1);

		return date.after(now.getTime());
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeInt(board);
		dest.writeInt(thread);
		dest.writeInt(index);
		dest.writeInt(level);
		dest.writeValue(parent);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeValue(image);
		dest.writeValue(shop);
		dest.writeInt(user);
		dest.writeInt(hit);
		dest.writeSerializable(date);
		dest.writeString(contentImagePath);
		dest.writeString(userNickname);
		dest.writeValue(comment);
		dest.writeParcelable(imageObject, flags);
		dest.writeParcelable(shopObject, flags);
		dest.writeParcelable(userObject, flags);
		dest.writeParcelable(contentObject, flags);
	}

	private Post(Parcel in) {
		key = in.readInt();
		board = in.readInt();
		thread = in.readInt();
		index = in.readInt();
		level = in.readInt();
		parent = (Integer) in.readValue(Integer.class.getClassLoader());
		title = in.readString();
		description = in.readString();
		image = (Integer) in.readValue(Integer.class.getClassLoader());
		shop = (Integer) in.readValue(Integer.class.getClassLoader());
		user = in.readInt();
		hit = in.readInt();
		date = (Date) in.readSerializable();
		contentImagePath = in.readString();
		userNickname = in.readString();
		comment = (Integer) in.readValue(Integer.class.getClassLoader());
		imageObject = in.readParcelable(Resource.class.getClassLoader());
		shopObject = in.readParcelable(Shop.class.getClassLoader());
		userObject = in.readParcelable(User.class.getClassLoader());
		contentObject = in.readParcelable(Content.class.getClassLoader());
	}

	public static final Creator<Post> CREATOR = new Creator<Post>() {

		@Override
		public Post createFromParcel(Parcel in) {
			return new Post(in);
		}

		@Override
		public Post[] newArray(int size) {
			return new Post[size];
		}
	};
}
