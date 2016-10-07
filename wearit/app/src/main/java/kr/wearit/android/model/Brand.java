package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Brand implements Parcelable, Images {

	private int key;
	private String name;
	private String description;
	private Resource logoObject;
	private String logoPath;
	//private String logoThumbnailPath;
	private Resource imageObject;
	private Resource previewObject;
	private String imagePath;
	//private String imageThumbnailPath;
	private String movie;
	private int collection;
	private Collection collectionObject;

	private FavoriteItem userFavorite;
	private ArrayList<Shop> shopList;
	private ArrayList<Product> productList;

	private double latitude;
	private double longitude;

	private String deliver_info;
	private String refund_info;

	private String start_time;
	private String end_time;

	private ArrayList<String> holidays;


	private String phone;

	private boolean checked;

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Resource getLogoObject() {
		return logoObject;
	}

	public void setLogoObject(Resource logoObject) {
		this.logoObject = logoObject;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	//	public String getLogoThumbnailPath() {
	//		return logoThumbnailPath;
	//	}
	//
	//	public void setLogoThumbnailPath(String logoThumbnailPath) {
	//		this.logoThumbnailPath = logoThumbnailPath;
	//	}

	public Resource getImageObject() {
		return imageObject;
	}

	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}

	public Resource getPreviewObject() { return previewObject; }

	public void setPreviewObject(Resource previewObject) {
		this.previewObject = previewObject;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public double getImageRatio() {
		if (imageObject == null)
			return 0;

		return (double) imageObject.getImageHeight() / imageObject.getImageWidth();
	}

	//	public String getImageThumbnailPath() {
	//		return imageThumbnailPath;
	//	}
	//
	//	public void setImageThumbnailPath(String imageThumbnailPath) {
	//		this.imageThumbnailPath = imageThumbnailPath;
	//	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}

	public Collection getCollectionObject() {
		return collectionObject;
	}

	public void setCollectionObject(Collection collectionObject) {
		this.collectionObject = collectionObject;
	}

	@Override
	public Type getImagesType() {
		return Type.BRAND;
	}

	@Override
	public int getImagesSize() {
		if (collectionObject == null)
			return 0;

		return collectionObject.getImages().size();
	}

	@Override
	public Resource getImagesResource(int i) {
		return collectionObject.getImages().get(i);
	}

	//

	public FavoriteItem getUserFavorite() {
		return userFavorite;
	}

	public void setUserFavorite(FavoriteItem userFavorite) {
		this.userFavorite = userFavorite;
	}

	public ArrayList<Shop> getShopList() {
		return shopList;
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

	public String getDeliver_info() {
		return deliver_info;
	}

	public void setDeliver_info(String deliver_info) {
		this.deliver_info = deliver_info;
	}

	public String getRefund_info() {
		return refund_info;
	}

	public void setRefund_info(String refund_info) {
		this.refund_info = refund_info;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public ArrayList<String> getHolidays() {
		return holidays;
	}

	public void setHolidays(ArrayList<String> holidays) {
		this.holidays = holidays;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		dest.writeString(description);
		dest.writeParcelable(logoObject, flags);
		dest.writeString(logoPath);
		//dest.writeString(logoThumbnailPath);
		dest.writeParcelable(imageObject, flags);
		dest.writeParcelable(previewObject, flags);
		dest.writeString(imagePath);
		//dest.writeString(imageThumbnailPath);
		dest.writeString(movie);
		dest.writeInt(collection);
		dest.writeParcelable(collectionObject, flags);

		dest.writeParcelable(userFavorite, flags);
		dest.writeTypedList(shopList);
		dest.writeTypedList(productList);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(deliver_info);
		dest.writeString(refund_info);
		dest.writeString(start_time);
		dest.writeString(end_time);
		dest.writeStringList(holidays);
		dest.writeString(phone);
	}

	private Brand(Parcel in) {
		key = in.readInt();
		name = in.readString();
		description = in.readString();
		logoObject = in.readParcelable(Resource.class.getClassLoader());
		logoPath = in.readString();
		//logoThumbnailPath = in.readString();
		imageObject = in.readParcelable(Resource.class.getClassLoader());
		previewObject = in.readParcelable(Resource.class.getClassLoader());
		imagePath = in.readString();
		//imageThumbnailPath = in.readString();
		movie = in.readString();
		collection = in.readInt();
		collectionObject = in.readParcelable(Collection.class.getClassLoader());

		userFavorite = in.readParcelable(FavoriteItem.class.getClassLoader());
		in.readTypedList(shopList = new ArrayList<Shop>(), Shop.CREATOR);
		in.readTypedList(productList = new ArrayList<Product>(), Product.CREATOR);

		latitude = in.readDouble();
		longitude = in.readDouble();
		deliver_info = in.readString();
		refund_info = in.readString();
		start_time = in.readString();
		end_time = in.readString();
		holidays = new ArrayList<String>();
		in.readStringList(holidays);
		phone = in.readString();
	}

	public static final Creator<Brand> CREATOR = new Creator<Brand>() {

		@Override
		public Brand createFromParcel(Parcel in) {
			return new Brand(in);
		}

		@Override
		public Brand[] newArray(int size) {
			return new Brand[size];
		}
	};
}
