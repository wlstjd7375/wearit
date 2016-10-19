package kr.wearit.android.view.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import kr.wearit.android.model.PasswordRequest;
import kr.wearit.android.util.EncryptPassword;
import kr.wearit.android.view.BaseActivity;

public class FindPasswordActivity extends BaseActivity {

    private String LOG = "FindPasswordActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private EditText etEmail;
    private EditText etPhone;
    private Button btSendSMS;
    private EditText etCertification;
    private EditText etNewPassword;
    private EditText etNewPasswordConfirm;
    private Button btDone;

    private String confirmCode;
    private boolean isConfirm;


    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        init();
    }

    private void init() {
        isConfirm = false;
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("FIND PASSWORD");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btSendSMS = (Button)findViewById(R.id.btSendSMS);
        etCertification = (EditText)findViewById(R.id.etCertification);
        etNewPassword = (EditText)findViewById(R.id.etNewPassword);
        etNewPasswordConfirm = (EditText)findViewById(R.id.etNewPasswordConfirm);
        btDone = (Button)findViewById(R.id.btDone);
        btDone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormIsValid()) {
                    UserApi.changePassword(etEmail.getText().toString(), etPhone.getText().toString(), EncryptPassword.encryptSHA256(etNewPassword.getText().toString()), new Api.OnAuthDefaultListener<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            showDialog();
                        }
                    });
                }
            }
        });

        btSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConfirm){
                    UserApi.passwordFindRequest(etEmail.getText().toString(), etPhone.getText().toString(), new Api.OnAuthDefaultListener<String>(){
                        @Override
                        public void onSuccess(String data){
                            if(data == null) {
                                makeToast("일치하는 회원 정보가 없습니다.");
                                return;
                            }
                            else {
                                makeToast("인증번호가 발송 되었습니다.");
                                etEmail.setTextColor(Color.parseColor("#d8d8d8"));
                                etPhone.setTextColor(Color.parseColor("#d8d8d8"));
                                etEmail.setFocusable(false);
                                etEmail.setClickable(false);
                                etPhone.setFocusable(false);
                                etPhone.setClickable(false);
                                confirmCode = data;
                            }
                        }
                    });
                }
            }
        });

    }

    private boolean isFormIsValid() {

        String email = etEmail.getText().toString();

        if(confirmCode != null && confirmCode.length() != 0 && etCertification.getText().toString().equals(confirmCode)){
            isConfirm = true;
        }

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

        String password = etNewPassword.getText().toString();
        String password2 = etNewPasswordConfirm.getText().toString();
        if(password.equals("")) {
            makeToast("비밀번호를 입력해주세요.");
            return false;
        }
        if(!password.equals(password2)) {
            makeToast("비밀번호를 확인해주세요.");
            return false;
        }

        String authenNumber = etCertification.getText().toString();
        if(authenNumber.equals("")) {
            makeToast("휴대폰 인증을 해주세요.");
            return false;
        }

        return true;
    }

    //move
    private void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.dialog_go_to_login, null);

        final AlertDialog dialog;
        dialog = new AlertDialog.Builder(this).create();

        dialog.setView(dialogView);
        TextView tvLoginOK = (TextView)dialogView.findViewById(R.id.tvLoginOK);
        tvLoginOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView tvLoginCancel = (TextView)dialogView.findViewById(R.id.tvLoginCancel);
        tvLoginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO go to main fragment,
                App.getInstance().setNextActivityAction(Const.GO_TO_MAIN_FRAGMENT);
                finish();
            }
        });
        dialog.show();
    }

    private void makeToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

}
