package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Mileage;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.User;


/**
 * Created by IISLab on 2016-01-12.
 */
public class MileageApi extends Api {


    public static void get(User user, OnDefaultListener<Mileage> handler) {
        get("/mileage/" + user.getKey(), handler, new TypeToken<Response<Mileage>>() {
        }.getType());
    }

    public static void getList(User user, OnDefaultListener<Pagination<Mileage>> handler) {
        get("/mileage/" + user.getKey() + "/list", handler, new TypeToken<Response<Pagination<Mileage>>>() {}.getType());
    }



}
