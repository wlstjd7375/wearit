package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Notice;
import kr.wearit.android.model.Pagination;

public class NoticeApi extends Api {

	public static void getNotice(int page, OnListener<Pagination<Notice>> handler) {
		get("/notice?page=" + page, handler, new TypeToken<Response<Pagination<Notice>>>() {}.getType());
	}

	public static void getNotice(Notice item, OnListener<Notice> handler) {
		get("/notice/" + item.getKey(), handler, new TypeToken<Response<Notice>>() {}.getType());
	}
}
