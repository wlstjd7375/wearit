package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.model.Coupon;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.User;

/**
 * Created by IISLab on 2016-01-12.
 */
public class CouponApi extends Api {

    public static void get(User user, OnDefaultListener<Coupon> handler) {
        get("/coupon/" + user.getKey(), handler, new TypeToken<Response<Coupon>>() {
        }.getType());
    }

    public static void getList(User user, OnDefaultListener<Pagination<Coupon>> handler) {
        get("/coupon/user/" + user.getKey()+"/list", handler, new TypeToken<Response<Pagination<Coupon>>>() { }.getType());
    }

}
