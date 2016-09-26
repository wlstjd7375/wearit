package kr.wearit.android.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.wearit.android.R;

/**
 * Created by KimJS on 2016-09-26.
 */
public class KeepFragment extends Fragment {

    private TextView tvToolbarTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keep, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("KEEP");

        return view;
    }
}
