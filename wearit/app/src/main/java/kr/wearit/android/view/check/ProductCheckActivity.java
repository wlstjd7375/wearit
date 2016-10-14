package kr.wearit.android.view.check;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.OrderApi;
import kr.wearit.android.model.Coupon;
import kr.wearit.android.model.Order;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductSize;
import kr.wearit.android.model.User;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.TextUtil;
import kr.wearit.android.util.Util;

public class ProductCheckActivity extends CheckBaseActivity {

    private static final String TAG = "ProductCheckActivity##";
    private static final String ARG = "product";
    private static final String ARG_SIZE = "size";
    private static final String ARG_COUNT = "count";
    private static final String ARG_DELI = "deliver";
    private static final String ARG_TYPE = "ordertype";

    public static void launch(Activity activity, Product product, final ProductSize selectSize, int count, int deliverPrice, String orderType) {

        App.getInstance().setUserMileage(activity);

        Intent intent = new Intent(activity, ProductCheckActivity.class);
        intent.putExtra(ARG, product);
        intent.putExtra(ARG_SIZE, selectSize);
        intent.putExtra(ARG_COUNT, count);
        intent.putExtra(ARG_DELI, deliverPrice);
        intent.putExtra(ARG_TYPE, orderType);

        activity.startActivity(intent);
    }
    private static Context mContext;

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

    //시간 설정
    private Button btSelectTime;
    private String selectedDateTiem;

    //추가상품 구성하기
    private Button btAddProduct;
    private TextView tvAddProduct;
    private TextView tvResetOption;
    private ArrayList<Product> optionItemList;
    private LinearLayout llOptionItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItem = getIntent().getParcelableExtra(ARG);
        mSize = getIntent().getParcelableExtra(ARG_SIZE);
        count = getIntent().getIntExtra(ARG_COUNT, 0);
        deliverPrice = getIntent().getIntExtra(ARG_DELI, 0);
        orderType = getIntent().getStringExtra(ARG_TYPE);

        coupon = -1;
        useCoupon = null;
        user = App.getInstance().getUser();
        couponList = App.getInstance().getCouponList();

        setContentView(R.layout.activity_product_check);
        mContext = this;

        ((TextView) findViewById(R.id.tv_brand)).setText(mItem.getBrandObject().getName());

        initProduct();
        initUser();
        initTotal();

        initDateTime();
        initAddOption();

        btOrder = (Button) findViewById(R.id.bt_payment);
        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                OrderApi.addDIrect(order, new Api.OnAuthListener<Order>() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onSuccess(Order data) {
                        if(data != null) {
                            if(paytype.equals("account") || paytype.equals("later")){
                                Intent intent = new Intent(ProductCheckActivity.this, OrderCompleteActivity.class);
                                intent.putExtra("order",data);
                                startActivity(intent);
                                finish();
                            }
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

    @Override
    protected void onResume() {
        super.onResume();
        if(isDateTimePicked()) {
            selectedDateTiem = getDateTimeResult();
            //TODO 시간선택하고 다른거 할거있나
            btSelectTime.setText(selectedDateTiem);
            //Toast.makeText(this, selectedDateTiem, Toast.LENGTH_SHORT).show();
        }
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
        if(paytype == null || paytype.length() == 0) {
            Toast.makeText(getActivity(),"결제 수단을 선택해주세요.",Toast.LENGTH_SHORT).show();
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

        etRequire = (EditText) findViewById(R.id.et_require);

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


        ((RelativeLayout) findViewById(R.id.rl_select_paytype)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout) findViewById(R.id.ll_possible_paytype)).setVisibility(View.VISIBLE);
            }
        });

        ((RelativeLayout) findViewById(R.id.rl_account_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype = "account";
                ((TextView) findViewById(R.id.tv_select_paytype)).setText(TextUtil.getPaytype(paytype));
                ((LinearLayout) findViewById(R.id.ll_possible_paytype)).setVisibility(View.GONE);
            }
        });

        ((RelativeLayout) findViewById(R.id.rl_card_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype = "card";
                ((TextView) findViewById(R.id.tv_select_paytype)).setText(TextUtil.getPaytype(paytype));
                ((LinearLayout) findViewById(R.id.ll_possible_paytype)).setVisibility(View.GONE);
            }
        });

        ((RelativeLayout) findViewById(R.id.rl_phone_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype = "phone";
                ((TextView) findViewById(R.id.tv_select_paytype)).setText(TextUtil.getPaytype(paytype));
                ((LinearLayout) findViewById(R.id.ll_possible_paytype)).setVisibility(View.GONE);
            }
        });

        ((RelativeLayout) findViewById(R.id.rl_later_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype = "later";
                ((TextView) findViewById(R.id.tv_select_paytype)).setText(TextUtil.getPaytype(paytype));
                ((LinearLayout) findViewById(R.id.ll_possible_paytype)).setVisibility(View.GONE);
            }
        });

        if(orderType.equals("reservation")) {
            ((LinearLayout) findViewById(R.id.ll_reservation)).setVisibility(View.VISIBLE);
        }
        else {
            ((LinearLayout) findViewById(R.id.ll_reservation)).setVisibility(View.GONE);
        }
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

    private void initDateTime() {
        //TODO 배송 불가능한 날짜, 시간 가져오기
        btSelectTime = (Button)findViewById(R.id.bt_select_time);
        btSelectTime.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 시간 쳌쳌

                showDialog();
            }
        });
    }

    private void initAddOption() {
        final ArrayList<Integer> brandList = getBrandList();

        tvAddProduct = (TextView)findViewById(R.id.tv_add_product);
        btAddProduct = (Button)findViewById(R.id.bt_add_product);
        btAddProduct.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductCheckActivity.this, AddOptionActivity.class);
                intent.putIntegerArrayListExtra("brand_name", brandList);
                startActivityForResult(intent, Const.GET_OPTION_ITEMS);
            }
        });
    }

    private ArrayList<Integer> getBrandList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(mItem.getBrandObject().getKey());

        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Const.GET_OPTION_ITEMS) {
            optionItemList = data.getParcelableArrayListExtra("option_item_list");
            if(optionItemList.size() != 0) {
                //옵션 아이템 레이아웃 인플레이트
                llOptionItems = (LinearLayout)findViewById(R.id.llOptionItems);

                //기존에 있던 버튼과 안내문구 안보이게 하고
                tvAddProduct.setVisibility(View.GONE);
                btAddProduct.setVisibility(View.GONE);

                //리스트 추가
                llOptionItems.setVisibility(View.VISIBLE);
                //회색 구분선
                llOptionItems.addView(getDivider());
                for(Product product : optionItemList) {
                    //아이템 레이아웃 추가
                    llOptionItems.addView(getItemLayout(product));
                    llOptionItems.addView(getDivider());
                }

                //다시 설정하기 버튼 보이기
                tvResetOption = (TextView)findViewById(R.id.tvResetOption);
                tvResetOption.setVisibility(View.VISIBLE);
                tvResetOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //다시 설정했을때 옵션 초기화, 리스트 삭제, 버튼과 안내문구 보여주기
                        optionItemList.clear();
                        llOptionItems.removeAllViews();
                        llOptionItems.setVisibility(View.GONE);
                        tvAddProduct.setVisibility(View.VISIBLE);
                        btAddProduct.setVisibility(View.VISIBLE);
                        tvResetOption.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
    }

    private View getItemLayout(Product mProduct) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.layout_listrow_keep, null);

        //set Bag List
        ImageView ivProduct = (ImageView)view.findViewById(R.id.ivProduct);
        TextView tvBrand = (TextView)view.findViewById(R.id.tvBrand);
        TextView tvProductName = (TextView)view.findViewById(R.id.tvProductName);
        TextView tvSalePrice = (TextView)view.findViewById(R.id.tvSalePrice);
        TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);

        //image
        int mScreenWidth = App.getInstance().getScreenWidth();
        ivProduct.getLayoutParams().height = mScreenWidth/2;
        ivProduct.getLayoutParams().width = mScreenWidth/3;
        ImageUtil.display(ivProduct, mProduct.getImagePath());

        tvBrand.setText(mProduct.getBrandName());
        tvProductName.setText(mProduct.getName());

        //tvSalePrice : 세일하기 전 가격
        //tvPrice : 판매가격
        if(mProduct.isSale()) {
            tvSalePrice.setText(mProduct.getPrice() + "원");
            tvPrice.setText(mProduct.getSalePrice() + "원");
            //오른쪽 정렬
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvPrice.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.tvSalePrice);
            tvPrice.setLayoutParams(params);
        } else {
            tvPrice.setText(mProduct.getPrice() + "원");
        }

        return view;
    }

    //회색 구분선
    private View getDivider() {
        View view = new View(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = 1;
        view.setLayoutParams(params);
        view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_gray));

        return view;
    }
}
