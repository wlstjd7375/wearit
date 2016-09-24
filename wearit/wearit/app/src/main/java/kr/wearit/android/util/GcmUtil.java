package kr.wearit.android.util;

import com.google.android.gcm.GCMRegistrar;

import kr.wearit.android.App;

/**
 * Created by KimJS on 2016-09-07.
 */
public class GcmUtil {
    public static void check() {
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(App.getInstance());
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(App.getInstance());
    }

    public static void unregister() {
        // At this point all attempts to register with the app
        // server failed, so we need to unregister the device
        // from GCM - the app will try to register again when
        // it is restarted. Note that GCM will send an
        // unregistered callback upon completion, but
        // GCMIntentService.onUnregistered() will ignore it.
        GCMRegistrar.unregister(App.getInstance());
    }

    public static void destory() {
        if (GCMRegistrar.isRegistered(App.getInstance()))
            GCMRegistrar.onDestroy(App.getInstance());
    }
}
