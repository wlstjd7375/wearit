package kr.wearit.android.view.order;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.OrderApi;
import kr.wearit.android.controller.UserApi;
import kr.wearit.android.model.CardPay;
import kr.wearit.android.model.Coupon;
import kr.wearit.android.model.Order;
import kr.wearit.android.model.Phone;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductSize;
import kr.wearit.android.model.User;
import kr.wearit.android.view.BaseActivity;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.Util;

public class ProductPaymentActivity extends BaseActivity {

    private static final String ARG = "product";
    private static final String ARG_SIZE = "size";
    private static final String ARG_COUNT = "count";
    private static final String ARG_DELI = "deliver";
    private static final String ARG_TYPE = "ordertype";

    public static void launch(Activity activity, Product product, final ProductSize selectSize, int count, int deliverPrice, String orderType) {

        App.getInstance().setUserMileage(activity);

        Intent intent = new Intent(activity, ProductPaymentActivity.class);
        intent.putExtra(ARG, product);
        intent.putExtra(ARG_SIZE, selectSize);
        intent.putExtra(ARG_COUNT, count);
        intent.putExtra(ARG_DELI, deliverPrice);
        intent.putExtra(ARG_TYPE, orderType);

        activity.startActivity(intent);
    }

    private Product mItem;
    private ProductSize mSize;
    private int count;
    private String orderType;

    private User user;

    private EditText etRequire;

    private int coupon;

    private Order order;

    private boolean orderFlag;

    private int totalCheck;
    private int deliverPrice;

    private int couponPrice;

    private Coupon useCoupon;

    private ArrayList<Coupon> couponList;

    private Button btOrder;
    private String paytype;

    private TextView tvReceiverName;
    private TextView tvReceiverPhone;
    private TextView tvReceiverEmail;
    private TextView tvReceiverAddr1;
    private TextView tvReceiverAddr2;

    private LinearLayout llCouponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItem = getIntent().getParcelableExtra(ARG);
        mSize = getIntent().getParcelableExtra(ARG_SIZE);
        count = getIntent().getIntExtra(ARG_COUNT, 0);
        deliverPrice = getIntent().getIntExtra(ARG_DELI, 0);
        orderType = getIntent().getStringExtra(ARG_TYPE);

        useCoupon = null;
        user = App.getInstance().getUser();
        couponList = App.getInstance().getCouponList();

        setContentView(R.layout.activity_product_payment);

        ((TextView) findViewById(R.id.tv_brand)).setText(mItem.getBrandObject().getName());

        initProduct();
        initUser();
        initTotal();

        btOrder = (Button) findViewById(R.id.bt_payment);
        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!orderFlag){
                    return;
                }

                if(!validate()){
                    return;
                }

                order = new Order();
                order.setUser(App.getInstance().getUser().getKey());
                order.setOrdertype(orderType);
                order.setProductkey(mSize.getKey());
                order.setProductCount(count);
                order.setPaytype(paytype);
                order.setOrdername((tvReceiverName).getText().toString());
                order.setOrderphone((tvReceiverPhone).getText().toString());
                order.setOrdermail((tvReceiverEmail).getText().toString());
                order.setReceivername((tvReceiverName).getText().toString());
                order.setReceiverphone((tvReceiverPhone).getText().toString());

                order.setAddress1((tvReceiverAddr1).getText().toString());
                order.setAddress2((tvReceiverAddr2).getText().toString());
                order.setRequest(etRequire.getText().toString());

                String orderPrice = ((TextView) findViewById(R.id.tv_total_price)).getText().toString();
                order.setPrice(getPrice(orderPrice.substring(0, orderPrice.length() - 2)));

                if(coupon != -1) {
                    order.setCoupon(new Integer(coupon));
                    order.setCouponprice(couponPrice);
                }
                else{
                    order.setCoupon(null);
                    order.setCouponprice(0);
                }

                OrderApi.addDIrect(order, new Api.OnAuthListener<Integer>() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onSuccess(Integer data) {
                        if(data != null) {
//                            if(paytype.equals("account")){
//                                OrderCompleteActivity.launch(getActivity(), paytype, data);
//                                finish();
//                            }
//                            else if(paytype.equals("card")){
//                                CardPay cardPay = new CardPay();
//                                cardPay.setOrdernum(data);
//                                cardPay.setProductname(mItem.getName());
//                                cardPay.setUser(App.getInstance().getUser().getId());
//
//                                CardPaymentActivity.launch(getActivity(), cardPay, data, order.getOrdertype());
//                            }
//                            else if(paytype.equals("phone")){
//                                Display display = getActivity().getWindowManager().getDefaultDisplay();
//                                Point size = new Point();
//                                display.getSize(size);
//                                int height = size.y;
//
//                                Phone phone = new Phone();
//                                phone.setEmail(etOrderMail.getText().toString());
//                                phone.setOrder(data);
//                                phone.setPhonenum(etOrderPhone.getText().toString());
//                                phone.setProductname(mItem.getName());
//                                phone.setUser(App.getInstance().getUser().getId());
//                                phone.setHeight(String.valueOf(height));
//
//                                PhonePaymentActivity.launch(getActivity(), phone, data, order.getOrdertype());
//                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFail() {

                    }
                });
            }
        });
    }

    public boolean validate(){
        if(tvReceiverName.getText().length() == 0){
            Toast.makeText(getActivity(),"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tvReceiverPhone.getText().length() == 0){
            Toast.makeText(getActivity(),"핸드폰 번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tvReceiverEmail.getText().length() == 0){
            Toast.makeText(getActivity(),"이메일을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tvReceiverAddr1.getText().length() == 0){
            Toast.makeText(getActivity(),"주소를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tvReceiverAddr2.getText().length() == 0){
            Toast.makeText(getActivity(),"상세 주소를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void initTotal(){
        int totalPrice  = count * mItem.getPriceCurrent();
        if(useCoupon != null) {
            if(useCoupon.getSale_cost() != null && useCoupon.getSale_cost() != 0) {
                totalPrice -= useCoupon.getSale_cost();
            }
            else if(useCoupon.getSale_rate() != null && useCoupon.getSale_rate() != 0) {
                totalPrice = totalPrice - (((totalPrice) * useCoupon.getSale_rate())/100);
            }
        }
        totalCheck = totalPrice + deliverPrice;
        ((TextView) findViewById(R.id.tv_total_price)).setText(Util.formatWon(totalCheck) + "원");

        ((TextView) findViewById(R.id.tv_calcul_tip)).setText(Util.formatWon(deliverPrice) + "원");
        ((TextView) findViewById(R.id.tv_tip_price)).setText(Util.formatWon(deliverPrice) + "원");
    }

    public void initUser(){
        tvReceiverName = (TextView) findViewById(R.id.tv_receiver_name);
        tvReceiverEmail = (TextView) findViewById(R.id.tv_receiver_email);
        tvReceiverPhone = (TextView) findViewById(R.id.tv_receiver_phone);
        tvReceiverAddr1 = (TextView) findViewById(R.id.tv_receiver_address1);
        tvReceiverAddr2 = (TextView) findViewById(R.id.tv_receiver_address2);

        tvReceiverName.setText(user.getName());
        tvReceiverPhone.setText(user.getPhone());
        tvReceiverEmail.setText(user.getId());
        tvReceiverAddr1.setText(user.getAddress1());
        tvReceiverAddr2.setText(user.getAddress2());

        ((TextView) findViewById(R.id.tv_modify_receiver)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //수정 버튼 눌렀을 때 코딩
            }
        });
        //쿠폰 목록 만들기
        llCouponList = ((LinearLayout) findViewById(R.id.ll_possible_coupon));

        for(int i=0;i<couponList.size();i++) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.listrow_order_coupon, null);
            ((TextView) view.findViewById(R.id.tv_coupon_name)).setText(couponList.get(i).getName());
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    useCoupon = couponList.get(finalI);
                    coupon = couponList.get(finalI).getKey();
                    ((TextView) findViewById(R.id.tv_select_coupon)).setText(couponList.get(finalI).getName());
                    llCouponList.setVisibility(View.GONE);
                    initTotal();
                }
            });
            llCouponList.addView(view);
        }
        ((RelativeLayout) findViewById(R.id.rl_select_coupon)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llCouponList.getVisibility() == View.VISIBLE){
                    llCouponList.setVisibility(View.GONE);
                }
                else {
                    llCouponList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initProduct(){
        //Product 부분
        ((TextView) findViewById(R.id.tv_brand_in_item)).setText(mItem.getBrandObject().getName());
        if (mItem.isSale()) {
            ((TextView) findViewById(R.id.tvSalePrice)).setText(Util.formatWon(mItem.getPriceCancel()) + "원");
            ((TextView) findViewById(R.id.tvSalePrice)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.tvSalePrice)).setVisibility(View.GONE);
        }
        ImageUtil.display(((ImageView) findViewById(R.id.ivProduct)), mItem.getImagePath());

        ((TextView) findViewById(R.id.tvSize)).setText("SIZE : " + mSize.getSize());
        ((TextView) findViewById(R.id.tvItemCount)).setText("개수 : " + String.valueOf(count));

        ((TextView) findViewById(R.id.tvPrice)).setText(Util.formatWon(count * mItem.getPriceCurrent())+"원");

        ((TextView) findViewById(R.id.tv_calcul_price)).setText(Util.formatWon(count * mItem.getPriceCurrent()) + "원");
        ((TextView) findViewById(R.id.tv_order_product_price)).setText(Util.formatWon(count * mItem.getPriceCurrent())+"원");
    }

    public int getPrice(String price) {
        String[] data = price.split("\\,");
        String num = "";
        for(int i=0;i<data.length;i++){
            num += data[i];
        }

        return Integer.valueOf(num);
    }
}
