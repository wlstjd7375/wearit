package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Cart;
import kr.wearit.android.model.Message;
import kr.wearit.android.model.Product;

public class MessageApi extends Api {

	public static void getCount(OnAuthListener<Integer> handler) {
		get("/me/message/count", handler, new TypeToken<Response<Integer>>() {}.getType());
	}

	public static void getListForTarget(OnAuthListener<ArrayList<Message>> handler) {
		get("/message/target", handler, new TypeToken<Response<ArrayList<Message>>>() {}.getType());
	}

	public static void getList(int target, OnAuthListener<ArrayList<Message>> handler) {
		get("/message/" + target, handler, new TypeToken<Response<ArrayList<Message>>>() {}.getType());
	}

	public static void getListForce(int target, OnAuthListener<ArrayList<Message>> handler) {
		getList(target, handler);
	}

	public static void get(int key, OnAuthListener<Message> handler) {
		get("/message/item/" + key, handler, new TypeToken<Response<Message>>() {
		}.getType());
	}

	public static void getUnread(int target, OnAuthListener<Integer> handler) {
		get("/message/" + target + "/unread", handler, new TypeToken<Response<Integer>>() {}.getType());
	}

	public static void add(int target, int shop, Message item, OnAuthListener<Integer> handler) {
		post("/message/" + target + "?shop=" + shop, item, handler, new TypeToken<Response<Integer>>() {
		}.getType());
	}

	public static void add(int target, Message item, OnAuthListener<Integer> handler) {
		post("/message/" + target, item, handler, new TypeToken<Response<Integer>>() {
		}.getType());
	}

	public static void cart(OnAuthListener<Integer> handler){
		post("/message/cart", handler, new TypeToken<Response<Integer>>() {
		}.getType());
	}

	public static void add(Product product, OnAuthListener<Integer> handler) {
		post("/message/product/" + product.getKey(), handler, new TypeToken<Response<Integer>>() {}.getType());
	}

    public static void add(int product, OnAuthListener<Integer> handler) {
        post("/message/product/" + product, handler, new TypeToken<Response<Integer>>() {}.getType());
    }

    public static void addRefund(int product, OnAuthListener<Integer> handler) {
        post("/message/refund/" + product, handler, new TypeToken<Response<Integer>>() {}.getType());
    }

    public static void addChange(int product, OnAuthListener<Integer> handler) {
        post("/message/change/" + product, handler, new TypeToken<Response<Integer>>() {}.getType());
    }

    public static void orderQuestion(int product, OnAuthListener<Integer> handler) {
        post("/message/order/" + product, handler, new TypeToken<Response<Integer>>() {}.getType());
    }

	public static void add(Cart cart, OnAuthListener<Message> handler) {
		post("/message/cart/" + cart.getKey(), handler, new TypeToken<Response<Message>>() {}.getType());
	}

	public static void addForQuestion(Message item, OnAuthListener<Integer> handler) {
		item.setType(Message.Type.QUESTION.value);

		post("/message/question", item, handler, new TypeToken<Response<Integer>>() {}.getType());
	}


    public static void remove(OnAuthListener<Void> handler) {
		delete("/message", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void remove(int target, OnAuthListener<Void> handler) {
		delete("/message/" + target, handler, new TypeToken<Response<Void>>() {}.getType());
	}
}
