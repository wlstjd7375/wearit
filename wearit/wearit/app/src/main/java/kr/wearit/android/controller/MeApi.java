package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Product;
import kr.wearit.android.model.Shop;

public class MeApi extends Api {

	public static void getShopList(OnAuthListener<ArrayList<Shop>> handler) {
		get("/me/shop", handler, new TypeToken<Response<ArrayList<Shop>>>() {}.getType());
	}

	public static void getProductList(OnAuthListener<ArrayList<Product>> handler) {
		get("/me/product", handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}
}
