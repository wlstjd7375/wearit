package kr.wearit.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.wearit.android.R;

public class SplashActivity extends AppCompatActivity {

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
        mThread = new Thread(wait);
        mThread.start();
    }
}
