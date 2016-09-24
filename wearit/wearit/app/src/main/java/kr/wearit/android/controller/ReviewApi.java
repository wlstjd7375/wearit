package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Review;

public class ReviewApi extends Api {

	public static void getPage(int page, OnListener<Pagination<Review>> handler) {
		get("/review?page=" + page, handler, new TypeToken<Response<Pagination<Review>>>() {}.getType());
	}

	public static void get(Review item, OnListener<Review> handler) {
		get("/review/" + item.getKey(), handler, new TypeToken<Response<Review>>() {}.getType());
	}

	public static void getForRefresh(Review item, OnListener<Review> handler) {
		get("/review/" + item.getKey() + "?hit=n", handler, new TypeToken<Response<Review>>() {}.getType());
	}

	public static void addFavorite(Review item, OnAuthListener<Void> handler) {
		post("/review/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void removeFavorite(Review item, OnAuthListener<Void> handler) {
		delete("/review/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}
}
