package kr.wearit.android.view.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        init();
    }

    private void init() {
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
                showDialog();
            }
        });
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
}
