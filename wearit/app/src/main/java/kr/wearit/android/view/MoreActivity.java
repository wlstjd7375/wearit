package kr.wearit.android.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.adapter.ProductListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.ui.ScrollListener;

public class MoreActivity extends BaseActivity {

    private String TAG = "MoreActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private ListView lvItemList;

    private int itemType;

    private final int BEST_ITEM = Const.BEST_ITEM;
    private final int NEW_ITEM = Const.NEW_ITEM;
    private final int SALE_ITEM = Const.SALE_ITEM;

    private ArrayList<Product> mDataList;
    private ProductListAdapter mAdapter;

    private RelativeLayout rlWating;

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
        //Loading
        rlWating = (RelativeLayout) findViewById(R.id.rl_waiting);
        rlWating.setVisibility(View.VISIBLE);

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
        //Loading finish
        rlWating.setVisibility(View.GONE);

        mAdapter = new ProductListAdapter(mContext, mDataList, App.getInstance().getScreenWidth());
        //mAdapter = new ProductListAdapter(mContext, getSubList(11), getScreenWidth());
        lvItemList.setAdapter(mAdapter);
        lvItemList.setOnScrollListener(mFetchHandler);
    }

    private ScrollListener mFetchHandler = new ScrollListener() {

        @Override
        public void onFetch(final ScrollListener listener, final int page) {
            //TODO Progressbar
            Log.d(TAG, "page = " + page);
            switch (itemType) {
                case BEST_ITEM:
                    tvToolbarTitle.setText("BEST ITEM");
                    ProductApi.getListOrder(page, "bestorder", new Api.OnDefaultListener<Pagination<Product>>() {
                        @Override
                        public void onSuccess(Pagination<Product> data) {
                            if (page == 1) {
                                mAdapter.clear();
                            }
                            mAdapter.addAll(data.getList());
                            listener.onFetched(data);
                        }
                    });
                    break;
                case NEW_ITEM:
                    tvToolbarTitle.setText("NEW ITEM");
                    ProductApi.getListOrder(page, "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
                        @Override
                        public void onSuccess(Pagination<Product> data) {
                            if (page == 1) {
                                mAdapter.clear();
                            }
                            mAdapter.addAll(data.getList());
                            listener.onFetched(data);
                        }
                    });
                    break;
                case SALE_ITEM:
                    tvToolbarTitle.setText("SALE ITEM");
                    ProductApi.getListSale(page, new Api.OnDefaultListener<Pagination<Product>>() {
                        @Override
                        public void onSuccess(Pagination<Product> data) {
                            if (page == 1) {
                                mAdapter.clear();
                            }
                            mAdapter.addAll(data.getList());
                            listener.onFetched(data);
                        }
                    });
                    break;
            }
        }
    };

    //원하는 갯수만큼 출력(테스트용)
    private ArrayList<Product> getSubList(int size) {
        int iter = mDataList.size();
        if(iter > size) {
            iter = size;
        }

        ArrayList<Product> result = new ArrayList<>();
        for(int i = 0; i < iter; i++){
            result.add(mDataList.get(i));
        }
        return result;
    }
}
