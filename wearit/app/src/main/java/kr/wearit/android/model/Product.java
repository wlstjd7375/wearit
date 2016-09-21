package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

import kr.wearit.android.util.DateUtil;

public class Product implements Parcelable, Images, ProductRelation {

	private int key;
	private String name;
	private int shop;
	private int relation;
	private int price;
	private boolean sale;
	private int sale_rate;
	private int sale_price;
	private Integer stock;
	private String totalsize;
	private String color;
	private int gender;
	private String material;
	private String collection_description;
	private String url;
	private int favorite;
	private int favoriteList;
	private int hit;
	private Date date;
	private String country;
	private String realsizeimagepath;
	private String realsizecomment;

	private String shopImagePath;
	private int shopManager;
	private String shopManagerNickname;
	private String brandName;
	private String imagePath;
	//private String imageThumbnailPath;
	private int product;
	private boolean blockMessage;
	private String note;
	private String code;

	private ArrayList<String> category;
	private Shop shopObject;
	private Brand brandObject;
	private Resource imageObject;
	private Content contentObject;
	//private Collection collectionObject;

	private ArrayList<ProductSize> sizelist;

	private ArrayList<Product> products;

	private FavoriteItem userFavorite;

    private ArrayList<DeliverInfo> deliverInfos;

	private ArrayList<DeliverInfo> visitDeliverInfos;

	private Boolean image_flag;

    //

	public Product() {
	}

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

	public Integer getShop() {
		return shop;
	}

	public void setShop(Integer shop) {
		this.shop = shop;
	}

	public ArrayList<String> getCategory(){ return category; }

	public void setCategory(ArrayList<String> category) { this.category = category; }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isSale() {
		return sale;
	}

	public int getSaleRate() {
		return sale_rate;
	}

	public int getSalePrice() {
		return sale_price;
	}

	public int getPriceCancel() {
		return isSale() ? getPrice() : 0;
	}

	public int getPriceCurrent() {
		return isSale() ? getSalePrice() : getPrice();
	}

	public boolean hasStock() {
		return stock == null || stock > 0;
	}

	public boolean isSold() {
		return !hasStock();
	}

	public String getCountry() { return this.country; }

	public void setCountry(String country) { this.country = country; }

	public String getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(String totalsize) {
		this.totalsize = totalsize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

    public ArrayList<DeliverInfo> getDeliverInfos() {
        return deliverInfos;
    }

    public void setDeliverInfos(ArrayList<DeliverInfo> deliverInfos) {
        this.deliverInfos = deliverInfos;
    }

	public ArrayList<DeliverInfo> getVisitDeliverInfos() {
		return visitDeliverInfos;
	}

	public void setVisitDeliverInfos(ArrayList<DeliverInfo> visitDeliverInfos) {
		this.visitDeliverInfos = visitDeliverInfos;
	}


	public ArrayList<ProductSize> getProductSizes() {
		return sizelist;
	}

	public void setProductSizes(ArrayList<ProductSize> productSizes) {
		this.sizelist = productSizes;
	}


	public String getGenderName() {
		switch (gender) {
			case 1:
				return "공용";
			case 2:
				return "남";
			case 3:
				return "여";
		}

		return "";
	}

	public String getMaterial() {
		return material;
	}

	public String getCollectionDescription() {
		return collection_description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getShopImagePath() {
		return shopImagePath;
	}

	public void setShopImagePath(String shopImagePath) {
		this.shopImagePath = shopImagePath;
	}

	public int getShopManager() {
		return shopManager;
	}

	public String getShopManagerNickname() {
		return shopManagerNickname;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

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

	public boolean getBlockMessage() {
		return blockMessage;
	}

	public void setBlockMessage(boolean value) {
		this.blockMessage = value;
	}

	//

	public Shop getShopObject() {
		return shopObject;
	}

	public void setShopObject(Shop shopObject) {
		this.shopObject = shopObject;
	}

	public Brand getBrand() {
		return brandObject;
	}

	public void setBrand(Brand brand) {
		this.brandObject = brand;
	}

	public Brand getBrandObject() {
		return brandObject;
	}

	public void setBrandObject(Brand brand) {
		this.brandObject = brand;
	}

	public Resource getImageObject() {
		return imageObject;
	}

	public void setImageObject(Resource imageObject) {
		this.imageObject = imageObject;
	}

	public void setNote(String note) { this.note = note; }

	public String getNote() { return this.note; }

	public void setCode(String code) { this.code = code; }

	public String getCode() { return this.code; }


	/*
	public Collection getCollectionObject() {
		return collectionObject;
	}

	public void setCollectionObject(Collection collectionObject) {
		this.collectionObject = collectionObject;
	}
	*/

	public Content getContentObject() {
		return contentObject;
	}

	public void setContentObject(Content contentObject) {
		this.contentObject = contentObject;
	}

	public void setRealsizeimagepath(String realsizeimagepath) {
		this.realsizeimagepath = realsizeimagepath;
	}

	public String getRealsizeimagepath() {
		return this.realsizeimagepath;
	}

	public void setRealsizecomment(String realsizecomment) {
		this.realsizecomment = realsizecomment;
	}

	public String getRealsizecomment() {
		return this.realsizecomment;
	}
	//

	public Boolean getImage_flag() { return this.image_flag; }

	public void setImage_flag(Boolean image_flag) { this.image_flag = image_flag; }

	@Override
	public Type getImagesType() {
		return Type.PRODUCT;
	}

	@Override
	public int getImagesSize() {
		return 1; //collectionObject.getImages() != null ? collectionObject.getImages().totalsize() + 1 : 1;
	}

	@Override
	public Resource getImagesResource(int i) {
		//if (i == 0)
		return imageObject;
		//else
		//	return collectionObject.getImages().get(i - 1);
	}

	//

	@Override
	public int getProductRelationKey() {
		return relation;
	}

	@Override
	public int getProductRelationCount() {
		return /*products != null ? products.totalsize() : */product;
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

	public boolean isNew() {
		return DateUtil.checkDay(date, 2);
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
		dest.writeInt(shop);
		dest.writeStringList(category);
		dest.writeString(note);
		dest.writeInt(relation);
		dest.writeInt(price);
		dest.writeInt(sale ? 1 : 0);
		dest.writeInt(sale_rate);
		dest.writeInt(sale_price);
		dest.writeString(code);
		dest.writeValue(stock);
		dest.writeString(totalsize);
		dest.writeString(country);
		dest.writeString(color);
		dest.writeInt(gender);
		dest.writeString(material);
		dest.writeString(collection_description);
		dest.writeString(url);
		dest.writeInt(favorite);
		dest.writeInt(favoriteList);
		dest.writeInt(hit);
		dest.writeSerializable(date);
		dest.writeString(realsizecomment);
		dest.writeString(realsizeimagepath);

		dest.writeString(shopImagePath);
		dest.writeInt(shopManager);
		dest.writeString(shopManagerNickname);
		dest.writeString(brandName);
		dest.writeString(imagePath);
		//dest.writeString(imageThumbnailPath);
		dest.writeInt(product);
		dest.writeInt(blockMessage ? 1 : 0);

		dest.writeParcelable(shopObject, flags);
		dest.writeParcelable(brandObject, flags);
		dest.writeParcelable(imageObject, flags);
		dest.writeParcelable(contentObject, flags);
		//dest.writeParcelable(collectionObject, flags);

		dest.writeTypedList(products);

		dest.writeTypedList(sizelist);
        dest.writeTypedList(deliverInfos);
		dest.writeTypedList(visitDeliverInfos);

		dest.writeParcelable(userFavorite, flags);
		dest.writeValue(image_flag);
	}

	private Product(Parcel in) {
		key = in.readInt();
		name = in.readString();
		shop = in.readInt();
		category = new ArrayList<String>();
		in.readStringList(category);
		note = in.readString();
		relation = in.readInt();
		price = in.readInt();
		sale = in.readInt() == 1;
		sale_rate = in.readInt();
		sale_price = in.readInt();
		code = in.readString();
		stock = (Integer) in.readValue(Integer.class.getClassLoader());
		totalsize = in.readString();
		country = in.readString();
		color = in.readString();
		gender = in.readInt();
		material = in.readString();
		collection_description = in.readString();
		url = in.readString();
		favorite = in.readInt();
		favoriteList = in.readInt();
		hit = in.readInt();
		date = (Date) in.readSerializable();
		realsizecomment = in.readString();
		realsizeimagepath = in.readString();

		shopImagePath = in.readString();
		shopManager = in.readInt();
		shopManagerNickname = in.readString();
		brandName = in.readString();
		imagePath = in.readString();
		//imageThumbnailPath = in.readString();
		product = in.readInt();
		blockMessage = in.readInt() == 1;

		shopObject = in.readParcelable(Shop.class.getClassLoader());
		brandObject = in.readParcelable(Brand.class.getClassLoader());
		imageObject = in.readParcelable(Resource.class.getClassLoader());
		contentObject = in.readParcelable(Content.class.getClassLoader());
		//collectionObject = in.readParcelable(Collection.class.getClassLoader());

		in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);

		in.readTypedList(sizelist = new ArrayList<ProductSize>(), ProductSize.CREATOR);
        in.readTypedList(deliverInfos = new ArrayList<DeliverInfo>(), DeliverInfo.CREATOR);
		in.readTypedList(visitDeliverInfos = new ArrayList<DeliverInfo>(), DeliverInfo.CREATOR);

		userFavorite = in.readParcelable(FavoriteItem.class.getClassLoader());
		image_flag = (Boolean)in.readValue(Boolean.class.getClassLoader());
	}

	public static final Creator<Product> CREATOR = new Creator<Product>() {

		@Override
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		@Override
		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}
