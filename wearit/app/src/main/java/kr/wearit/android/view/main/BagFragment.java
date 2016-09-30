package kr.wearit.android.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.adapter.BagListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.CartApi;
import kr.wearit.android.model.CartDeliver;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.view.MainActivity;


/**
 * Created by KimJS on 2016-09-26.
 */
public class BagFragment extends Fragment {

    private final String TAG = "BagFragment##";
    private TextView tvToolbarTitle;
    private TextView tvTotalItemInfo;

    private View view;

    //Go Shopping
    private RelativeLayout rlGoShopping;
    private TextView tvNoItem;
    private Button btGoShopping;

    //Bag List
    private ListView lvBagList;
    private BagListAdapter mAdapter;
    private ArrayList<ProductCart> mProductList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bag, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("BAG");

        tvTotalItemInfo = (TextView)view.findViewById(R.id.tvTotalItemInfo);

        rlGoShopping = (RelativeLayout)view.findViewById(R.id.rlGoShopping);
        lvBagList = (ListView)view.findViewById(R.id.lvBagList);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(App.getInstance().isLogin()) {
            CartApi.getList(new Api.OnAuthDefaultListener<CartDeliver>() {
                @Override
                public void onSuccess(CartDeliver data) {
                    mProductList = data.getCart();
                    setListView();
                }
            });
        }
        //TODO 로그인이 안됐을때 페이지 어떻게?
        /*
        else {
            setListView();
        }*/
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
        lvBagList.setVisibility(View.INVISIBLE);
        rlGoShopping.setVisibility(View.VISIBLE);

        tvTotalItemInfo.setText("NO ITEM");

        tvNoItem = (TextView) view.findViewById(R.id.tvNoItem);
        tvNoItem.setText("ITBAG에 담긴 상품이 없습니다.");
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
        lvBagList.setVisibility(View.VISIBLE);

        //set Total item in bag information
        int cnt = mProductList.size();
        tvTotalItemInfo.setText(cnt + " items: ");

        //Set Bag List

        //Create Adapter
        mAdapter = new BagListAdapter(getActivity(), mProductList, BagFragment.this);
        //setAdapter
        lvBagList.setAdapter(mAdapter);
    }

    public void deleteRow(ProductCart item) {
        mProductList.remove(item);
        setListView();
        //mAdapter.remove(item);
        //mAdapter.notifyDataSetChanged();
    }
}
