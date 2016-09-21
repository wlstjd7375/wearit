package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

public class FavoriteApi extends Api {

	public static void remove(int key, OnAuthListener<Void> handler) {
		delete("/favorite/item/" + key, handler, new TypeToken<Response<Void>>() {}.getType());
	}
}
