package kr.wearit.android.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.adapter.CouponListAdapter;
import kr.wearit.android.model.Coupon;

public class CouponActivity extends BaseActivity {

    private String LOG = "SettingActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private ListView lvCouponList;
    private CouponListAdapter mAdapter;
    private ArrayList<Coupon> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        init();
        getCouponList();
        setListView();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Coupon");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lvCouponList = (ListView)findViewById(R.id.lvCouponList);
    }

    private void getCouponList() {
        mDataList = App.getInstance().getCouponList();
    }

    private void setListView() {
        mAdapter = new CouponListAdapter(mContext, mDataList);
        lvCouponList.setAdapter(mAdapter);
        lvCouponList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int couponKey = ((CouponListAdapter.ViewHolder)view.getTag()).getCouponKey();
                //api call ?
            }
        });
    }
}
