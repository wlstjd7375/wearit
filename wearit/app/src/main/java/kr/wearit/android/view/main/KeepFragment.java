package kr.wearit.android.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.adapter.BagListAdapter;
import kr.wearit.android.adapter.KeepListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.MeApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.view.MainActivity;

/**
 * Created by KimJS on 2016-09-26.
 */
public class KeepFragment extends Fragment {

    private final String TAG = "KeepFragment##";
    private TextView tvToolbarTitle;

    private View view;

    //Go Shopping
    private RelativeLayout rlGoShopping;
    private TextView tvNoItem;
    private Button btGoShopping;

    //Keep List
    private ListView lvKeepList;
    private KeepListAdapter mAdapter;
    private ArrayList<Product> mProductList;
    //private final String BAG_URL = "/me/product";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keep, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("KEEP");

        rlGoShopping = (RelativeLayout)view.findViewById(R.id.rlGoShopping);
        lvKeepList = (ListView)view.findViewById(R.id.lvKeepList);

        if(App.getInstance().isLogin()) {
            ProductApi.getList("/me/product", new Api.OnDefaultListener<ArrayList<Product>>() {
                @Override
                public void onSuccess(ArrayList<Product> data) {
                    mProductList = data;
                    setListView();
                }
            });
        }

        return view;
    }

    private void setListView() {
        if(mProductList.size() == 0) {
            setNoItemLayout();
        }
        else {
            setBagListLayout();
        }
    }


    private void setNoItemLayout() {
        //if there is no item
        //set layout_go_shopping
        lvKeepList.setVisibility(View.INVISIBLE);
        rlGoShopping.setVisibility(View.VISIBLE);

        tvNoItem = (TextView) view.findViewById(R.id.tvNoItem);
        tvNoItem.setText("현재 KEEP한 상품이 없습니다.\n마음에 드는 상품을 KEEP하세요.");

        btGoShopping = (Button) view.findViewById(R.id.btGoShopping);
        btGoShopping.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(Const.FRAGMENT_ITEMLIST);
            }
        });
    }

    private void setBagListLayout() {
        //No Item Layout invisible
        rlGoShopping.setVisibility(View.INVISIBLE);
        lvKeepList.setVisibility(View.VISIBLE);


        //Set Bag List

        //Create Adapter
        mAdapter = new KeepListAdapter(getActivity(), mProductList, KeepFragment.this);
        //setAdapter
        lvKeepList.setAdapter(mAdapter);
    }

    public void deleteRow(Product item) {
        mProductList.remove(item);
        setListView();
        //mAdapter.remove(item);
        //mAdapter.notifyDataSetChanged();
    }
}
