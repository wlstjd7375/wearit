package kr.wearit.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.gson.Gson;
import com.tsengvn.typekit.Typekit;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import kr.wearit.android.controller.AdvertiseApi;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.CouponApi;
import kr.wearit.android.controller.DeviceApi;
import kr.wearit.android.controller.HomeApi;
import kr.wearit.android.controller.MileageApi;
import kr.wearit.android.controller.OrderApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.controller.ShopApi;
import kr.wearit.android.model.Advertise;
import kr.wearit.android.model.Coupon;
import kr.wearit.android.model.Home;
import kr.wearit.android.model.Mileage;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Point;
import kr.wearit.android.model.ProductCategory;
import kr.wearit.android.model.Settle;
import kr.wearit.android.model.ShopPlace;
import kr.wearit.android.model.User;
import kr.wearit.android.util.GcmUtil;
import kr.wearit.android.util.ImageUtil;

/**
 * Created by KimJS on 2016-09-02.
 */
public class App extends Application {
    //@#!@#
    private static final String TAG = App.class.getSimpleName() + "##";
    private static final boolean LOG = Config.LOG;
    //
    public interface OnUserStateListener {
        void onStateChanged(User user);
    }

    public interface OnMessageStateListener {
        void onStateChanged(int value);
    }

    public interface OnCartStateListener {
        void onStateChanged(int value);
    }
    //
    private static final String PREF_USER = "_user_obj_";

    private static final String PREF_USER_ID = "_user_id_";
    private static final String PREF_USER_NAME = "_user_name_";
    private static final String PREF_USER_PASSWORD = "_user_password_";
    private static final String PREF_USER_LOGIN_PASSWD = "_user_login_passwd";
    private static final String PREF_USER_FACEBOOK = "_user_facebook_";
    private static final String PREF_USER_KAKAO = "_user_kakao_";
    private static final String PREF_APP = "_app_";
    private static final String PREF_SERVER = "_server_";
    private static final String PREF_NOTICE = "_notice_";
    private static final String PREF_ADVERTIES = "_advertise";
    //
    private static App instance;
    //
    public static App getInstance() {
        return instance;
    }
    //
    private User mUser;
    private List<WeakReference<OnUserStateListener>> mUserStateListener = Collections.synchronizedList(new ArrayList<WeakReference<OnUserStateListener>>());
    private String mApp;

    //스크린 넓이
    private int mScreenWidth;

    //메인프레그먼트로 보내기
    private int activityNextAction;

    private static ArrayList<ShopPlace> placeList;
    private static ArrayList<Home> homeList;
    private static HashMap<Integer, ProductCategory> productCategoryList;
    private static ArrayList<Coupon> couponList;
    private static ArrayList<Advertise> advertiseList;
    private static Point point;
    private static Settle settle;

    private Observable mPostObservable = new Observable();
    //
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

//        setDefaultFont();

        if (LOG)
            Log.d(TAG, "onCreate // 2014-12-29");

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSansCJKkr-Regular.otf"))
                .addBold(Typekit.createFromAsset(this, "NotoSansCJKkr-Medium.otf"));

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e(TAG, paramThrowable.getMessage(), paramThrowable);
            }
        });

        instance = this;

        ImageUtil.init(this);

        mApp = PreferenceManager.getDefaultSharedPreferences(this).getString(PREF_APP, null);

        if (mApp == null) {
            mApp = UUID.randomUUID().toString();

            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PREF_APP, mApp).commit();
        }

        /*
        GcmUtil.check();
        //GcmUtil.unregister();
        //GCMRegistrar.setRegisteredOnServer(App.getInstance(), false);

        Config.Server server = getServer();

        Config.load(server);

        switch (server) {
            case DEV:
                Toast.makeText(this, "DEV", Toast.LENGTH_SHORT).show();
                break;
            case STG:
                Toast.makeText(this, "STG", Toast.LENGTH_SHORT).show();
                break;
            case PRD:
                break;
        }
        */
    }

    //쓰는것
    //Login
    public User getUser() {
        if(mUser == null) { // 앱 시작 or 로그인이 되어있지 않은 상태
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            String json = pref.getString(PREF_USER, null);
            if(json == null) {
                //로그인정보 없음
                mUser = null;
            }
            else {
                //로그인정보 존재
                Gson gson = new Gson();
                User user = gson.fromJson(json, User.class);
                mUser = user;
            }
        }
        return mUser;
    }
    public boolean isLogin() {
        if(getUser() == null) {
            if(LOG) {
                Log.d(TAG, "Login Status: false");
            }
            return false;
        }
        else {
            if(LOG) {
                Log.d(TAG, "Login Status: true");
            }
            return true;
        }
    }

    public void login(User user, String loginPasswd) {
        //setUser(user, true, loginPasswd);
        setUser(user);
    }

    public void logout() {
        //setUser(null, true, null);
        setUser(null);
    }
    public void modifyUser(User user) {
        //setUser(user, null);
    }


    public void setUser(User user) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        if (LOG)
            Log.d(TAG, "setUser // user = " + user);

        if (LOG)
            if (user != null)
                Log.d(TAG, "setUser // id = " + user.getId() + ", password = " + user.getPassword());

        if (user != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            editor.putString(PREF_USER, json);
        }
        else { //Logout
            editor.putString(PREF_USER, null);
        }

        editor.commit();
        mUser = user;
    }

    public void setNextActivityAction(int action) {
        activityNextAction = action;
    }
    public int getNextActivityAction() {
        return activityNextAction;
    }

    //
    public String getApp() {
        return mApp;
    }

    public int getScreenWidth() {
        //TODO 한번씩 터진다 최초에 받아오고 그 이후엔 변수로 리턴
        if(mScreenWidth == 0) {
            try {
                mScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            } catch(Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }

        return mScreenWidth;
    }


    //안쓰는거
    public String[] getAccountPreference() {
//        User user = DBManager.getManager(getApplicationContext()).getUser();
//
//        if(user == null) {
//            return null;
//        }
//        else {
//            return new String[] { user.getId(), user.getPassword() };
//        }

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String id = pref.getString(PREF_USER_ID, null);
        String password = pref.getString(PREF_USER_PASSWORD, null);
        String loginpasswd = pref.getString(PREF_USER_LOGIN_PASSWD, null);
        String facebook = pref.getString(PREF_USER_FACEBOOK, null);
        String kakao = pref.getString(PREF_USER_KAKAO, null);
        String name = pref.getString(PREF_USER_NAME, null);

        if (LOG)
            Log.d(TAG, "getAccountPreference // id = " + id + ", password = " + password + ", facebook = " + facebook);

        if (TextUtils.isEmpty(facebook)) {
            if (id == null || password == null)
                return null;
        } else {
            if (id == null)
                return null;
        }

        return new String[] { id, password, loginpasswd, kakao, facebook, name };
    }

    public String getAccountPreferenceId() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String id = pref.getString(PREF_USER_ID, null);
        String facebook = pref.getString(PREF_USER_FACEBOOK, null);
        String kakao = pref.getString(PREF_USER_KAKAO, null);

        return (TextUtils.isEmpty(facebook) && TextUtils.isEmpty(kakao)) ? id : null;
    }

    public void login(User user) {
        setUser(user, false, null);

    }





    public Config.Server getServer() {
        return Config.Server.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(PREF_SERVER, Config.Server.PRD.name()));
    }

    public void setServer(Config.Server value) {
        PreferenceManager.getDefaultSharedPreferences(this).edit() //
                .putString(PREF_SERVER, value.name()) //
                .commit();
    }

    public int getNotice() {
        return PreferenceManager.getDefaultSharedPreferences(this) //
                .getInt(PREF_NOTICE, 0);
    }

    public void setNotice(int value) {
        PreferenceManager.getDefaultSharedPreferences(this).edit() //
                .putInt(PREF_NOTICE, value) //
                .commit();
    }

    public synchronized void addOnUserStateListener(OnUserStateListener listener) {
        mUserStateListener.add(new WeakReference<OnUserStateListener>(listener));

        listener.onStateChanged(mUser);
    }

    public synchronized void removeOnUserStateListener(OnUserStateListener listener) {
        for (int i = mUserStateListener.size() - 1; i >= 0; i--)
            if (mUserStateListener.get(i).get() == listener)
                mUserStateListener.remove(i);
    }



    //
    private void setUser(User user, boolean facebook, String loginPasswd) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        if (LOG)
            Log.d(TAG, "setUser // user = " + user + ", facebook = " + facebook + ", token = " + loginPasswd);

        if (LOG)
            if (user != null)
                Log.d(TAG, "setUser // id = " + user.getId() + ", password = " + user.getPassword());

        if (user != null) {
            if(loginPasswd != null) {
                editor.putString(PREF_USER_NAME, user.getName());
                editor.putString(PREF_USER_ID, user.getId()).putString(PREF_USER_PASSWORD, user.getPassword()).putString(PREF_USER_LOGIN_PASSWD, loginPasswd);
                editor.putString(PREF_USER_FACEBOOK, user.getFacebookid()).putString(PREF_USER_KAKAO, user.getKakaoid());
            }
            else {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                editor.putString(PREF_USER_NAME, user.getName());
                editor.putString(PREF_USER_ID, user.getId()).putString(PREF_USER_PASSWORD, user.getPassword()).putString(PREF_USER_LOGIN_PASSWD, pref.getString(PREF_USER_LOGIN_PASSWD, null));
                editor.putString(PREF_USER_FACEBOOK, user.getFacebookid()).putString(PREF_USER_KAKAO, user.getKakaoid());
            }
        }
        else
            editor.putString(PREF_USER_PASSWORD, null);

        editor.commit();

        mUser = user;

        if(user != null) {
            setCouponList();
        }

        for (WeakReference<OnUserStateListener> l : mUserStateListener) {
            OnUserStateListener listener = l.get();

            if (listener == null)
                continue;

            listener.onStateChanged(user);
        }

        final String regId = GCMRegistrar.getRegistrationId(this);

        if (LOG)
            Log.d(TAG, "register // regId = " + regId);

        if (TextUtils.isEmpty(regId)) {
            // Automatically registers application on startup.
            GCMRegistrar.register(this, Const.GCM_SENDER_ID);
            return;
        }

        // Device is already registered on GCM, check server.
        if (GCMRegistrar.isRegisteredOnServer(this)) {
            // Skips registration.
            return;
        }

        // Try to register again, but not in the UI thread.
        // It's also necessary to cancel the thread onDestroy(),
        // hence the use of AsyncTask instead of a raw thread.
        DeviceApi.register(regId, new Api.OnAuthDefaultListener<Void>() {

            @Override
            public void onSuccess(Void data) {
                if (LOG)
                    Log.d(TAG, "register.onSuccess");

                GCMRegistrar.setRegisteredOnServer(App.getInstance(), true);
            }

            @Override
            public void onFail() {
                if (LOG)
                    Log.d(TAG, "register.onFail");
                GcmUtil.unregister();
            }
        });
    }

    public void setPlaceList(){
        placeList = new ArrayList<ShopPlace>();
        ShopApi.getPlaceTree(new Api.OnDefaultListener<ArrayList<ShopPlace>>() {

            @Override
            public void onSuccess(ArrayList<ShopPlace> data) {
                placeList.addAll(data);
            }
        });
    }

    public void setPlaceList(ArrayList<ShopPlace> list){
        placeList = list;
    }

    public void setHomeList(){
        homeList = new ArrayList<Home>();
        HomeApi.getList(new Api.OnDefaultListener<ArrayList<Home>>() {

            @Override
            public void onSuccess(ArrayList<Home> data) {
                homeList.addAll(data);
            }

        });
    }

    public ArrayList<Home> getHomeList(){
        return this.homeList;
    }

    public ArrayList<ShopPlace> getPlaceList(){
        return placeList;
    }

    public void setProductCategoryList() {
        productCategoryList = new HashMap<Integer,ProductCategory>();
        ProductApi.getCategoryTree(new Api.OnListener<ArrayList<ProductCategory>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(ArrayList<ProductCategory> data) {


                System.out.println("PRoductCategory Size : " + productCategoryList.size());
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void setProductCategoryList(ArrayList<ProductCategory> categoryList) {
        productCategoryList = new HashMap<Integer, ProductCategory>();
        for (int i = 0; i < categoryList.size(); i++) {
            productCategoryList.put(categoryList.get(i).getKey(), categoryList.get(i));
        }

    }

    public void setUserMileage(Activity activity) {
        MileageApi.getList(App.getInstance().getUser(), new Api.OnWaitListener<Pagination<Mileage>>(activity) {
            @Override
            public void onSuccess(Pagination<Mileage> data) {
                ArrayList<Mileage> list = data.getList();
                int totalAdd = 0;
                int totalSub = 0;
                for (int i = 0; i < list.size(); i++) {
                    Mileage item = list.get(i);
                    if (item.getAdd().equals("0")) {
                        if (!item.getSub().equals("0")) {
                            totalSub += Integer.valueOf(item.getSub());
                        }
                    } else {
                        totalAdd += Integer.valueOf(item.getAdd());
                    }
                }

                getUser().setTotal_mileage_add(totalAdd);
                getUser().setTotal_mileage_sub(totalSub);
            }
        });
    }

    public HashMap<Integer,ProductCategory> getProductCategoryList() { return productCategoryList; }

    public void setCouponList(){
        CouponApi.getList(mUser, new Api.OnWaitListener<Pagination<Coupon>>() {

            @Override
            public void onSuccess(Pagination<Coupon> data) {
                couponList = data.getList();
            }
        });
    }

    public ArrayList<Coupon> getCouponList(){
        setCouponList();
        return couponList;
    }

    public void setAdvertise() {
        AdvertiseApi.getList(new Api.OnAuthListener<ArrayList<Advertise>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ArrayList<Advertise> data) {
                advertiseList = data;
            }

            @Override
            public void onFail() {

            }
        });
    }

    public Advertise getAdvertise() {
        if(advertiseList == null) {
            return null;
        }

        if(advertiseList.size() == 0) {
            return null;
        }

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int id = pref.getInt(PREF_ADVERTIES, -1);

        System.out.println("Shared Preference : " + id);

        if(id == advertiseList.get(0).getKey()){
            return null;
        }
        else {
            return advertiseList.get(0);
        }
    }

    public int getPointRate() {
        return point.getRate();
    }

    public void setPoint() {
        OrderApi.getPoint(new Api.OnAuthListener<Point>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Point data) {
                point = data;
            }

            @Override
            public void onFail() {

            }
        });
    }

    public Settle getSettle() {
        return settle;
    }

    public void setSettle() {
        OrderApi.getSettle(new Api.OnAuthListener<Settle>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Settle data) {
                settle = data;
            }

            @Override
            public void onFail() {

            }
        });
    }
    public Observable getPostObservable() {
        return mPostObservable;
    }

}
