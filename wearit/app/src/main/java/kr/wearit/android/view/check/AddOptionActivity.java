package kr.wearit.android.view.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.adapter.AddOptionAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.BrandApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Brand;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.ui.ScrollListener;
import kr.wearit.android.view.BaseActivity;

public class AddOptionActivity extends BaseActivity {

    private String TAG = "AddOptionActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private TextView tvRightButton; // clear

    private ArrayList<Integer> brandList;

    //toolbar item count
    private TextView tvSelectedCount;
    private TextView tvTotalCount;

    //List
    private ListView lvAddOption;
    private AddOptionAdapter mAdapter;
    private LinearLayout llAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_item);

        Intent intent = getIntent();
        brandList = intent.getExtras().getIntegerArrayList("brand_name");


        init();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("추가상품 구성하기");
        tvRightButton = (TextView)findViewById(R.id.tvRightButton);
        tvRightButton.setText("CLEAR");
        tvRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //TODO item count
        tvSelectedCount = (TextView)findViewById(R.id.tvSelectedCount);
        tvTotalCount = (TextView)findViewById(R.id.tvTotalCount);

        lvAddOption = (ListView)findViewById(R.id.lvAddOption);
        lvAddOption.setOnScrollListener(mFetchHandler);
        /*
        lvAddOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "position = " + position);
            }
        });*/
        mFetchHandler.fetch(1);

        llAddProduct = (LinearLayout)findViewById(R.id.llAddProduct);
        llAddProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO 확인버튼
            }
        });
    }

    private void getItemList(final int page) {
        String brand = "";
        int brandCount = brandList.size();
        for(int i = 0; i < brandCount; i++) {
            brand += String.valueOf(brandList.get(i));
            brand += ",";
        }
        brand = brand.substring(0, brand.length() - 1);
        ProductApi.getListByBrand(brand,"neworder", page, new Api.OnAuthDefaultListener<Pagination<Product>>(){
            @Override
            public void onSuccess(Pagination<Product> data) {
                Log.d(TAG, "total count = " + data.getList().size());
                if(mAdapter == null) {
                    mAdapter = new AddOptionAdapter(mContext, data.getList(), App.getInstance().getScreenWidth());
                    lvAddOption.setAdapter(mAdapter);
                }
                else {
                    if(page == 1) {
                        mAdapter.clear();
                    }

                    mAdapter.addAll(data.getList());
                    //rlWating.setVisibility(View.GONE);
                }
                mFetchHandler.onFetched(data);
            }
        });
    }

    private ScrollListener mFetchHandler = new ScrollListener() {

        @Override
        public void onFetch(final ScrollListener listener, final int page) {
            //TODO Progressbar
            Log.d(TAG, "onFetch page = " + page);
            getItemList(page);
        }
    };
}
