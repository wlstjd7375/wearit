package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kr.wearit.android.R;
import kr.wearit.android.view.account.LoginActivity;
import kr.wearit.android.view.account.SignupActivity;

/**
 * Created by KimJS on 2016-09-17.
 */
public class MyPageGuestFragment extends Fragment {

    private TextView tvToolbarTitle;

    private Button btSignUp;
    private Button btLogin;

    private TextView tvSetting;
    private TextView tvCustomService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage_guest, container, false);

        tvToolbarTitle = (TextView) view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("MY PAGE");

        btSignUp = (Button)view.findViewById(R.id.btSignUp);
        btSignUp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });
        btLogin = (Button)view.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
