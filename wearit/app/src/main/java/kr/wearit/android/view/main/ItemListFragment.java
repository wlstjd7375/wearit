package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import kr.wearit.android.R;
import kr.wearit.android.adapter.ProductListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;

/**
 * Created by KimJS on 2016-09-23.
 */
public class ItemListFragment extends Fragment {

    private Context mContext;

    private TextView tvToolbarTitle;
    private ListView lvItemList;

    private ProductListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mContext = getActivity();

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("ITEM");
        lvItemList = (ListView)view.findViewById(R.id.lvItemList);

        ProductApi.getListOrder(1, "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
            @Override
            public void onSuccess(Pagination<Product> data) {
                mAdapter = new ProductListAdapter(mContext, data.getList(), getScreenWidth());
                lvItemList.setAdapter(mAdapter);
                //lvItemList.setOnScrollListener(mFetchHandler);
            }
        });


        return view;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
