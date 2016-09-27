package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.view.MainActivity;


/**
 * Created by KimJS on 2016-09-26.
 */
public class BagFragment extends Fragment {

    private TextView tvToolbarTitle;

    //Go Shopping
    private LinearLayout llGoShopping;
    private TextView tvNoItem;
    private Button btGoShopping;

    //Bag List
    private ListView lvBagList;
    //adapter

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("BAG");

        //TODO if there is no item
        //set layout_go_shopping
        tvNoItem = (TextView) view.findViewById(R.id.tvNoItem);
        tvNoItem.setText("ITBAG에 담긴 상품이 없습니다.");
        btGoShopping = (Button) view.findViewById(R.id.btGoShopping);
        btGoShopping.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(Const.FRAGMENT_ITEMLIST);
            }
        });

        //else
        //get Bag Product List

        return view;
    }

}
