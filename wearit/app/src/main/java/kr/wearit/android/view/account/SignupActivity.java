package kr.wearit.android.view.account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import kr.wearit.android.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {
        mContext = this;
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
        btSignUp = (Button)findViewById(R.id.btSignUp);
    }

}
