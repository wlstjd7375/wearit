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
import android.widget.TextView;

import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.view.MainActivity;

/**
 * Created by KimJS on 2016-09-26.
 */
public class KeepFragment extends Fragment {

    private TextView tvToolbarTitle;

    //Go Shopping
    private LinearLayout llGoShopping;
    private TextView tvNoItem;
    private Button btGoShopping;

    //Keep List
    private ListView lvBagList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keep, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("KEEP");

        //TODO if there is no item
        //set layout_go_shopping
        tvNoItem = (TextView) view.findViewById(R.id.tvNoItem);
        tvNoItem.setText("현재 KEEP한 상품이 없습니다.\n마음에 드는 상품을 KEEP하세요.");
        btGoShopping = (Button) view.findViewById(R.id.btGoShopping);
        btGoShopping.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(Const.FRAGMENT_ITEMLIST);
            }
        });

        //else
        //get Keep Product List

        return view;
    }
}
