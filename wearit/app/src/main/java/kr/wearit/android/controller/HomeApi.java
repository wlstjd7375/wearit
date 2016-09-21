package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Home;
import kr.wearit.android.model.Pagination;

/**
 * Created by bagjehyeon on 2015. 12. 28..
 */

public class HomeApi extends Api {

    public static void getPage(OnListener<Pagination<Home>> handler){
        get("/home", handler, new TypeToken<Response<Pagination<Home>>>() {}.getType());
    }

    public static void getList(OnListener<ArrayList<Home>> handler) {
        get("/home/list",handler, new TypeToken<Response<ArrayList<Home>>>() {}.getType());
    }

    public static void get(Home item, OnListener<Home> handler) {
        get("/home/" + item.getKey(), handler, new TypeToken<Response<Home>>() {
        }.getType());
    }

    public static void get(int item, OnListener<Home> handler) {
        get("/home/" + item, handler, new TypeToken<Response<Home>>() {
        }.getType());
    }
}
