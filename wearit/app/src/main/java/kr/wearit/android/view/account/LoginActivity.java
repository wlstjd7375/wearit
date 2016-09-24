package kr.wearit.android.view.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.wearit.android.R;
import kr.wearit.android.view.BaseActivity;

public class LoginActivity extends BaseActivity {

    private String LOG = "LoginActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private TextView tvSignupToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("LOGIN");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvSignupToday = (TextView)findViewById(R.id.tvSignupToday);
        tvSignupToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
