package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.ArrayList;

import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.adapter.MainNewsAdapter;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsPair;
import kr.wearit.android.model.Product;
import kr.wearit.android.util.ImageUtil;

/**
 * Created by KimJS on 2016-09-16.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainActivity##";
    private static final boolean LOG = Config.LOG;
    private Context mContext;

    private int screenWidth;
    private ListView lvMainNews;
    private MainNewsAdapter mainNewsAdapter;

    private View header;
    private View footer;
    private LinearLayout llHorizontalView;
    private static final int BEST_ITEM = 0;
    private static final int NEW_ITEM = 1;
    private static final int SALE_ITEM = 2;

    private ArrayList<News> mNewsList;
    private ArrayList<Product> mBestItemList;
    private ArrayList<Product> mNewItemList;
    private ArrayList<Product> mSaleItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(LOG) {
            Log.d(TAG, "in MainFragment");
        }
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mNewsList = (ArrayList<News>)getArguments().get("newslist");
        mBestItemList = (ArrayList<Product>)getArguments().get("bestitemlist");
        mSaleItemList = (ArrayList<Product>)getArguments().get("saleitemlist");
        mNewItemList = (ArrayList<Product>)getArguments().get("newitemlist");

        mContext = getActivity();
        lvMainNews = (ListView)view.findViewById(R.id.lvMainNews);
        header = getActivity().getLayoutInflater().inflate(R.layout.header_main, null);
        footer = getActivity().getLayoutInflater().inflate(R.layout.footer_main, null);
        screenWidth = getScreenWidth();

        addProductList(mBestItemList, BEST_ITEM);
        addProductList(mNewItemList, NEW_ITEM);
        addProductList(mSaleItemList, SALE_ITEM);

        lvMainNews.addFooterView(footer);
        addNewsView();

        return view;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private void addNewsView() {
        if(LOG) {
            Log.d(TAG, "addNewsView()");
        }
        //Header
        DynamicHeightImageView headerImageView = (DynamicHeightImageView) header.findViewById(R.id.ivMainHeader);
        int w = mNewsList.get(0).getImageWidth();
        int h = mNewsList.get(0).getImageHeight();
        Log.d(TAG, w + ", " + h);
        headerImageView.setHeightRatio(ImageUtil.getRatio(w, h));
        ImageUtil.display(headerImageView, mNewsList.get(0).getImagePath());
        lvMainNews.addHeaderView(header);

        //Grid News
        ArrayList<NewsPair> mNewPairList = new ArrayList<>();
        int iter = (mNewsList.size()-1) / 2;
        int j = 1;
        int maxLine = 4;
        for(int i = 1; i < iter+1; i++) {
            if(j++ > maxLine) break;
            ArrayList<News> t = new ArrayList<>();
            NewsPair np = new NewsPair();
            int idx = i * 2;
            np.setNews1(mNewsList.get(idx-1));
            np.setNews2(mNewsList.get(idx));
            mNewPairList.add(np);
        }
        mainNewsAdapter = new MainNewsAdapter(mContext, mNewPairList, screenWidth);
        lvMainNews.setAdapter(mainNewsAdapter);
    }

    private void addProductList(ArrayList<Product> mProductList, int type) {
        Log.d(TAG, "addProductList: " + type);
        switch (type) {
            case BEST_ITEM:
                llHorizontalView = (LinearLayout) footer.findViewById(R.id.llMainBestItem);
                break;
            case NEW_ITEM:
                llHorizontalView = (LinearLayout) footer.findViewById(R.id.llMainNewItem);
                break;

            case SALE_ITEM:
                llHorizontalView = (LinearLayout) footer.findViewById(R.id.llMainSaleItem);
                break;
        }

        int i = 0;
        for(Product product : mProductList) {
            if(i++ > 5) {
                break;
            }
            View llProductItem = getActivity().getLayoutInflater().inflate(R.layout.layout_item, null);
            DynamicHeightImageView ivProduct = (DynamicHeightImageView) llProductItem.findViewById(R.id.ivProduct);
            TextView tvBrand = (TextView) llProductItem.findViewById(R.id.tvProductBrand);
            TextView tvName = (TextView) llProductItem.findViewById(R.id.tvProductName);
            TextView tvPrice1 = (TextView) llProductItem.findViewById(R.id.tvProductPrice1);
            TextView tvPrice2 = (TextView) llProductItem.findViewById(R.id.tvProductPrice2);

            //imageView.setHeightRatio(ImageUtil.getRatio(product.getImageWi));
            ImageUtil.display(ivProduct, product.getImagePath());

            tvBrand.setText(product.getBrandName());
            tvName.setText(product.getName());
            /*
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"NotoSansCJKkr-Regular.otf");
            tvName.setTypeface(font);*/

            tvPrice1.setText(product.getPrice() + "");
            if(product.isSale()) {
                //TODO 빨간줄
                tvPrice1.setTextColor(Color.parseColor("#e33131"));
                tvPrice2.setText(product.getSalePrice() + "");
            }

            llHorizontalView.addView(llProductItem);
        }
    }
}
