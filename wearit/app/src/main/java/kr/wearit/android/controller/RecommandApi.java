package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.wearit.android.model.Recommand;

/**
 * Created by TAEK on 2016. 1. 13..
 */
public class RecommandApi extends Api {

    public static void getList(OnListener<ArrayList<Recommand>> handler) {
        get("/recommand", handler, new TypeToken<Response<ArrayList<Recommand>>>() {
        }.getType());
    }

}