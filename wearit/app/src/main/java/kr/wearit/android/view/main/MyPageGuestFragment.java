package kr.wearit.android.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kr.wearit.android.R;

/**
 * Created by KimJS on 2016-09-17.
 */
public class MyPageGuestFragment extends Fragment {

    private TextView tvToolbarTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage_guest, container, false);

        tvToolbarTitle = (TextView) view.findViewById(R.id.tvToolbarTitle);

        tvToolbarTitle.setText("MY PAGE");

        return view;
    }
}
