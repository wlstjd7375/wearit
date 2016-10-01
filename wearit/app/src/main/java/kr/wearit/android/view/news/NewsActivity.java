package kr.wearit.android.view.news;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.adapter.ProductListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.NewsApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.controller.ShopApi;
import kr.wearit.android.model.News;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductArgs;
import kr.wearit.android.model.Shop;
import kr.wearit.android.view.BaseActivity;
import kr.wearit.android.ui.ScrollListener;
import kr.wearit.android.widget.ContentView;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.view.product.ProductActivity;

public class NewsActivity extends BaseActivity {

    @SuppressWarnings("unused")
    private static final String TAG = NewsActivity.class.getSimpleName() + "##";
    @SuppressWarnings("unused")
    private static final boolean LOG = Config.LOG;

    private static final String EXTRA_ITEM = "news";
    private static final String EXTRA_KEY = "key";

    private static NewsActivity newsActivity;
    //

    //

    private News mExtraItem;

    private View header;
    private ListView lvProduct;
    private ProductListAdapter mAdapter;

    private RelativeLayout rlWating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExtraItem = getIntent().getParcelableExtra(EXTRA_ITEM);
        int key = getIntent().getIntExtra(EXTRA_KEY, -1);

        setContentView(R.layout.activity_news);

        rlWating = (RelativeLayout) findViewById(R.id.rl_waiting);
        header = getActivity().getLayoutInflater().inflate(R.layout.header_news, null);

        lvProduct = (ListView) findViewById(R.id.lv_product);

        rlWating.setVisibility(View.VISIBLE);

        if(key != -1){
            NewsApi.get(key, new Api.OnWaitListener<News>(getActivity()) {

                @Override
                public void onSuccess(News item) {
                    mExtraItem = item;
                    initailize();
                    rlWating.setVisibility(View.GONE);
                }
            });
        }
    }

    public void initailize() {

        ContentView content = (ContentView) header.findViewById(R.id.content);

        System.out.println("Title : " + mExtraItem.getDescription());

        content.setContent(mExtraItem.getContentObject(), mExtraItem.getDescription());

        lvProduct.addHeaderView(header);
//        lvProduct.setOnScrollListener(mFetchHandler);
        if(mExtraItem.getProductRelationCount() != 0){
            ((RelativeLayout) header.findViewById(R.id.ll_product_header)).setVisibility(View.VISIBLE);
            Log.d(TAG, "size = " + mExtraItem.getProductRelationCount());
            mAdapter = new ProductListAdapter(getActivity(), mExtraItem.getProductRelationList(), App.getInstance().getScreenWidth());
            lvProduct.setAdapter(mAdapter);
            lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int selPosition = position - 1;
                    if(selPosition < 0) {
                        selPosition = 0;
                    }
                    Intent intent = new Intent(getActivity(), ProductActivity.class);
                    intent.putExtra("key",mAdapter.getItem(selPosition).getKey());
                    startActivity(intent);
                }
            });
            lvProduct.setOnScrollListener(mFetchHandler);
            mFetchHandler.fetch(1);
        }
        else {
            ((RelativeLayout) header.findViewById(R.id.ll_product_header)).setVisibility(View.GONE);
        }
    }

    private ScrollListener mFetchHandler = new ScrollListener() {

        @Override
        public void onFetch(final ScrollListener listener, final int page) {
            ProductApi.getRelationPage(mExtraItem.getProductRelationKey(), page, new Api.OnWaitListener<Pagination<Product>>(getActivity()) {
                @Override
                public void onSuccess(Pagination<Product> data) {
                    if (page == 1) {
                        mAdapter.clear();
                    }
                    mAdapter.addAll(data.getList());
//                                if (page == 1)
//                                    setFetched();
                    listener.onFetched(data);
                    mAdapter.isEmpty();
//                                setData(data.getList());
                }
            });
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
