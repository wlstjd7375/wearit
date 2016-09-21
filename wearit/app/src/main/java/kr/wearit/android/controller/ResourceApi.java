package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Resource;

public class ResourceApi extends Api {

	public static void add(String path, OnAuthListener<Resource> handler) {
		postFile("/resource", path, handler, new TypeToken<Response<Resource>>() {}.getType());
	}
}
