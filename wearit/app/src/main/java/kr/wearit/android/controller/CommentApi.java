package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.App;
import kr.wearit.android.model.Comment;
import kr.wearit.android.model.Pagination;

public class CommentApi extends Api {

	public static void getPage(int comment, OnListener<Pagination<Comment>> handler) {
		get("/comment/" + comment + "/item", handler, new TypeToken<Response<Pagination<Comment>>>() {}.getType());
	}

	public static void getCount(int comment, OnListener<Integer> handler) {
		get("/comment/" + comment + "/item/count", handler, new TypeToken<Response<Integer>>() {}.getType());
	}

	public static void add(Comment item, OnAuthListener<Integer> handler) {
		item.setUser(App.getInstance().getUser().getKey());

		if (item.getParent() == null)
			post("/comment/" + item.getComment() + "/item", item, handler, new TypeToken<Response<Integer>>() {}.getType());
		else
			post("/comment/item/" + item.getParent(), item, handler, new TypeToken<Response<Integer>>() {}.getType());
	}
}
