package kr.wearit.android.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import kr.wearit.android.R;

public class SettingActivity extends BaseActivity {

    private String LOG = "SettingActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private Switch swOnOff;

    private static final String PREF_SETTING = "_app_setting_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("CUSTOMER SERVICE");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swOnOff = (Switch)findViewById(R.id.swOnOff);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean setting = pref.getBoolean(PREF_SETTING, false); // Default : false
        swOnOff.setChecked(setting);
        swOnOff.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //TODO ON
                } else {
                    //TODO OFF
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save setting status
        boolean isChecked = swOnOff.isChecked();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putBoolean(PREF_SETTING, isChecked);
        editor.commit();
    }

}
