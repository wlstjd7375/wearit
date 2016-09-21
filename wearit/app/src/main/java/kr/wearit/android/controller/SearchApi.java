package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Brand;
import kr.wearit.android.model.News;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.Search;
import kr.wearit.android.model.Shop;
import kr.wearit.android.util.Util;

public class SearchApi extends Api {

	@SuppressWarnings("unused")
	@Deprecated
	private static void _get(String query, final OnListener<Search> handler) {
		final Search search = new Search();

		final Runnable post = new Runnable() {

			@Override
			public void run() {
				if (search.getShop() == null)
					return;

				if (search.getBrand() == null)
					return;

				if (search.getProduct() == null)
					return;

				if (search.getNews() == null)
					return;

				handler.onSuccess(search);
			}
		};

		getShop(query, new OnListener<ArrayList<Shop>>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(ArrayList<Shop> data) {
				search.setShop(data);

				post.run();
			}

			@Override
			public void onFail() {
				search.setShop(new ArrayList<Shop>());

				post.run();
			}
		});

		getBrand(query, new OnListener<ArrayList<Brand>>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(ArrayList<Brand> data) {
				search.setBrand(data);

				post.run();
			}

			@Override
			public void onFail() {
				search.setBrand(new ArrayList<Brand>());

				post.run();
			}
		});

		getProduct(query, new OnListener<ArrayList<Product>>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(ArrayList<Product> data) {
				search.setProduct(data);

				post.run();
			}

			@Override
			public void onFail() {
				search.setProduct(new ArrayList<Product>());

				post.run();
			}
		});

		getNews(query, new OnListener<ArrayList<News>>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(ArrayList<News> data) {
				search.setNews(data);

				post.run();
			}

			@Override
			public void onFail() {
				search.setNews(new ArrayList<News>());

				post.run();
			}
		});
	}

	public static void get(String query, OnListener<Search> handler) {
		get("/search?query=" + Util.encodeQueryString(query), handler, new TypeToken<Response<Search>>() {}.getType());
	}

	public static void getShop(String query, OnListener<ArrayList<Shop>> handler) {
		get("/search/shop?query=" + Util.encodeQueryString(query), handler, new TypeToken<Response<ArrayList<Shop>>>() {}.getType());
	}

	public static void getBrand(String query, OnListener<ArrayList<Brand>> handler) {
		get("/search/brand?query=" + Util.encodeQueryString(query), handler, new TypeToken<Response<ArrayList<Brand>>>() {}.getType());
	}

	public static void getProduct(String query, OnListener<ArrayList<Product>> handler) {
		get("/search/product?query=" + Util.encodeQueryString(query), handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}

	public static void getNews(String query, OnListener<ArrayList<News>> handler) {
		get("/search/news?query=" + Util.encodeQueryString(query), handler, new TypeToken<Response<ArrayList<News>>>() {}.getType());
	}
}
