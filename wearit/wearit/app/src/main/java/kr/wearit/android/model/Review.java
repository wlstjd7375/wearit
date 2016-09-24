package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Review implements Parcelable, ProductRelation, ImageSizeContainer {

	private int key;
	private String title;
	private String description;
	private int relation;
	private int comment;
	private int favorite;
	private int favoriteList;
	private int hit;
	private Date date;

	private String imagePath;
	private int imageWidth;
	private int imageHeight;
	private int product;
	private int commentCount;

	private Resource imageObject;
	private Content contentObject;

	private ShopGroup shops;
	private ArrayList<Product> products;

	private FavoriteItem userFavorite;

	//

	public Review() {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//

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

	//

	public Resource getImageObject() {
		return imageObject;
	}

	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}

	public Content getContentObject() {
		return contentObject;
	}

	public void setContentObject(Content contentObject) {
		this.contentObject = contentObject;
	}

	//

	public ShopGroup getShops() {
		return shops;
	}

	public void setShops(ShopGroup shops) {
		this.shops = shops;
	}

	//

	@Override
	public int getProductRelationKey() {
		return relation;
	}

	@Override
	public int getProductRelationCount() {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeInt(relation);
		dest.writeInt(comment);
		dest.writeInt(commentCount);
		dest.writeInt(favorite);
		dest.writeInt(favoriteList);
		dest.writeInt(hit);
		dest.writeSerializable(date);

		dest.writeString(imagePath);
		dest.writeInt(imageWidth);
		dest.writeInt(imageHeight);
		dest.writeInt(product);

		dest.writeParcelable(imageObject, flags);
		dest.writeParcelable(contentObject, flags);

		dest.writeParcelable(shops, flags);
		dest.writeTypedList(products);

		dest.writeParcelable(userFavorite, flags);
	}

	private Review(Parcel in) {
		key = in.readInt();
		title = in.readString();
		description = in.readString();
		relation = in.readInt();
		comment = in.readInt();
		commentCount = in.readInt();
		favorite = in.readInt();
		favoriteList = in.readInt();
		hit = in.readInt();
		date = (Date) in.readSerializable();

		imagePath = in.readString();
		imageWidth = in.readInt();
		imageHeight = in.readInt();
		product = in.readInt();

		imageObject = in.readParcelable(Resource.class.getClassLoader());
		contentObject = in.readParcelable(Content.class.getClassLoader());

		shops = in.readParcelable(ShopGroup.class.getClassLoader());
		in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);

		userFavorite = in.readParcelable(FavoriteItem.class.getClassLoader());
	}

	public static final Creator<Review> CREATOR = new Creator<Review>() {

		@Override
		public Review createFromParcel(Parcel in) {
			return new Review(in);
		}

		@Override
		public Review[] newArray(int size) {
			return new Review[size];
		}
	};
}
