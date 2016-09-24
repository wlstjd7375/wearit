package kr.wearit.android.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.NewsApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsCategory;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.view.main.ItemListFragment;
import kr.wearit.android.view.main.MainFragment;
import kr.wearit.android.view.main.MyPageGuestFragment;

public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity##";
    private final boolean LOG = Config.LOG;
    private Context mContext;

    private ArrayList<News> mNewsList;
    private ArrayList<Product> mBestItemList;
    private ArrayList<Product> mNewItemList;
    private ArrayList<Product> mSaleItemList;

    private final int FRAGMENT_MAIN = 1;
    private final int FRAGMENT_SEARCH = 2;
    private final int FRAGMENT_CART = 3;
    private final int FRAGMENT_K = 4;
    private final int FRAGMENT_MY = 5;

    //BottomBar
    private LinearLayout llBtnMain;
    private LinearLayout llBtnSearch;
    private LinearLayout llBtnBag;
    private LinearLayout llBtnK;
    private LinearLayout llBtnMy;

    private RelativeLayout rlWating;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                if(LOG) {
                    Log.d(TAG, "in mHandler");
                }
                rlWating.setVisibility(View.GONE);
                changeFragment(1, makeBundle());
            }
        }
    };


    private Bundle makeBundle() {
        Bundle args = new Bundle();
        args.putSerializable("newslist", mNewsList);
        args.putSerializable("bestitemlist", mBestItemList);
        args.putSerializable("saleitemlist", mSaleItemList);
        args.putSerializable("newitemlist", mNewItemList);

        return args;
    }

    private void changeFragment(int idx, Bundle args) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(idx) {
            case FRAGMENT_MAIN:
                MainFragment mf = new MainFragment();
                mf.setArguments(args);
                fragmentTransaction.replace(R.id.fragment_content, mf);
                fragmentTransaction.commit();
                break;

            case FRAGMENT_SEARCH:
                ItemListFragment af = new ItemListFragment();
                fragmentTransaction.replace(R.id.fragment_content, af);
                fragmentTransaction.commit();
                break;

            case FRAGMENT_MY:
                MyPageGuestFragment mpf = new MyPageGuestFragment();
                fragmentTransaction.replace(R.id.fragment_content, mpf);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        rlWating = (RelativeLayout) findViewById(R.id.rl_waiting);
        rlWating.setVisibility(View.VISIBLE);
        initView();
        getData();
    }

    private void initView() {
        //Bottom bar button click listener
        llBtnMain = (LinearLayout) findViewById(R.id.llBtnMain);
        llBtnSearch = (LinearLayout) findViewById(R.id.llBtnSearch);
        llBtnBag = (LinearLayout) findViewById(R.id.llBtnBag);
        llBtnK = (LinearLayout) findViewById(R.id.llBtnK);
        llBtnMy = (LinearLayout) findViewById(R.id.llBtnMy);

        llBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_MAIN, makeBundle());
            }
        });
        llBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_SEARCH, null);
            }
        });
        llBtnMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_MY, null);
            }
        });
    }

    private void getData() {
        NewsApi.getPage(NewsCategory.ALL, 1, new Api.OnDefaultListener<Pagination<News>>() {
            @Override
            public void onSuccess(Pagination<News> data) {
                mNewsList = data.getList();

                ProductApi.getListOrder(1, "bestorder", new Api.OnDefaultListener<Pagination<Product>>() {
                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        mBestItemList = data.getList();

                        ProductApi.getListOrder(1, "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
                            @Override
                            public void onSuccess(Pagination<Product> data) {
                                mNewItemList = data.getList();

                                ProductApi.getListSale(1, new Api.OnDefaultListener<Pagination<Product>>() {
                                    @Override
                                    public void onSuccess(Pagination<Product> data) {
                                        mSaleItemList = data.getList();
                                        mHandler.sendEmptyMessage(1);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
