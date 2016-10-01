package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Context;
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
import kr.wearit.android.view.MainActivity;

/**
 * Created by KimJS on 2016-09-17.
 */
public class MyPageFragment extends Fragment implements
        View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    private String TAG = "MyPageFragment##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ListView lvMyPage;
    private ArrayList<String> itemList;
    private MyPageAdapter mAdapter;

    private String[] mypagetList = {"MY INFO", "ORDER LIST", "COUPON", "SETTING", "CUSTOMER SERVICE", "LOG OUT"};

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.d(TAG, date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
    }

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
                //0 부터 시작
                switch (position) {
                    case 4:
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
