package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Intent;
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
import kr.wearit.android.model.DeliverInfo;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.view.MainActivity;
import kr.wearit.android.view.account.LoginActivity;
import kr.wearit.android.view.check.CartCheckActivity;
import kr.wearit.android.view.check.CheckActivity;


/**
 * Created by KimJS on 2016-09-26.
 */
public class BagFragment extends Fragment {

    private final String TAG = "BagFragment##";
    private TextView tvToolbarTitle;
    private TextView tvTotalItemInfo;

    private View view;

    private TextView tvCheckOut;

    //Go Login
    private RelativeLayout rlGoLogin;
    private Button btGoLogin;

    //Go Shopping
    private RelativeLayout rlGoShopping;
    private TextView tvNoItem;
    private Button btGoShopping;

    //Bag List
    private ListView lvBagList;
    private BagListAdapter mAdapter;
    private ArrayList<ProductCart> mProductList;
    private ArrayList<DeliverInfo> mDeliverList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bag, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("BAG");

        tvTotalItemInfo = (TextView)view.findViewById(R.id.tvTotalItemInfo);

        rlGoLogin = (RelativeLayout)view.findViewById(R.id.rlGoLogin);
        rlGoShopping = (RelativeLayout)view.findViewById(R.id.rlGoShopping);
        lvBagList = (ListView)view.findViewById(R.id.lvBagList);

        //TODO check out
        tvCheckOut = (TextView)view.findViewById(R.id.tvCheckOut);
        tvCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.getInstance().isLogin()) {
                    Intent intent = new Intent(getActivity(), CartCheckActivity.class);
                    intent.putExtra("cart", mProductList);
                    intent.putExtra("deliver", mDeliverList);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(App.getInstance().isLogin()) {
            tvCheckOut.setVisibility(View.VISIBLE);
            CartApi.getList(new Api.OnAuthDefaultListener<CartDeliver>() {
                @Override
                public void onSuccess(CartDeliver data) {
                    mProductList = data.getCart();
                    mDeliverList = data.getDeliverInfo();
                    setListView();
                }
            });
        }
        else {
            tvCheckOut.setVisibility(View.INVISIBLE);
            //Go to Login
            rlGoLogin.setVisibility(View.VISIBLE);
            lvBagList.setVisibility(View.INVISIBLE);
            rlGoShopping.setVisibility(View.INVISIBLE);
            btGoLogin = (Button)view.findViewById(R.id.btGoLogin);
            btGoLogin.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
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
        rlGoLogin.setVisibility(View.INVISIBLE);
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
        rlGoLogin.setVisibility(View.INVISIBLE);
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
