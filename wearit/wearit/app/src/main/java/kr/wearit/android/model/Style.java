package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

import kr.wearit.android.util.Util;

public class Style implements Parcelable, Images, ProductRelation, ImageSizeContainer {

	private int key;
	private String title;
	private String description;
	private int image;
	private Resource imageObject;
	private String imagePath;
	private int imageWidth;
	private int imageHeight;
	private int collection;
	private Collection collectionObject;
	private int gender;
	private int relation;
	private int comment;
	private int favorite;
	private int favoriteList;
	private int hit;
	private int product;
	private int commentCount;
	private ArrayList<Product> products;
	private Date date;
	private int isfavorite;

	private ShopGroup shops;
	private FavoriteItem userFavorite;

	//

	public Style() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
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

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public Resource getImageObject() {
		return imageObject;
	}

	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	@Override
	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
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

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIsfavorite() { return this.isfavorite; }

	public void setIsfavorite(int isfavorite) { this.isfavorite = isfavorite; }

	//

	@Override
	public Type getImagesType() {
		return Type.STYLE;
	}

	@Override
	public int getImagesSize() {
		return collectionObject.getImages().size();
	}

	@Override
	public Resource getImagesResource(int i) {
//		if (i == 0)
//			return imageObject;
//		else
			return collectionObject.getImages().get(i);
	}

	//

	@Override
	public int getProductRelationKey() {
		return relation;
	}

	@Override
	public int getProductRelationCount() {
		if(product > 6){
			return 6;
		}
		return /*products != null ? products.size() : */product;
	}

	@Override
	public ArrayList<Product> getProductRelationList() {
		if (products == null)
			products = new ArrayList<Product>();

		return products;
	}

	@Override
	public void setProductRelationList(ArrayList<Product> products) {
		this.products = products;
	}

	//

	public ShopGroup getShops() {
		return shops;
	}

	public void setShops(ShopGroup shops) {
		this.shops = shops;
	}

	public FavoriteItem getUserFavorite() {
		return userFavorite;
	}

	public void setUserFavorite(FavoriteItem userFavorite) {
		this.userFavorite = userFavorite;
	}

	//

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int value) {
		commentCount = value;
	}

	//

	public boolean isNew() {
		return Util.isNew(date);
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeInt(image);
		dest.writeParcelable(imageObject, flags);
		dest.writeString(imagePath);
		dest.writeInt(imageWidth);
		dest.writeInt(imageHeight);
		dest.writeInt(collection);
		dest.writeParcelable(collectionObject, flags);
		dest.writeInt(gender);
		dest.writeInt(relation);
		dest.writeInt(comment);
		dest.writeInt(commentCount);
		dest.writeInt(favorite);
		dest.writeInt(favoriteList);
		dest.writeInt(hit);
		dest.writeInt(product);
		dest.writeTypedList(products);
		dest.writeSerializable(date);
		dest.writeInt(isfavorite);

		dest.writeParcelable(shops, flags);
		dest.writeParcelable(userFavorite, flags);
	}

	private Style(Parcel in) {
		key = in.readInt();
		title = in.readString();
		description = in.readString();
		image = in.readInt();
		imageObject = in.readParcelable(Resource.class.getClassLoader());
		imagePath = in.readString();
		imageWidth = in.readInt();
		imageHeight = in.readInt();
		collection = in.readInt();
		collectionObject = in.readParcelable(Collection.class.getClassLoader());
		gender = in.readInt();
		relation = in.readInt();
		comment = in.readInt();
		commentCount = in.readInt();
		favorite = in.readInt();
		favoriteList = in.readInt();
		hit = in.readInt();
		product = in.readInt();
		in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);
		date = (Date) in.readSerializable();
		isfavorite = in.readInt();

		shops = in.readParcelable(ShopGroup.class.getClassLoader());
		userFavorite = in.readParcelable(FavoriteItem.class.getClassLoader());
	}

	public static final Creator<Style> CREATOR = new Creator<Style>() {

		@Override
		public Style createFromParcel(Parcel in) {
			return new Style(in);
		}

		@Override
		public Style[] newArray(int size) {
			return new Style[size];
		}
	};
}
