package kr.wearit.android.view.check;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.wearit.android.R;

public class CheckActivity extends CheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Button btSetDateTime = (Button)findViewById(R.id.btSetDateTime);
        btSetDateTime.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
}
