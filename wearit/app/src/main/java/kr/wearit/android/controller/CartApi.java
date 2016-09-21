package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import kr.wearit.android.model.Cart;
import kr.wearit.android.model.CartDeliver;
import kr.wearit.android.model.ProductCart;

public class CartApi extends Api {

    public static final AtomicInteger CountCache = new AtomicInteger(0);

    //
    public static void getCount(OnAuthListener<Integer> handler) {
        get("/me/cart/count", handler, new TypeToken<Response<Integer>>() {}.getType());
    }

    public static void getList(OnAuthListener<CartDeliver> handler) {
        get("/cart", handler, new TypeToken<Response<CartDeliver>>() {
        }.getType());
    }

    public static void add(int sizeKey, int count,  OnAuthListener<Integer> handler) {
        Cart item = new Cart();
        item.setProductKey(sizeKey);
        item.setCount(count);

        post("/cart", item, handler, new TypeToken<Response<Integer>>() {
        }.getType());
    }

    public static void remove(ProductCart item, OnWaitListener<Void> handler) {
        delete("/cart/" + item.getKey(), handler, new TypeToken<Response<Void>>() {
        }.getType());
    }

    public static void remove(ArrayList<ProductCart> list, OnWaitListener<Void> handler) {
        ArrayList<RemoveKey> removeSet = new ArrayList<RemoveKey>();
        for(int i=0;i<list.size();i++){
            RemoveKey key = new RemoveKey(list.get(i).getKey());
            removeSet.add(key);
        }
        put("/cart",removeSet, handler, new TypeToken<Response<Void>>() {
        }.getType());
    }

    public static void edit(int cartKey, int sizeKey, int count,  OnAuthListener<Integer> handler) {
        Cart send = new Cart();
        send.setProductKey(sizeKey);
        send.setCount(count);
        send.setKey(cartKey);

        put("/cart/" + send.getKey(), send, handler, new TypeToken<Response<Integer>>() {
        }.getType());
    }


    private static class RemoveKey {
        private int key;

        public RemoveKey(int key) {
            this.key = key;
        }
    }
}
