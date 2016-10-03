package kr.wearit.android.view.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.model.User;
import kr.wearit.android.view.BaseActivity;

public class MyInfoActivity extends BaseActivity {

    private String LOG = "MyInfoActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etName;
    private EditText etBirthday;
    private EditText etPhone;
    private EditText etAddress;
    private Button btFindAddress;
    private EditText etExtraAddress;
    private EditText etHeight;
    private EditText etWeight;
    private Button btModify;

    private boolean isEditableMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        init();
        setUserDefaultInfo();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("MY INFO");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etEmail = (EditText)findViewById(R.id.etEmail);
        etEmail.setEnabled(false);

        etPassword = (EditText)findViewById(R.id.etPassword);
        etPassword.setEnabled(false);
        etPasswordConfirm = (EditText)findViewById(R.id.etPasswordConfirm);

        etName = (EditText)findViewById(R.id.etName);
        etName.setEnabled(false);
        etBirthday = (EditText)findViewById(R.id.etBirthday);
        etBirthday.setEnabled(false);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etPhone.setEnabled(false);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etAddress.setEnabled(false);
        btFindAddress = (Button)findViewById(R.id.btFindAddress);
        etExtraAddress = (EditText)findViewById(R.id.etExtraAddress);
        etExtraAddress.setEnabled(false);
        etHeight = (EditText)findViewById(R.id.etHeight);
        etHeight.setEnabled(false);
        etWeight = (EditText)findViewById(R.id.etWeight);
        etWeight.setEnabled(false);

        btModify = (Button)findViewById(R.id.btModify);
        btModify.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEditableMode) {
                    setEditableMode();
                }
                else {
                    if(isPasswordCorrect()) {
                        saveUserInfo();
                        finish();
                    }
                }
                //showDialog();
            }
        });
    }

    private void setUserDefaultInfo() {
        User user = App.getInstance().getUser();
        etEmail.setHint(user.getEmail());
        etName.setHint(user.getName());
        etPassword.setText("000000");
        etPassword.setTextColor(ContextCompat.getColor(mContext, R.color.edittext_hint_color));
        //TODO
        etBirthday.setHint("");
        etPhone.setHint(user.getPhone());
        etAddress.setHint(user.getAddress1());
        etExtraAddress.setHint(user.getAddress2());
        //TODO
        etHeight.setHint("");
        etWeight.setHint("");
    }

    private void setEditableMode() {
        User user = App.getInstance().getUser();
        isEditableMode = true;

        etPassword.setEnabled(true);
        etPassword.setText("");
        etPassword.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        etPasswordConfirm.setVisibility(View.VISIBLE);

        etName.setHint("NAME");
        etName.setText(user.getName());
        etName.setEnabled(true);

        etBirthday.setHint("생년월일");
        etBirthday.setText("");
        etBirthday.setEnabled(true);

        etAddress.setHint("주소");
        etAddress.setText(user.getAddress1());
        etAddress.setEnabled(true);

        btFindAddress.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        btFindAddress.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        btFindAddress.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        etExtraAddress.setHint("나머지 주소 입력");
        etExtraAddress.setText(user.getAddress2());
        etExtraAddress.setEnabled(true);

        etHeight.setHint("키 (CM)");
        etHeight.setText("");
        etHeight.setEnabled(true);

        etWeight.setHint("몸무게 (KG)");
        etWeight.setText("");
        etWeight.setEnabled(true);

        btModify.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        btModify.setText("수정완료");
        btModify.setTextColor(ContextCompat.getColor(mContext, R.color.white));
    }

    private boolean isPasswordCorrect() {
        String p1 = etPassword.getText().toString();
        String p2 = etPasswordConfirm.getText().toString();
        if(p1.equals("")) {
            Toast.makeText(mContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(p2.equals("")) {
            Toast.makeText(mContext, "비밀번호 확인란을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(p1.equals(p2)) {
            return true;
        }
        else {
            Toast.makeText(mContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void saveUserInfo() {
        User user = App.getInstance().getUser();
        user.setPassword(etPassword.getText().toString());
        user.setName(etName.getText().toString());
        //TODO
        //user.setBirthday();
        user.setAddress1(etAddress.getText().toString());
        user.setAddress2(etExtraAddress.getText().toString());
        //TODO Height, weight

        //TODO call Api

        App.getInstance().setUser(user);
    }
}
