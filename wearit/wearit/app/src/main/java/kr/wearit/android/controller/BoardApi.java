package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.model.Board;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Post;
import kr.wearit.android.model.PostComment;
import kr.wearit.android.model.Shop;
import kr.wearit.android.util.Util;


public class BoardApi extends Api {

	private static ArrayList<Board> mBoardList;

	//

	private static ArrayList<Board> getBoardListForShop(boolean shop) {
		if (!shop)
			return mBoardList;

		ArrayList<Board> list = new ArrayList<Board>();

		for (Board item : mBoardList)
			if (item.isShop())
				list.add(item);

		return list;
	}

	//

	public static void getBoardList(OnListener<ArrayList<Board>> handler) {
		getBoardList(false, handler);
	}

	public static void getBoardList(final boolean shop, final OnListener<ArrayList<Board>> handler) {
		if (mBoardList != null) {
			handler.onStart();
			handler.onSuccess(getBoardListForShop(shop));
		} else {
			get("/board", new OnListener<ArrayList<Board>>() {

				@Override
				public void onStart() {
					handler.onStart();
				}

				@Override
				public void onSuccess(ArrayList<Board> data) {
					mBoardList = data;

					handler.onSuccess(getBoardListForShop(shop));
				}

				@Override
				public void onFail() {
					handler.onFail();
				}
			}, new TypeToken<Response<ArrayList<Board>>>() {}.getType());
		}
	}

	public static void getPostPage(OnListener<Pagination<Post>> handler) {
		get("/board/post", handler, new TypeToken<Response<Pagination<Post>>>() {}.getType());
	}

	public static void getPostPage(Board board, Shop shop, String query, Board.SearchRange range, OnListener<Pagination<Post>> handler) {
		String params = "";

		if (board != null && board.getKey() > 0)
			params += (params.length() == 0 ? "?" : "&") + "board=" + board.getKey();

		if (shop != null && shop != Shop.ENTIRE)
			params += (params.length() == 0 ? "?" : "&") + "shop=" + shop.getKey();

		if (query != null)
			params += (params.length() == 0 ? "?" : "&") + "query=" + Util.encodeQueryString(query);

		if (range != null)
			params += (params.length() == 0 ? "?" : "&") + "range=" + range.value;

		get("/board/post" + params, handler, new TypeToken<Response<Pagination<Post>>>() {}.getType());
	}

	public static void getPost(int key, OnListener<Post> handler) {
		get("/board/post/" + key, handler, new TypeToken<Response<Post>>() {}.getType());
	}

	public static void addPost(Post item, OnAuthListener<Integer> handler) {
		post("/board/post", item, handler, new TypeToken<Response<Integer>>() {}.getType());
	}

	public static void removePost(Post item, OnAuthListener<Void> handler) {
		delete("/board/post/" + item.getKey(), handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void getCommentList(Post post, OnListener<ArrayList<PostComment>> handler) {
		get("/board/post/" + post.getKey() + "/comment", handler, new TypeToken<Response<ArrayList<PostComment>>>() {}.getType());
	}

	public static void addComment(PostComment item, OnAuthListener<Integer> handler) {
		item.setUser(App.getInstance().getUser().getKey());

		if (item.getParent() == null)
			post("/board/post/" + item.getPost() + "/comment", item, handler, new TypeToken<Response<Integer>>() {}.getType());
		else
			post("/board/comment/" + item.getParent(), item, handler, new TypeToken<Response<Integer>>() {}.getType());
	}
}
