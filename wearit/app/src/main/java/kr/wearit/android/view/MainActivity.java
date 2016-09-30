package kr.wearit.android.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nhn.android.maps.opt.V;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.NewsApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsCategory;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.view.main.BagFragment;
import kr.wearit.android.view.main.ItemListFragment;
import kr.wearit.android.view.main.KeepFragment;
import kr.wearit.android.view.main.MainFragment;
import kr.wearit.android.view.main.MyPageFragment;
import kr.wearit.android.view.main.MyPageGuestFragment;

public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity##";
    private final boolean LOG = Config.LOG;
    private Context mContext;

    private ArrayList<News> mNewsList;
    private ArrayList<Product> mBestItemList;
    private ArrayList<Product> mNewItemList;
    private ArrayList<Product> mSaleItemList;

    private final int FRAGMENT_MAIN = Const.FRAGMENT_MAIN;
    private final int FRAGMENT_ITEMLIST = Const.FRAGMENT_ITEMLIST;
    private final int FRAGMENT_BAG = Const.FRAGMENT_BAG;
    private final int FRAGMENT_K = Const.FRAGMENT_K;
    private final int FRAGMENT_MY = Const.FRAGMENT_MY;

    //BottomBar
    private LinearLayout llBtnMain;
    private LinearLayout llBtnSearch;
    private LinearLayout llBtnBag;
    private LinearLayout llBtnK;
    private LinearLayout llBtnMy;

    private RelativeLayout rlWating;

    private LinearLayout llBottomBar;

    public static Wating wait;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                if(LOG) {
                    Log.d(TAG, "in mHandler");
                }
                rlWating.setVisibility(View.GONE);
                changeFragment(FRAGMENT_MAIN);
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

    public void changeFragment(int idx) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment();
        switch(idx) {
            case FRAGMENT_MAIN:
                Bundle args = makeBundle();
                fragment = new MainFragment();
                fragment.setArguments(args);
                break;

            case FRAGMENT_ITEMLIST:
                fragment = new ItemListFragment();
                break;

            case FRAGMENT_BAG:
                fragment = new BagFragment();
                break;

            case FRAGMENT_K:
                fragment = new KeepFragment();
                break;

            case FRAGMENT_MY:
                if(App.getInstance().isLogin()) {
                    fragment = new MyPageFragment();
                }
                else {
                    fragment = new MyPageGuestFragment();
                }
                break;
        }
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        rlWating = (RelativeLayout) findViewById(R.id.rl_waiting);
        rlWating.setVisibility(View.VISIBLE);

        wait = new Wating();

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
                changeFragment(FRAGMENT_MAIN);
            }
        });
        llBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_ITEMLIST);
            }
        });
        llBtnBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_BAG);
            }
        });
        llBtnK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_K);
            }
        });
        llBtnMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(FRAGMENT_MY);
            }
        });

        llBottomBar = (LinearLayout) findViewById(R.id.ll_bottom_bar);
    }

    private void getData() {
        NewsApi.getPage(NewsCategory.ALL, 1, new Api.OnDefaultListener<Pagination<News>>() {
            @Override
            public void onSuccess(Pagination<News> data) {
                mNewsList = data.getList();
                mHandler.sendEmptyMessage(1);

            }
        });
    }

    public static void removeBottomBar() {
        wait.run();
    }

    private class Wating implements Runnable{

        @Override
        public void run() {
            if(llBottomBar.getVisibility() == View.VISIBLE) {
                llBottomBar.setVisibility(View.GONE);
            }
            else {
                llBottomBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fragment_content);
        if(currentFragment instanceof MainFragment) {
            super.onBackPressed();
        }
        else {
            changeFragment(FRAGMENT_MAIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(LOG) {
            Log.d(TAG, "onActivityResult, " + requestCode);
        }
        if(resultCode == Const.FROM_LOGIN_SUCCESS) {
            changeFragment(FRAGMENT_MAIN);
        }
    }
}
