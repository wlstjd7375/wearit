package kr.wearit.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.R;
import kr.wearit.android.adapter.OrderListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.OrderApi;
import kr.wearit.android.model.Order;

public class OrderListActivity extends BaseActivity {

    private String LOG = "OrderListActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private ListView lvOrderList;
    private OrderListAdapter mAdapter;
    private ArrayList<Order> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        init();
        getOrderList();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("ORDER LIST");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDataList = new ArrayList<>();
        lvOrderList = (ListView)findViewById(R.id.lvOrderList);

    }

    private void getOrderList() {
        OrderApi.getList(new Api.OnAuthListener<ArrayList<Order>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(ArrayList<Order> data) {
                mDataList = data;
                mAdapter = new OrderListAdapter(mContext, mDataList);
                lvOrderList.setAdapter(mAdapter);
                lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(OrderListActivity.this, OrderInfoActivity.class);
                        intent.putExtra("order", mAdapter.getItem(position).getKey());
                        startActivity(intent);
                    }
                });
                //mAdapter.clear();
                //mAdapter.addAll(mDataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail() {
            }
        });
    }
}
