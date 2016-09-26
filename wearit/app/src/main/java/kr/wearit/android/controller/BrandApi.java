package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Brand;
import kr.wearit.android.model.BrandBanner;
import kr.wearit.android.model.Pagination;

public class BrandApi extends Api {

	public static void getPage(int page, OnListener<Pagination<Brand>> handler) {
		get("/brand?page="+page, handler, new TypeToken<Response<Pagination<Brand>>>() {}.getType());
	}

	public static void getBannerList(OnListener<Pagination<BrandBanner>> handler) {
		get("/brand/banner?page=1", handler, new TypeToken<Response<Pagination<BrandBanner>>>() {}.getType());
	}

	public static void getList(String group, OnListener<ArrayList<Brand>> handler) {
		get("/brand/group/" + group, handler, new TypeToken<Response<ArrayList<Brand>>>() {}.getType());
	}

	public static void get(Brand item, OnListener<Brand> handler) {
		get("/brand/" + item.getKey(), handler, new TypeToken<Response<Brand>>() {}.getType());
	}

	public static void get(int key, OnListener<Brand> handler) {
		get("/brand/" + key, handler, new TypeToken<Response<Brand>>() {}.getType());
	}

	public static void getForRefresh(Brand item, OnListener<Brand> handler) {
		get("/brand/" + item.getKey() + "?hit=n", handler, new TypeToken<Response<Brand>>() {}.getType());
	}

	//

	public static void addFavorite(Brand item, OnAuthListener<Void> handler) {
		post("/brand/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void removeFavorite(Brand item, OnAuthListener<Void> handler) {
		delete("/brand/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}
}
