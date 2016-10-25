package kr.wearit.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.DeviceApi;
import kr.wearit.android.util.GcmUtil;

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final boolean LOG = Config.LOG;

    private Context mContext;
    private Thread mThread;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == 1) {
                if(mThread != null) {
                    mThread.interrupt();
                }
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    private Runnable wait = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;


    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Splash ## onResume()");
        register();
        // 이전에 로그인 한 기록이 있으면 shared preference 에서 불러와
        // User 정보(객체)를 App.getintance().mUser 에 저장해둠
        if(App.getInstance().isLogin()) {
            App.getInstance().setCouponList();
        }

        mThread = new Thread(wait);
        mThread.start();
    }

    private void register(){
        System.out.println("Splash ## Register()");
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (LOG)
            Log.d(TAG, "register // regId = " + regId);

        if (TextUtils.isEmpty(regId)) {
            // Automatically registers application on startup.
            GCMRegistrar.register(this, Const.GCM_SENDER_ID);
            return;
        }
        System.out.println(GCMRegistrar.getRegistrationId(this));

        // Device is already registered on GCM, check server.
        if (GCMRegistrar.isRegisteredOnServer(this)) {
            // Skips registration.
            return;
        }

        // Try to register again, but not in the UI thread.
        // It's also necessary to cancel the thread onDestroy(),
        // hence the use of AsyncTask instead of a raw thread.
        DeviceApi.register(regId, new Api.OnWaitListener<Void>(getActivity()) {

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
}
