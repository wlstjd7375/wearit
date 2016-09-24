package kr.wearit.android.controller;

import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Shop;
import kr.wearit.android.model.ShopArgs;
import kr.wearit.android.model.ShopBanner;
import kr.wearit.android.model.ShopConcept;
import kr.wearit.android.model.ShopPlace;

public class ShopApi extends Api {

    public static void get(int key, OnListener<Shop> handler) {
        get("/shop/" + key, handler, new TypeToken<Response<Shop>>() {}.getType());
    }

    public static void getForRefresh(Shop item, OnListener<Shop> handler) {
        get("/shop/" + item.getKey() + "?hit=n", handler, new TypeToken<Response<Shop>>() {}.getType());
    }

    public static void getShopBannerList(OnListener<Pagination<ShopBanner>> handler) {
        get("/shop/banner?order=order&page=1",handler, new TypeToken<Response<Pagination<ShopBanner>>>() {}.getType());
    }

    public static void getPage(ShopArgs args, int page, OnListener<Pagination<Shop>> handler) {
        get(args.getUrl(page), handler, new TypeToken<Response<Pagination<Shop>>>() {}.getType());
    }

    public static void getList(ShopArgs.Profile args, OnListener<ArrayList<Shop>> handler) {
        get("/me/shop", handler, new TypeToken<Response<ArrayList<Shop>>>() {}.getType());
    }

    public static void getList(ShopArgs.All all, OnListener<ArrayList<Shop>> handler) {
        get("/shop/list", handler, new TypeToken<Response<ArrayList<Shop>>>() {}.getType());
    }

    public static void getPlaceTree(final OnListener<ArrayList<ShopPlace>> handler) {
        get("/shop/place/tree", new OnDefaultListener<ArrayList<ShopPlace>>() {

            @Override
            public void onSuccess(ArrayList<ShopPlace> data) {
                ArrayList<ShopPlace> tree = new ArrayList<ShopPlace>();
                SparseArray<ShopPlace> hash = new SparseArray<ShopPlace>();

                for (ShopPlace item : data) {
                    if (item.getParent() == null) {
                        hash.put(item.getKey(), item);
                        tree.add(item);
                    } else {
                        ShopPlace parent = hash.get(item.getParent());

                        if (parent.getChildren() == null)
                            parent.setChildren(new ArrayList<ShopPlace>());

                        parent.getChildren().add(item);
                    }
                }

                handler.onSuccess(tree);
            }

            @Override
            public void onFail() {
                handler.onFail();
            }
        }, new TypeToken<Response<ArrayList<ShopPlace>>>() {}.getType());
    }

    public static void getConceptList(OnListener<ArrayList<ShopConcept>> handler) {
        get("/shop/concept", handler, new TypeToken<Response<ArrayList<ShopConcept>>>() {}.getType());
    }

    //

    public static void addFavorite(Shop item, OnAuthListener<Void> handler) {
        post("/shop/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
    }

    public static void removeFavorite(Shop item, OnAuthListener<Void> handler) {
        delete("/shop/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
    }
}
