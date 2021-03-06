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

import com.google.android.gcm.GCMRegistrar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.adapter.MyPageAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.DeviceApi;
import kr.wearit.android.controller.UserApi;
import kr.wearit.android.view.CouponActivity;
import kr.wearit.android.view.CustomerServiceActivity;
import kr.wearit.android.view.MainActivity;
import kr.wearit.android.view.OrderListActivity;
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
        for(int i=0; i < mypagetList.length; i++) {
            itemList.add(mypagetList[i]);
        }

        mAdapter = new MyPageAdapter(mContext, itemList);
        lvMyPage.setAdapter(mAdapter);

        lvMyPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent;

                //0 부터 시작
                switch (position) {
                    case 0: //My Info Activity
                        mStartActivity(MyInfoActivity.class);
                        break;
                    case 1:
                        mStartActivity(OrderListActivity.class);
                        break;
                    case 2: //Coupon Activity
                        mStartActivity(CouponActivity.class);
                        break;
                    case 3: //Setting Activity
                        mStartActivity(SettingActivity.class);
                        break;
                    case 4: //Customer Service
                        mStartActivity(CustomerServiceActivity.class);
                        break;
                    case 5: //LOG OUT api 필요한지
                        DeviceApi.unregister(GCMRegistrar.getRegistrationId(getActivity()),new Api.OnWaitListener<Void>(getActivity()) {

                            @Override
                            public void onSuccess(Void data) {
                                GCMRegistrar.unregister(getActivity());
                            }
                        });
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

    private void mStartActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }
}
