package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Key;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsCategory;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Shop;

public class NewsApi extends Api {

	public static void getPage(NewsCategory category, int page, OnListener<Pagination<News>> handler) {
		getPage(null, category, page, handler);
	}

	public static void getPage(Key<Shop> shop, NewsCategory category, int page, OnListener<Pagination<News>> handler) {
		String url = "/news?";
		if(shop == null)
		{
			 url = "/news?";
		}
		else
		{
			url += "shop=" + shop.getKey() + "&";
		}

		if (category != NewsCategory.ALL)
		{
			url += "category=" + category.value + "&";
		}
		url += "order=" + "order&";
		url += "page=" + page;

		get(url, handler, new TypeToken<Response<Pagination<News>>>() {}.getType());
	}

	public static void get(int key, OnListener<News> handler) {
		get("/news/" + key, handler, new TypeToken<Response<News>>() {}.getType());
	}

	public static void get(News item, OnListener<News> handler) {
		get("/news/" + item.getKey(), handler, new TypeToken<Response<News>>() {}.getType());
	}

	public static void getForRefresh(News item, OnListener<News> handler) {
		get("/news/" + item.getKey() + "?hit=n", handler, new TypeToken<Response<News>>() {}.getType());
	}

	public static void addFavorite(News item, OnAuthListener<Void> handler) {
		post("/news/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void removeFavorite(News item, OnAuthListener<Void> handler) {
		delete("/news/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}
}
