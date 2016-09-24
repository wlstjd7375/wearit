package kr.wearit.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;

public class MoreActivity extends BaseActivity {

    private String LOG = "LoginActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private ListView lvItemList;

    private int itemType;

    private final int BEST_ITEM = Const.BEST_ITEM;
    private final int NEW_ITEM = Const.NEW_ITEM;
    private final int SALE_ITEM = Const.SALE_ITEM;

    private ArrayList<Product> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Intent intent = getIntent();
        itemType = intent.getIntExtra("item_type", 0);

        init();

        if(itemType != 0) {
            setListView();
        }
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lvItemList = (ListView)findViewById(R.id.lvItemList);
        mDataList = new ArrayList<>();
    }

    private void setListView() {
        switch (itemType) {
            case BEST_ITEM:
                tvToolbarTitle.setText("BEST ITEM");
                ProductApi.getListOrder(1, "bestorder", new Api.OnDefaultListener<Pagination<Product>>() {
                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        mDataList = data.getList();
                        setListAdapter();
                    }
                });
                break;
            case NEW_ITEM:
                tvToolbarTitle.setText("NEW ITEM");
                ProductApi.getListOrder(1, "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        mDataList = data.getList();
                        setListAdapter();
                    }
                });
                break;
            case SALE_ITEM:
                tvToolbarTitle.setText("SALE ITEM");
                ProductApi.getListSale(1, new Api.OnDefaultListener<Pagination<Product>>() {
                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        mDataList = data.getList();
                        setListAdapter();
                    }
                });
                break;
        }
    }

    private void setListAdapter() {

    }
}
