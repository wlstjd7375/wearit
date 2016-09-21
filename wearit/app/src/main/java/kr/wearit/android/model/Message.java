package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import kr.wearit.android.App;

public class Message implements Parcelable {

	public enum Type {
		NORMAL(1), SHOP(2), CART(3), PRODUCT(4), BROADCASET_MESSAGE_ROOT(5), QUESTION(6);

		public final int value;

		private Type(int value) {
			this.value = value;
		}

		public static Type valueOf(int value) {
			for (Type item : values())
				if (item.value == value)
					return item;

			throw new AssertionError("value = " + value);
		}
	}

	//

	private int key;
	private int user1;
	private int user2;
	private String content;
	private Integer image;
	private int type;
	private Integer shop;
	private Integer product;
	private Integer question;
	private Date date1;
	private Date date2;

	private String user1Nickname;
	private String user1ImagePath;
	private String user2Nickname;
	private String user2ImagePath;
	private String imagePath;
	private String productImagePath;

	private int unread;

	//

	public Message() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getUser1() {
		return user1;
	}

	public void setUser1(int user1) {
		this.user1 = user1;
	}

	public int getUser2() {
		return user2;
	}

	public void setUser2(int user2) {
		this.user2 = user2;
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

	public int getType() {
		return type;
	}

	public Type getTypeEnum() {
		return Type.valueOf(type);
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getShop() {
		return shop;
	}

	public void setShop(Integer shop) {
		this.shop = shop;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Integer getQuestion() {
		return question;
	}

	public void setQuestion(Integer question) {
		this.question = question;
	}

	public Date getDate1() {
		return date1;
	}

	public Date getDate2() {
		return date2;
	}

	//

	public String getUser1Nickname() {
		return user1Nickname;
	}

	public String getUser1ImagePath() {
		return user1ImagePath;
	}

	public String getUser2Nickname() {
		return user2Nickname;
	}

	public String getUser2ImagePath() {
		return user2ImagePath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getProductImagePath() {
		return productImagePath;
	}

	public String getDisplayImagePath() {
		switch (getTypeEnum()) {
			case NORMAL:
			case SHOP:
			case QUESTION:
			case BROADCASET_MESSAGE_ROOT:
				return imagePath;
			case CART:
			case PRODUCT:
				return productImagePath;
		}

		throw new AssertionError();
	}

	//

	public int getUnread() {
		return unread;
	}

	//

	public boolean isMe() {
		return App.getInstance().isLogin() && App.getInstance().getUser().getKey() == user1;
	}

	public int getTargetKey() {
		return App.getInstance().getUser().getKey() == user1 ? user2 : user1;
	}

	public String getTargetNickname() {
		return App.getInstance().getUser().getKey() == user1 ? user2Nickname : user1Nickname;
	}

	public String getTargetImagePath() {
		return App.getInstance().getUser().getKey() == user1 ? user2ImagePath : user1ImagePath;
	}

	public Date getTargetDate() {
		return date1;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeInt(user1);
		dest.writeInt(user2);
		dest.writeString(content);
		dest.writeValue(image);
		dest.writeInt(type);
		dest.writeValue(shop);
		dest.writeValue(product);
		dest.writeSerializable(date1);
		dest.writeSerializable(date2);

		dest.writeString(user1Nickname);
		dest.writeString(user1ImagePath);
		dest.writeString(user2Nickname);
		dest.writeString(user2ImagePath);
		dest.writeString(imagePath);
		dest.writeString(productImagePath);

		dest.writeInt(unread);
	}

	private Message(Parcel in) {
		key = in.readInt();
		user1 = in.readInt();
		user2 = in.readInt();
		content = in.readString();
		image = (Integer) in.readValue(Integer.class.getClassLoader());
		type = in.readInt();
		shop = (Integer) in.readValue(Integer.class.getClassLoader());
		product = (Integer) in.readValue(Integer.class.getClassLoader());
		date1 = (Date) in.readSerializable();
		date2 = (Date) in.readSerializable();

		user1Nickname = in.readString();
		user1ImagePath = in.readString();
		user2Nickname = in.readString();
		user2ImagePath = in.readString();
		imagePath = in.readString();
		productImagePath = in.readString();

		unread = in.readInt();
	}

	public static final Creator<Message> CREATOR = new Creator<Message>() {

		@Override
		public Message createFromParcel(Parcel in) {
			return new Message(in);
		}

		@Override
		public Message[] newArray(int size) {
			return new Message[size];
		}
	};
}
