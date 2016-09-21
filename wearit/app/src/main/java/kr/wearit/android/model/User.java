package kr.wearit.android.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import kr.wearit.android.util.Util;

public class User implements Parcelable {

	public static final int ALL = -1;
	public static final int ROOT = -2;

	//

	public enum Account {
		ID(1, "아이디"), KAKAO(2, "카카오"),FACEBOOK(3, "페이스북");

		@SuppressLint("UseSparseArrays")
		private static final Map<Integer, Account> map = new HashMap<Integer, Account>();

		static {
			for (Account item : values())
				map.put(item.value, item);
		}

		public final int value;
		public final String title;

		private Account(int value, String title) {
			this.value = value;
			this.title = title;
		}

		public int getValue() {
			return value;
		}

		public static Account valueOf(int value) {
			return map.get(value);
		}
	}

	//

	private int key;
	private int account = Account.ID.value;
	private String id;
	private String password;
	private Integer image;
	private String name;
	private String nickname;
	private String facebookid;
	private String kakaoid;
	private String phone;
	private String gcm;
	private String email;

	private boolean gcmNotify;
	private boolean gcmMessage;

	private Integer couponcount;

	private Integer mileage;
	private Integer total_mileage_add;
	private Integer total_mileage_sub;

	private String postcode;
	private String address1;
	private String address2;

	private Resource imageObject;

	//

	public User() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public Account getAccountType() {
		return Account.valueOf(account);
	}

	public void setAccountType(Account account) {
		this.account = account.value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public String getName() { return this.name; }

	public void setName(String name) { this.name = name; }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getTotal_mileage_add() {
		return total_mileage_add;
	}

	public void setTotal_mileage_add(Integer total_mileage_add) {
		this.total_mileage_add = total_mileage_add;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getTotal_mileage_sub() {
		return total_mileage_sub;
	}

	public void setTotal_mileage_sub(Integer total_mileage_sub) {
		this.total_mileage_sub = total_mileage_sub;
	}

	public String getFacebookid(){ return this.facebookid; }

	public void setFacebookid(String facebookid) { this.facebookid = facebookid; }

	public String getKakaoid() { return this.kakaoid; }

	public void setKakaoid(String kakaoid) { this.kakaoid = kakaoid; }

	public String getEmail() { return this.email; }

	public void setEmail(String email) { this.email = email; }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String value) {
		this.gcm = value;
	}

	public boolean isGcmNotify() {
		return gcmNotify;
	}

	public void setGcmNotify(boolean gcmNotify) {
		this.gcmNotify = gcmNotify;
	}

	public boolean isGcmMessage() {
		return gcmMessage;
	}

	public void setGcmMessage(boolean gcmMessage) {
		this.gcmMessage = gcmMessage;
	}

	//

	public Resource getImageObject() {
		return imageObject;
	}

	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}


	public Integer getCouponcount() {
		return couponcount;
	}

	public void setCouponcount(Integer couponcount) {
		this.couponcount = couponcount;
	}


	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return this.postcode;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return this.address1;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return this.address2;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(id);
		dest.writeString(password);
		dest.writeInt(Util.writeInteger(image));
		dest.writeString(name);
		dest.writeString(nickname);
		dest.writeString(facebookid);
		dest.writeString(kakaoid);
		dest.writeString(email);
		dest.writeString(phone);
		dest.writeString(address1);
		dest.writeString(address2);
		dest.writeString(postcode);
		dest.writeInt(mileage);
		dest.writeInt(total_mileage_add);
		dest.writeInt(total_mileage_sub);
		dest.writeInt(couponcount);

		dest.writeParcelable(imageObject, flags);
	}

	private User(Parcel in) {
		key = in.readInt();
		id = in.readString();
		password = in.readString();
		image = Util.readInteger(in.readInt());
		name = in.readString();
		nickname = in.readString();
		facebookid = in.readString();
		kakaoid = in.readString();
		email = in.readString();
		phone = in.readString();
		address1 = in.readString();
		address2 = in.readString();
		postcode = in.readString();

		mileage = in.readInt();
		total_mileage_add = in.readInt();
		total_mileage_sub = in.readInt();
		couponcount = in.readInt();

		imageObject = in.readParcelable(Resource.class.getClassLoader());
	}

	public static final Creator<User> CREATOR = new Creator<User>() {

		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
