package kr.wearit.android.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import kr.wearit.android.R;
import kr.wearit.android.view.account.SignupActivity;

public class MoreActivity extends AppCompatActivity {

    private String LOG = "LoginActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private ListView lvItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        //TODO get argument, best_item, new_item, sale_item

        init();
        setListView();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        //TODO
        tvToolbarTitle.setText("TITLE");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lvItemList = (ListView)findViewById(R.id.lvItemList);
    }

    private void setListView() {
        //TODO
        //switch( ) item category
        //API CALL;
        //setAdapter in onSuccess;
    }
}
