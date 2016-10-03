package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.adapter.MyPageAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.UserApi;
import kr.wearit.android.view.CouponActivity;
import kr.wearit.android.view.CustomerServiceActivity;
import kr.wearit.android.view.MainActivity;
import kr.wearit.android.view.SettingActivity;
import kr.wearit.android.view.account.MyInfoActivity;

/**
 * Created by KimJS on 2016-09-17.
 */
public class MyPageFragment extends Fragment {

    private String TAG = "MyPageFragment##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ListView lvMyPage;
    private ArrayList<String> itemList;
    private MyPageAdapter mAdapter;

    private String[] mypagetList = {"MY INFO", "ORDER LIST", "COUPON", "SETTING", "CUSTOMER SERVICE", "LOG OUT"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        mContext = getActivity();

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        lvMyPage = (ListView)view.findViewById(R.id.lvMyPage);

        tvToolbarTitle.setText("MY PAGE");

        itemList = new ArrayList<>();
        itemList.add(mypagetList[0]);
        itemList.add(mypagetList[1]);
        itemList.add(mypagetList[2]);
        itemList.add(mypagetList[3]);
        itemList.add(mypagetList[4]);
        itemList.add(mypagetList[5]);
        mAdapter = new MyPageAdapter(mContext, itemList);
        lvMyPage.setAdapter(mAdapter);

        lvMyPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;

                //0 부터 시작
                switch (position) {
                    case 0: //My Info Activity
                        intent = new Intent(mContext, MyInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 2: //Coupon Activity
                        intent = new Intent(mContext, CouponActivity.class);
                        startActivity(intent);
                        break;
                    case 3: //Setting Activity
                        intent = new Intent(mContext, SettingActivity.class);
                        startActivity(intent);
                        break;
                    case 4: //Customer Service
                        intent = new Intent(getActivity(), CustomerServiceActivity.class);
                        startActivity(intent);
                        break;
                    case 5: //LOG OUT api 필요한지
                        UserApi.logout(new Api.OnWaitListener<Void>(getActivity()) {
                            @Override
                            public void onSuccess(Void data) {
                                App.getInstance().logout();
                                //TODO go to main fragment
                                ((MainActivity)getActivity()).changeFragment(1);
                            }
                        });
                        break;

                }
            }
        });

        return view;
    }
}
