package kr.wearit.android.controller;

import android.annotation.SuppressLint;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Gender;
import kr.wearit.android.model.Key;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Shop;
import kr.wearit.android.model.Style;
import kr.wearit.android.model.StyleCategory;

public class StyleApi extends Api {

	@SuppressLint("DefaultLocale")
	public static void getPage(Key<Shop> shop, StyleCategory category, Gender gender, int page, OnListener<Pagination<Style>> handler) {
		get(String.format("/style?shop=%d&gender=%d&page=%d", shop.getKey(), gender.value, page), handler, new TypeToken<Response<Pagination<Style>>>() {}.getType());
	}

	public static void get(Style item, OnListener<Style> handler) {
		get("/style/" + item.getKey(), handler, new TypeToken<Response<Style>>() {}.getType());
	}

	public static void getForRefresh(Style item, OnListener<Style> handler) {
		get("/style/" + item.getKey() + "?hit=n", handler, new TypeToken<Response<Style>>() {}.getType());
	}

	public static void addFavorite(Style item, OnAuthListener<Void> handler) {
		post("/style/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void removeFavorite(Style item, OnAuthListener<Void> handler) {
		delete("/style/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}
}
