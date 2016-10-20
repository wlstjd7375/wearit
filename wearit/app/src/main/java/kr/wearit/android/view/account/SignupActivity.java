package kr.wearit.android.view.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.UserApi;
import kr.wearit.android.model.User;
import kr.wearit.android.util.EncryptPassword;
import kr.wearit.android.view.BaseActivity;

public class SignupActivity extends BaseActivity {

    private String LOG = "LoginActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etName;
    private EditText etBirthday;
    private EditText etPhone;
    private Button btSendSMS;
    private EditText etCertification;
    private Button btCertification;
    private EditText etAddress;
    private Button btFindAddress;
    private EditText etExtraAddress;
    private EditText etHeight;
    private EditText etWeight;
    private Button btSignUp;

    private User user;

    private String confirmCode;
    private boolean isConfirm;

    private static String address;
    private static String postcd;

    private Wait wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {
        mContext = this;
        isConfirm = false;
        wait = new Wait();
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("SIGN UP");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText)findViewById(R.id.etPasswordConfirm);
        etName = (EditText)findViewById(R.id.etName);
        etBirthday = (EditText)findViewById(R.id.etBirthday);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btSendSMS = (Button)findViewById(R.id.btSendSMS);
        etCertification = (EditText)findViewById(R.id.etCertification);
        btCertification = (Button)findViewById(R.id.btCertification);
        etAddress = (EditText)findViewById(R.id.etAddress);
        btFindAddress = (Button)findViewById(R.id.btFindAddress);
        etExtraAddress = (EditText)findViewById(R.id.etExtraAddress);
        etHeight = (EditText)findViewById(R.id.etHeight);
        etWeight = (EditText)findViewById(R.id.etWeight);

        btSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConfirm) {
                    UserApi.confirmPhone(etPhone.getText().toString(), new Api.OnAuthDefaultListener<String>() {
                        @Override
                        public void onSuccess(String data) {
                            if(data == null){
                                makeToast("이미 가입된 휴대폰 번호 입니다.");
                                return;
                            }
                            makeToast("인증 번호가 발송 되었습니다.");
                            System.out.println("인증 번호 : " + data);
                            confirmCode = data;
                        }
                    });
                }
            }
        });

        btCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etCertification.getText().toString();
                if(input.equals(confirmCode)){
                    makeToast("인증이 완료 되었습니다.");
                    isConfirm = true;
                    etCertification.setTextColor(Color.parseColor("#d8d8d8"));
                    etPhone.setTextColor(Color.parseColor("#d8d8d8"));
                    etPhone.setFocusable(false);
                    etPhone.setClickable(false);
                    etCertification.setFocusable(false);
                    etCertification.setClickable(false);
                }
                else{
                    makeToast("인증 번호를 다시 입력 해주세요.");
                }
            }
        });

        btSignUp = (Button)findViewById(R.id.btSignUp);
        btSignUp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormIsValid()) {
                    makeUserInfo();
                    UserApi.add(user, new Api.OnListener<User>() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(User data) {
                            makeToast("회원가입 성공!");
                            finish();
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }
            }
        });

        btFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindAddressActivity.launch(getActivity());
            }
        });
    }

    private void makeUserInfo() {
        user = new User();

        user.setId(etEmail.getText().toString());
        user.setAccount(1);
        user.setPassword(EncryptPassword.encryptSHA256(etPassword.getText().toString()));
        user.setName(etName.getText().toString());
        user.setBirthday(etBirthday.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setPostcode(postcd);
        user.setAddress1(etAddress.getText().toString());
        user.setAddress2(etExtraAddress.getText().toString());
        user.setHeight(etHeight.getText().toString());
        user.setWeight(etWeight.getText().toString());
    }

    private boolean isFormIsValid() {

        String email = etEmail.getText().toString();

        if(!isConfirm) {
            makeToast("휴대폰 인증을 해주세요.");
            return false;
        }
        if(email.equals("")) {
            makeToast("Email을 입력해 주세요.");
            return false;
        }
        if(!email.contains("@") || !email.contains(".")) {
            makeToast("Email 형식이 잘못되었습니다.");
            return false;
        }

        String password = etPassword.getText().toString();
        String password2 = etPasswordConfirm.getText().toString();
        if(password.equals("")) {
            makeToast("비밀번호를 입력해주세요.");
            return false;
        }
        if(!password.equals(password2)) {
            makeToast("비밀번호를 확인해주세요.");
            return false;
        }

        String birthday = etBirthday.getText().toString();
        if(birthday.equals("")) {
            makeToast("생년월일을 입력해주세요.");
            return false;
        }

        String authenNumber = etCertification.getText().toString();
        if(authenNumber.equals("")) {
            makeToast("휴대폰 번호를 인증해주세요.");
            return false;
        }

        //TODO 인증번호 확인

        if(etAddress.getText().toString().equals("")) {
            makeToast("주소를 입력해주세요.");
            return false;
        }
        if(etExtraAddress.getText().toString().equals("")) {
            makeToast("나머지 주소를 입력해주세요.");
            return false;
        }


        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void setAddress(String postcd, String address) {
        this.postcd = postcd;
        this.address = address;

        wait.run();
    }

    private class Wait implements Runnable{

        @Override
        public void run() {
            etAddress.setText(address);

        }
    }
}
