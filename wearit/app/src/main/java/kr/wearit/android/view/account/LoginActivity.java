package kr.wearit.android.view.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.UserApi;
import kr.wearit.android.model.User;
import kr.wearit.android.util.EncryptPassword;
import kr.wearit.android.view.BaseActivity;

public class LoginActivity extends BaseActivity {

    private String TAG = "LoginActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private TextView tvSignupToday;

    //Login
    private EditText etID;
    private EditText etPassword;
    private Button btLogin;

    private TextView tvFindPassword;

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

        //Find Password
        tvFindPassword = (TextView)findViewById(R.id.tvFindPassword);
        tvFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FindPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Login
        etID = (EditText) findViewById(R.id.etID);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = etID.getText().toString();
                final String password = etPassword.getText().toString();
                Log.d(TAG, "id= " + id + " pw= " + password);
                if(id.equals("")) {
                    Toast.makeText(mContext, "ID 를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if(password.equals("")) {
                    Toast.makeText(mContext, "PASSWORD 를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    UserApi.login(id, EncryptPassword.encryptSHA256(password), new Api.OnDefaultListener<User>("아이디와 비밀번호를 확인해주세요.") {

                        @Override
                        public void onSuccess(User data) {
                            User user = new User();
                            user.setId(id);
                            user.setPassword(EncryptPassword.encryptSHA256(password));

                            App.getInstance().login(data, EncryptPassword.encryptSHA256(password));
                            App.getInstance().setCouponList();
                            //Activity를 종료하고 MainActivity 에 Login 이 성공했다는 것을 알려줌
                            //MainFragment로 가기위해
                            Intent intent = getIntent();
                            setResult(Const.FROM_LOGIN_SUCCESS, intent);
                            finish();
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(mContext, "로그인이 실패했습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
