package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Order;
import kr.wearit.android.model.OrderProduct;
import kr.wearit.android.model.Phone;
import kr.wearit.android.model.Point;
import kr.wearit.android.model.Settle;

/**
 * Created by Administrator on 2016-02-02.
 */
public class OrderApi extends Api{
    public static void getPoint(OnAuthListener<Point> handler) {
        get("/point", handler, new TypeToken<Response<Point>>() {
        }.getType());
    }

    public static void getSettle(OnAuthListener<Settle> handler) {
        get("/settle", handler, new TypeToken<Response<Settle>>() {
        }.getType());
    }

    public static void getList(OnAuthListener<ArrayList<Order>> handler) {
        get("/order", handler, new TypeToken<Response<ArrayList<Order>>>() {
        }.getType());
    }

    public static void get(Order order,OnAuthListener<Order> handler) {
        get("/order/" + order.getKey(), handler, new TypeToken<Response<Order>>() {
        }.getType());
    }

    public static void get(int order,OnAuthListener<Order> handler) {
        get("/order/" + order, handler, new TypeToken<Response<Order>>() {
        }.getType());
    }

    public static void add(Order order,  OnAuthListener<Integer> handler) {
        post("/order", order, handler, new TypeToken<Response<Integer>>() {
        }.getType());
    }

    public static void phone(Phone phone, OnAuthDefaultListener<Integer> handler) {
        post("/payment/phone", phone, handler, new TypeToken<Response<Integer>>() {
        }.getType());
    }

    public static void addDIrect(Order order,  OnAuthListener<Integer> handler) {

        post("/order/direct", order, handler, new TypeToken<Response<Integer>>() {
        }.getType());
    }

    public static void cancel(Order order, OnAuthListener<Void> handler) {
        put("/order/" + order.getKey() + "/cancel", handler, new TypeToken<Response<Void>>() {
        }.getType());
    }

    public static void refund(OrderProduct product, OnAuthListener<Boolean> handler) {
        put("/order/" + product.getKey() + "/refund", handler, new TypeToken<Response<Boolean>>() {
        }.getType());
    }

    public static void change(OrderProduct product, OnAuthListener<Boolean> handler) {
        put("/order/" + product.getKey() + "/exchange", handler, new TypeToken<Response<Boolean>>() {
        }.getType());
    }

    public static void question(OrderProduct product, OnAuthListener<Boolean> handler) {
        put("/order/" + product.getKey() + "/question", handler, new TypeToken<Response<Boolean>>() {
        }.getType());
    }

    public static void remove(int order, OnAuthListener<Void> handler) {
        delete("/order/" + order, handler, new TypeToken<Response<Void>>() {
        }.getType());
    }

    public static void alertAdmin(Order order, OnAuthListener<Boolean> handler) {
        post("/order/alert",order,handler,new TypeToken<Response<Boolean>>() {
        }.getType());
    }
}