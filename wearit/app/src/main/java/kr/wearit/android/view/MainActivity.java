package kr.wearit.android.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Level;

import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.adapter.MainNewsAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.NewsApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsCategory;
import kr.wearit.android.model.NewsPair;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.ui.ScrollListener;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.view.main.MainFragment;
import kr.wearit.android.view.main.MyPageFragment;

import com.etsy.android.grid.util.DynamicHeightImageView;

import org.w3c.dom.Text;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity##";
    private static final boolean LOG = Config.LOG;
    private Context mContext;

    private ArrayList<News> mNewsList;
    private ArrayList<Product> mBestItemList;
    private ArrayList<Product> mNewItemList;
    private ArrayList<Product> mSaleItemList;

    private static final int FRAGMENT_MAIN = 1;
    private static final int FRAGMENT_SEARCH = 2;
    private static final int FRAGMENT_CART = 3;
    private static final int FRAGMENT_K = 4;
    private static final int FRAGMENT_MY = 5;

    //BottomBar
    private LinearLayout llBtnMain;
    private LinearLayout llBtnSearch;
    private LinearLayout llBtnBag;
    private LinearLayout llBtnK;
    private LinearLayout llBtnMy;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                if(LOG) {
                    Log.d(TAG, "in mHandler");
                }
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

            case FRAGMENT_MY:
                MyPageFragment mpf = new MyPageFragment();
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
