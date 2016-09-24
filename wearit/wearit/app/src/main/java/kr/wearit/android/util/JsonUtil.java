package kr.wearit.android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import kr.wearit.android.controller.Response;

/**
 * Created by KimJS on 2016-09-07.
 */
public class JsonUtil {

    @SuppressWarnings("unused")
    private static final String TAG = JsonUtil.class.getSimpleName();

    //

    private static Gson create() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    //

    @SuppressWarnings("unchecked")
    public static <T> Response<T> parseResponse(String json, Type type) {
        System.out.println("pareseResponse" + type);
        return (Response<T>) create().fromJson(json, type);
    }

    public static String toString(Object object) {
        return create().toJson(object);
    }
}
