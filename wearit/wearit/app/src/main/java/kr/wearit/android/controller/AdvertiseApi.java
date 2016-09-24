package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Advertise;

/**
 * Created by Administrator on 2016-03-03.
 */
public class AdvertiseApi  extends Api {

    public static void getList(OnAuthListener<ArrayList<Advertise>> handler) {
        get("/advertise/list", handler, new TypeToken<Response<ArrayList<Advertise>>>() {
        }.getType());
    }

    public static void get(int key, OnWaitListener<Advertise> handler) {
        get("/advertise/" + key, handler, new TypeToken<Response<Advertise>>() {
        }.getType());
    }
}