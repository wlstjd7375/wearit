package kr.wearit.android.model;

import android.os.Parcel;

import java.util.ArrayList;

public class Shop implements Key<Shop>, Images {

	public static final Key<Shop> ALL = new Shop(-1);
	public static final Key<Shop> ROOT = new Shop(-2);

	public static final Shop ENTIRE = createEntire();

	//

	public static boolean equals(Shop shop1, Shop shop2) {
		if (shop1 == shop2)
			return true;

		if (shop1 == null || shop2 == null)
			return false;

		return shop1.getKey() == shop2.getKey();
	}

	public static boolean isValid(Integer key) {
		return key != null && key > 0;
	}

	//

	private static Shop createEntire() {
		Shop shop = new Shop();
		shop.setKey(ALL.getKey());
		shop.setName("전체매장");

		return shop;
	}

	//

	public static Key<Shop> getKey(int key) {
		return new Shop(key);
	}

	//

	private int key;
	private String name;
	private String title;
	private String description;
	private String introduction;
	private String phone;
	private String address;
	private double latitude;
	private double longitude;
	private String hour;
	private String holiday;
	private String homepage;
	private String subway;
	private String parking;
	private boolean block_message;
	private boolean block_message_product;
	private int owner;
	private int manager;
	private String managerNickname;
	private int favorite;
	private int favoriteList;
	private int hit;
	private int place;
	private ArrayList<ProductCategory> categories;

	private String logoPath;
	private String imagePath;
	private int promotion;

	private int brand1;
	private int brand2;
	private int brand3;
	private int brand4;
	private String brandlogopath1;
	private String brandlogopath2;
	private String brandlogopath3;
	private String brandlogopath4;

	private String refund_info;
	private String deliver_info;

	private Resource logoObject;
	private Resource imageObject;
	private Collection collectionObject;

	private FavoriteItem userFavorite;
	private ArrayList<Brand> brands;
	private ArrayList<Post> reviews;

	private String category;

	private int visit;

	//

	public Shop() {
	}

	private Shop(int key) {
		this.key = key;
	}

	//

	@Override
	public int getKey() {
		return key;
	}

	@Override
	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Resource getLogoObject() {
		return logoObject;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public Resource getImageObject() {
		return imageObject;
	}

	/*
	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}
	*/

	public String getImagePath() {
		return imagePath;
	}


//	public void setImagePath(String imagePath) {
//		this.imagePath = imagePath;
//	}


	public int getPromotion() {
		return promotion;
	}

	public void setPromotion(int promotion) {
		this.promotion = promotion;
	}

	public Collection getCollectionObject() {
		return collectionObject;
	}

	public void setCollectionObject(Collection collectionObject) {
		this.collectionObject = collectionObject;
	}

	public int getPlace(){
		return this.place;
	}

	public void setPlace(int place){
		this.place = place;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getSubway() {
		return subway;
	}

	public void setSubway(String subway) {
		this.subway = subway;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public boolean getBlockMessage() {
		return block_message;
	}

	public void setBlockMessage(boolean value) {
		this.block_message = value;
	}

	public boolean getBlockMessageProduct() {
		return block_message_product;
	}

	public void setBlockMessageProduct(boolean value) {
		this.block_message_product = value;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public String getManagerNickname() {
		return managerNickname;
	}

	public void setManagerNickname(String managerNickname) {
		this.managerNickname = managerNickname;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getFavoriteList() {
		return favoriteList;
	}

	public void setFavoriteList(int favoriteList) {
		this.favoriteList = favoriteList;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public void setCategories(ArrayList<ProductCategory> categories){
		this.categories = categories;
	}

	public void setCategory(String category) { this.category = category; }

	public String getCategory() { return this.category; }

	public ArrayList<ProductCategory> getCategories(){
		return this.categories;
	}

	@Override
	public Type getImagesType() {
		return Type.SHOP;
	}

	@Override
	public int getImagesSize() {
		return getCollectionObject().getImages().size();
	}

	@Override
	public Resource getImagesResource(int i) {

		return getCollectionObject().getImages().get(i);
	}

	//

	public FavoriteItem getUserFavorite() {
		return userFavorite;
	}

	public void setUserFavorite(FavoriteItem userFavorite) {
		this.userFavorite = userFavorite;
	}

	public ArrayList<Brand> getBrands() {
		return brands;
	}

	public ArrayList<Post> getReviews() {
		return reviews;
	}

	public void setBrand1(int brand1){
		this.brand1 = brand1;
	}

	public int getBrand1() {
		return this.brand1;
	}

	public void setBrand2(int brand2){
		this.brand2 = brand2;
	}

	public int getBrand2() {
		return this.brand2;
	}

	public void setBrand3(int brand3){
		this.brand3 = brand3;
	}

	public int getBrand3(){
		return this.brand3;
	}

	public void setBrand4(int brand4){
		this.brand4 = brand4;
	}

	public int getBrand4(){
		return this.brand4;
	}

	public void setBrandlogopath1(String brandlogopath1) { this.brandlogopath1 = brandlogopath1; }

	public String getBrandlogopath1() { return this.brandlogopath1; }

	public void setBrandlogopath2(String brandlogopath2) { this.brandlogopath2 = brandlogopath2; }

	public String getBrandlogopath2() { return this.brandlogopath2; }

	public void setBrandlogopath3(String brandlogopath3) { this.brandlogopath3 = brandlogopath3; }

	public String getBrandlogopath3() { return this.brandlogopath3; }

	public void setBrandlogopath4(String brandlogopath4) { this.brandlogopath4 = brandlogopath4; }

	public String getBrandlogopath4() { return this.brandlogopath4; }

	public void setRefund_info(String refund_info){
		this.refund_info = refund_info;
	}

	public String getRefund_info(){
		return this.refund_info;
	}

	public void setDeliver_info(String deliver_info){
		this.deliver_info = deliver_info;
	}

	public String getDeliver_info(){
		return this.deliver_info;
	}

	public int getVisit() {
		return this.visit;
	}

	public void setVisit(int visit) {
		this.visit = visit;
	}
	//

	@Override
	public String toString() {
		return name; // for android layout adapter
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(name);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(introduction);
		dest.writeString(logoPath);
		dest.writeParcelable(imageObject, flags);
		dest.writeString(imagePath);
		dest.writeParcelable(collectionObject, flags);
		dest.writeInt(place);
		dest.writeString(phone);
		dest.writeString(address);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(hour);
		dest.writeString(holiday);
		dest.writeString(homepage);
		dest.writeString(subway);
		dest.writeString(parking);
		dest.writeInt(block_message ? 1 : 0);
		dest.writeInt(block_message_product ? 1 : 0);
		dest.writeInt(owner);
		dest.writeInt(manager);
		dest.writeString(managerNickname);
		dest.writeInt(favorite);
		dest.writeInt(favoriteList);
		dest.writeInt(hit);

		dest.writeInt(promotion);
		dest.writeParcelable(userFavorite, flags);
		dest.writeTypedList(brands);
		dest.writeTypedList(reviews);
		dest.writeTypedList(categories);
		dest.writeString(category);
		dest.writeInt(brand1);
		dest.writeInt(brand2);
		dest.writeInt(brand3);
		dest.writeInt(brand4);
		dest.writeString(brandlogopath1);
		dest.writeString(brandlogopath2);
		dest.writeString(brandlogopath3);
		dest.writeString(brandlogopath4);
		dest.writeString(refund_info);
		dest.writeString(deliver_info);

		dest.writeInt(visit);
	}

	private Shop(Parcel in) {
		key = in.readInt();
		name = in.readString();
		title = in.readString();
		description = in.readString();
		introduction = in.readString();
		logoPath = in.readString();
		imageObject = in.readParcelable(Resource.class.getClassLoader());
		imagePath = in.readString();
		collectionObject = in.readParcelable(Collection.class.getClassLoader());
		place = in.readInt();
		phone = in.readString();
		address = in.readString();
		latitude = in.readDouble();
		longitude = in.readDouble();
		hour = in.readString();
		holiday = in.readString();
		homepage = in.readString();
		subway = in.readString();
		parking = in.readString();
		block_message = in.readInt() == 1;
		block_message_product = in.readInt() == 1;
		owner = in.readInt();
		manager = in.readInt();
		managerNickname = in.readString();
		favorite = in.readInt();
		favoriteList = in.readInt();
		hit = in.readInt();


		promotion = in.readInt();
		userFavorite = in.readParcelable(FavoriteItem.class.getClassLoader());
		in.readTypedList(brands = new ArrayList<Brand>(), Brand.CREATOR);
		in.readTypedList(reviews = new ArrayList<Post>(), Post.CREATOR);
		in.readTypedList(categories = new ArrayList<ProductCategory>(), ProductCategory.CREATOR);
		category = in.readString();

		brand1 = in.readInt();
		brand2 = in.readInt();
		brand3 = in.readInt();
		brand4 = in.readInt();
		brandlogopath1 = in.readString();
		brandlogopath2 = in.readString();
		brandlogopath3 = in.readString();
		brandlogopath4 = in.readString();

		refund_info = in.readString();
		deliver_info = in.readString();

		visit = in.readInt();
	}

	public static final Creator<Shop> CREATOR = new Creator<Shop>() {

		@Override
		public Shop createFromParcel(Parcel in) {
			return new Shop(in);
		}

		@Override
		public Shop[] newArray(int size) {
			return new Shop[size];
		}
	};
}
