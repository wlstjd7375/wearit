package kr.wearit.android.controller;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.model.Device;

public final class DeviceApi extends Api {

	private static final String TAG = DeviceApi.class.getSimpleName();
	private static final boolean LOG = Config.LOG;

	//

	public static void register(String regId, OnListener<Void> listener) {
		if (LOG)
			Log.d(TAG, "register // regId = " + regId);

		Device item = new Device();
		item.setId(regId);
		item.setOs(0);

		if (App.getInstance().isLogin())
			item.setUser(App.getInstance().getUser().getKey());

		post("/device", item, listener, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void unregister(String regId, OnListener<Void> listener) {
		Log.i(TAG, "unregistering device (regId = " + regId + ")");

		delete("/device/" + regId, listener, new TypeToken<Response<Void>>() {}.getType());
	}
}
