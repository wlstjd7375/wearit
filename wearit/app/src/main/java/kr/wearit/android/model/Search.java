package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Search implements Parcelable {

	private ArrayList<Shop> shop;
	private ArrayList<Brand> brand;
	private ArrayList<Product> product;
	private ArrayList<News> news;

	public Search (){

	}

	public ArrayList<Shop> getShop() {
		return shop;
	}

	public void setShop(ArrayList<Shop> shop) {
		this.shop = shop;
	}

	public ArrayList<Brand> getBrand() {
		return brand;
	}

	public void setBrand(ArrayList<Brand> brand) {
		this.brand = brand;
	}

	public ArrayList<Product> getProduct() {
		return product;
	}

	public void setProduct(ArrayList<Product> product) {
		this.product = product;
	}

	public ArrayList<News> getNews() {
		return news;
	}

	public void setNews(ArrayList<News> news) {
		this.news = news;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(shop);
		dest.writeTypedList(brand);
		dest.writeTypedList(product);
		dest.writeTypedList(news);
	}

	private Search(Parcel in) {
		in.readTypedList(shop = new ArrayList<Shop>(), Shop.CREATOR);
		in.readTypedList(brand = new ArrayList<Brand>(), Brand.CREATOR);
		in.readTypedList(product = new ArrayList<Product>(), Product.CREATOR);
		in.readTypedList(news = new ArrayList<News>(), News.CREATOR);
	}

	public static final Creator<Search> CREATOR = new Creator<Search>() {

		@Override
		public Search createFromParcel(Parcel in) {
			return new Search(in);
		}

		@Override
		public Search[] newArray(int size) {
			return new Search[size];
		}
	};
}
