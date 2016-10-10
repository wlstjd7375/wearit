package kr.wearit.android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.OrderApi;
import kr.wearit.android.model.Order;

public class OrderActivity extends BaseActivity {
    int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        key = getIntent().getIntExtra("order",-1);

        setContentView(R.layout.activity_order);
        if(key != -1 ) {
            OrderApi.get(key, new Api.OnAuthDefaultListener<Order>() {
                @Override
                public void onSuccess(Order data) {
                    System.out.println("Size : " + data.getProducts().size());
                }
            });
        }
    }
}
