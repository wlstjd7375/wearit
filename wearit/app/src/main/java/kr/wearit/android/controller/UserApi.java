package kr.wearit.android.controller;

import android.text.TextUtils;

import com.google.android.gcm.GCMRegistrar;
import com.google.gson.reflect.TypeToken;

import kr.wearit.android.App;
import kr.wearit.android.model.PasswordRequest;
import kr.wearit.android.model.User;

public class UserApi extends Api {

	public static void checkId(String id, OnListener<Boolean> handler) {
		get("/user/check/id/" + id, handler, new TypeToken<Response<Boolean>>() {}.getType());
	}

	public static void checkNickname(String nickname, OnListener<Boolean> handler) {
		get("/user/check/nickname/" + nickname, handler, new TypeToken<Response<Boolean>>() {}.getType());
	}

	public static void checkPhone(String phone, OnListener<Boolean> handler) {
		get("/user/check/phone/" + phone, handler, new TypeToken<Response<Boolean>>() {}.getType());
	}

	public static void add(User user, OnListener<User> handler) {
		post("/user/data", user, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void get(OnAuthListener<User> handler) {
		get("/account", handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void login(String id, String password, OnListener<User> handler) {
		User user = new User();
		user.setAccountType(User.Account.ID);
		user.setId(id);
		user.setPassword(password);

        System.out.println("Login Api - Id : " + id + ", Passwd : " + password );

		String gcm = GCMRegistrar.getRegistrationId(App.getInstance());

		if (!TextUtils.isEmpty(gcm))
			user.setGcm(gcm);

		post("/account", user, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void loginWithFacebook(String userId,String name ,OnListener<User> handler) {
		User user = new User();
		user.setAccountType(User.Account.FACEBOOK);
		user.setFacebookid(userId);

		post("/account", user, handler, new TypeToken<Response<User>>() {}.getType());
	}

	public static void loginWithKaKao(String userId,String name ,OnListener<User> handler) {
		User user = new User();
		user.setAccountType(User.Account.KAKAO);
		user.setKakaoid(userId);

		post("/account", user, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void logout(OnListener<Void> handler) {
		delete("/account", handler, new TypeToken<Response<Void>>() {
		}.getType());
	}

	public static void modifyImage(String path, OnAuthListener<User> handler) {
		postFile("/account/image", path, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void removeImage(OnAuthListener<User> handler) {
		delete("/account/image", handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void modifyData(User user,OnAuthListener<User> handler){
		put("/user/" + user.getKey(), user, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void modifyNickname(String nickname, OnAuthListener<User> handler) {
		User user = new User();

		put("/account/nickname", user, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void modifyPassword(String password, OnAuthListener<User> handler) {
		User user = new User();
		user.setPassword(password);

		put("/account/password", user, handler, new TypeToken<Response<User>>() {
		}.getType());
	}

	public static void modifyGcmNotify(boolean gcm, OnAuthListener<User> handler) {
		User user = new User();
		user.setGcmNotify(gcm);

		put("/account/gcm/notify", user, handler, new TypeToken<Response<User>>() {}.getType());
	}

	public static void modifyGcmMessage(boolean gcm, OnAuthListener<User> handler) {
		User user = new User();
		user.setGcmMessage(gcm);

		put("/account/gcm/message", user, handler, new TypeToken<Response<User>>() {}.getType());
	}

	public static void idFindRequest(String username, String userphone,  OnAuthListener<PasswordRequest> handler){

		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setName(username);
		passwordRequest.setUserphone(userphone);

		post("/account/find/id", passwordRequest, handler, new TypeToken<Response<PasswordRequest>>() {
		}.getType());

	}

	public static void passwordFindRequest(String userid, String userphone,  OnAuthListener<String> handler){

		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setUserid(userid);
		passwordRequest.setUserphone(userphone);

		post("/account/find/password", passwordRequest, handler, new TypeToken<Response<String>>() {
		}.getType());

	}
	public static void confirmPhone(String userphone,  OnAuthListener<String> handler){

		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setUserphone(userphone);

		post("/account/confirm", passwordRequest, handler, new TypeToken<Response<String>>() {
		}.getType());
	}


	public static void codePost(String code, String userphone,  OnAuthListener<PasswordRequest> handler){


		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setCode(code);
		passwordRequest.setUserphone(userphone);
		post("/account/find/code", passwordRequest, handler, new TypeToken<Response<PasswordRequest>>() {
		}.getType());

	}

	public static void getID(String name, String phoneNum, String code, OnAuthListener<String> handler){

		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setName(name);
		passwordRequest.setUserphone(phoneNum);
		passwordRequest.setCode(code);
		post("/account/show/id", passwordRequest, handler, new TypeToken<Response<String>>() {
		}.getType());

	}

	public static void changePassword(String userid, String phoneNum, String password, OnAuthListener<Boolean> handler){
		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setUserid(userid);
		passwordRequest.setUserphone(phoneNum);
		passwordRequest.setPassword(password);

		put("/account/change/password", passwordRequest, handler, new TypeToken<Response<Boolean>>() {
		}.getType());
	}

}
