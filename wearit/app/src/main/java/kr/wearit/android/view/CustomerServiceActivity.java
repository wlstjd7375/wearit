package kr.wearit.android.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.MessageApi;
import kr.wearit.android.model.Message;

public class CustomerServiceActivity extends BaseActivity {

    private String LOG = "CustomerServiceActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private EditText etCustomerService;
    private Button btSendQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        init();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("CUSTOMER SERVICE");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //TODO
        etCustomerService = (EditText)findViewById(R.id.etCustomerService);
        btSendQuestion = (Button)findViewById(R.id.btSendQuestion);

        btSendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message item = new Message();
                item.setContent(etCustomerService.getText().toString());

                MessageApi.addForQuestion(item, new Api.OnAuthWaitListener<Integer>(getActivity()) {
                    @Override
                    public void onSuccess(Integer data) {
                        Toast.makeText(getActivity(),"문의가 접수 되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}
