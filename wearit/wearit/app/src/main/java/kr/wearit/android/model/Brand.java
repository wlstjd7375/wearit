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
