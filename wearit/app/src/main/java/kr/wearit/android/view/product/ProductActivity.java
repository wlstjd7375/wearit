package kr.wearit.android.view.product;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.ContentPart;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductSize;
import kr.wearit.android.model.Resource;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.Util;

import kr.wearit.android.view.BaseActivity;
import kr.wearit.android.widget.ContentView;

public class ProductActivity extends BaseActivity {
    private final boolean LOG = Config.LOG;
    private static final String TAG = ProductActivity.class.getSimpleName();

    private static final String EXTRA_ITEM = "product";
    private static final String EXTRA_KEY = "key";

//    private CustomDialog customDialog;

    private Product mItem;
    private RelativeLayout rlWating;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LOG)
            Log.d(TAG, "onCreate");

        mItem = getIntent().getParcelableExtra(EXTRA_ITEM);
        int key = getIntent().getIntExtra(EXTRA_KEY, -1);
        setContentView(R.layout.activity_product);

        if(key != -1) {
            ProductApi.get(key, new Api.OnWaitListener<Product>(this) {

                @Override
                public void onSuccess(Product item) {
                    mItem = item;
                    initialize();
                }
            });
        }

        rlWating = (RelativeLayout) findViewById(R.id.rl_waiting);
        rlWating.setVisibility(View.VISIBLE);
    }

    public void initialize() {
        ImageUtil.display((ImageView) findViewById(R.id.iv_product),mItem.getImagePath());


        ((TextView) findViewById(R.id.tv_name)).setText(mItem.getName());

        TextView salePrice = (TextView) findViewById(R.id.tv_sale_price);
        TextView price = (TextView) findViewById(R.id.tv_price);

        if (mItem.isSale()) {
            salePrice.setVisibility(View.VISIBLE);
            salePrice.setText(Util.formatWon(mItem.getSalePrice()));

            price.setVisibility(View.VISIBLE);
            price.setText(Util.formatWon(mItem.getPrice()) + "원");
        } else {
            price.setVisibility(View.GONE);

            salePrice.setVisibility(View.VISIBLE);
            salePrice.setText(Util.formatWon(mItem.getPrice()) + "원");
        }


        String ment = "";
        for(int i=0;i<mItem.getDeliverInfos().size();i++){
            if(mItem.getDeliverInfos().get(i).getDeliverPrice() == 0){
                ment += mItem.getShopObject().getName() + " 상품으로만 "+ String.valueOf(mItem.getDeliverInfos().get(i).getBasis()) +"원 이상 구매시 무료배송";
            }
        }
        ((TextView) findViewById(R.id.tv_delivery)).setText(ment);

        if(mItem.getNote() != null && mItem.getNote().length() != 0) {
            ((LinearLayout) findViewById(R.id.ll_warning)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tv_warning)).setText(mItem.getNote());
        }
        else {
            ((LinearLayout) findViewById(R.id.ll_warning)).setVisibility(View.GONE);
        }

        ((LinearLayout) findViewById(R.id.ll_detail_sel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((LinearLayout) findViewById(R.id.ll_detail)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) findViewById(R.id.ll_detail)).setVisibility(View.GONE);
                }
                else {
                    ((LinearLayout) findViewById(R.id.ll_detail)).setVisibility(View.VISIBLE);
                }
            }
        });
        ((ContentView) findViewById(R.id.cv_product)).setContent(mItem.getContentObject(), "");

        boolean flag = false;

        if(mItem.getCountry() != null && mItem.getCountry().length() != 0) {
            ((TextView) findViewById(R.id.tv_country)).setText("제조국 : " + mItem.getCountry());
            flag = true;
        }
        else {
            ((TextView) findViewById(R.id.tv_country)).setVisibility(View.GONE);
        }
        if(mItem.getTotalsize() != null && mItem.getTotalsize().length() != 0) {
            ((TextView) findViewById(R.id.tv_size)).setText("사이즈 : " + mItem.getTotalsize());
            flag = true;
        }
        else {
            ((TextView) findViewById(R.id.tv_size)).setVisibility(View.GONE);
        }
        if(mItem.getColor() != null && mItem.getColor().length() != 0) {
            ((TextView) findViewById(R.id.tv_color)).setText("색상 : " + mItem.getColor());
            flag = true;
        }
        else {
            ((TextView) findViewById(R.id.tv_color)).setVisibility(View.GONE);
        }
        if(mItem.getMaterial() != null && mItem.getMaterial().length() != 0) {
            ((TextView) findViewById(R.id.tv_fabric)).setText("소재 : " + mItem.getMaterial());
            flag = true;
        }
        else {
            ((TextView) findViewById(R.id.tv_fabric)).setVisibility(View.GONE);
        }
        if(!flag) {
            ((LinearLayout) findViewById(R.id.ll_in_detail)).setVisibility(View.GONE);
        }

        flag = false;
        if ((mItem.getRealsizecomment().length() != 0) || (mItem.getRealsizeimagepath() != null)) {
            if (mItem.getRealsizeimagepath() != null) {
                ImageUtil.display((ImageView) findViewById(R.id.iv_real), mItem.getRealsizeimagepath());
                flag = true;
            } else {
                ((ImageView) findViewById(R.id.iv_real)).setVisibility(View.GONE);
            }
            if (!(mItem.getRealsizecomment().equals("null"))) {
                ((TextView) findViewById(R.id.tv_real)).setText(mItem.getRealsizecomment());
                flag = true;
            }
        }
        if(flag) {
            ((LinearLayout) findViewById(R.id.ll_size_sel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((LinearLayout) findViewById(R.id.ll_size)).getVisibility() == View.VISIBLE) {
                        ((LinearLayout) findViewById(R.id.ll_size)).setVisibility(View.GONE);
                    } else {
                        ((LinearLayout) findViewById(R.id.ll_size)).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        //추후에 브랜드로 바꿔야됨!
        ((LinearLayout) findViewById(R.id.ll_delivery_sel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((LinearLayout) findViewById(R.id.ll_delivery)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) findViewById(R.id.ll_delivery)).setVisibility(View.GONE);
                }
                else {
                    ((LinearLayout) findViewById(R.id.ll_delivery)).setVisibility(View.VISIBLE);
                }
            }
        });

        ((TextView) findViewById(R.id.tv_delivery_info)).setText(mItem.getShopObject().getDeliver_info());
        ((TextView) findViewById(R.id.tv_refund_info)).setText(mItem.getShopObject().getRefund_info());

        rlWating.setVisibility(View.GONE);
//        optionLayoutInitialize();

    }
//
//
//    public void optionLayoutInitialize(){
//        ((Button) findViewById(R.id.visit_question)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VisitGuideActivity.launch(getActivity());
//            }
//        });
//
//        findViewById(R.id.product_pay_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (selectedPosition != mItem.getProductSizes().size()) {
//                    if (!mItem.getProductSizes().get(selectedPosition).isSoldout()) {
//                        Toast.makeText(getActivity(), "품절된 옵션 입니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//                if (selectedPosition != mItem.getProductSizes().size() && cart_count > 0) {
//                    mTracker.send(new HitBuilders.EventBuilder().setCategory("buy").setAction("add").setLabel("product/" + String.valueOf(mItem.getKey())).build());
//                    try {
//                        AirBridge.goal("buy_item_info_option");
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    if(purchaseFlag == 2){
//                        mTracker.send(new HitBuilders.EventBuilder().setCategory("buy").setAction("add").setLabel("product/" + String.valueOf(mItem.getKey())).build());
//
//                        int tempkey = mItem.getProductSizes().get(selectedPosition).getKey();
//
//                        int price = 0;
//                        int deliverPrice = 0;
//
//                        if(mItem.isSale()) {
//                            price = mItem.getSalePrice() * cart_count;
//                        }
//                        else {
//                            price = mItem.getPrice() * cart_count;
//                        }
//
//                        for(int i=0;i<mItem.getDeliverInfos().size();i++){
//                            if(price >= mItem.getDeliverInfos().get(i).getBasis()) {
//                                deliverPrice = mItem.getDeliverInfos().get(i).getDeliverPrice();
//                                break;
//                            }
//                        }
//
//                        ProductPaymentActivity.launch(getActivity(), mItem, mItem.getProductSizes().get(selectedPosition), cart_count, deliverPrice, "normal");
//                        cartinFlag = false;
//                        cart_in_layout.setVisibility(View.INVISIBLE);
//                    }
//                    else if(purchaseFlag == 3) {
//                        //방문배송
//                        mTracker.send(new HitBuilders.EventBuilder().setCategory("visitbuy").setAction("add").setLabel("product/" + String.valueOf(mItem.getKey())).build());
//
//                        int tempkey = mItem.getProductSizes().get(selectedPosition).getKey();
//                        int price = 0;
//                        int deliverPrice = 0;
//                        int basis = 0;
//
//                        if(mItem.isSale()) {
//                            price = mItem.getSalePrice() * cart_count;
//                        }
//                        else {
//                            price = mItem.getPrice() * cart_count;
//                        }
////                        for(int i=0;i<mItem.getVisitDeliverInfos().size();i++){
////                            if(mItem.getVisitDeliverInfos().get(i).getDeliverPrice() == 0) {
////                                basis = mItem.getVisitDeliverInfos().get(i).getBasis();
////                            }
////                            if(price >= mItem.getVisitDeliverInfos().get(i).getBasis()) {
////                                deliverPrice = mItem.getVisitDeliverInfos().get(i).getDeliverPrice();
////                                break;
////                            }
////                        }
//                        if(price >= 200000) {
//                            deliverPrice = 0;
//                        }
//                        else {
//                            deliverPrice = 10000;
//                        }
//                        ProductVisitPaymentActivity.launch(getActivity(), mItem, mItem.getProductSizes().get(selectedPosition), cart_count, deliverPrice, basis, "visit");
//                        cartinFlag = false;
//                        cart_in_layout.setVisibility(View.INVISIBLE);
//
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "옵션을 정해주세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//        findViewById(R.id.product_cart_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(selectedPosition != mItem.getProductSizes().size()) {
//                    if (!mItem.getProductSizes().get(selectedPosition).isSoldout()) {
//                        Toast.makeText(getActivity(), "품절된 옵션 입니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//                if (selectedPosition != mItem.getProductSizes().size() && cart_count > 0) {
//
//                    int tempkey = mItem.getProductSizes().get(selectedPosition).getKey();
//                    mTracker.send(new HitBuilders.EventBuilder().setCategory("cart").setAction("add").setLabel("product/"+String.valueOf(mItem.getKey())).build());
//                    try {
//                        AirBridge.goal("cart_item_info_option");
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    CartApi.add(tempkey, cart_count, new Api.OnAuthListener<Integer>() {
//
//                        @Override
//                        public void onStart() {
//                        }
//
//                        @Override
//                        public void onSuccess(Integer data) {
//                            dialogFlag = true;
//                            customDialog = new CustomDialog(getActivity());
//                            customDialog.show();
//                        }
//
//                        @Override
//                        public void onFail() {
//                            System.out.println("실패 ㅅㅂ");
//                        }
//                    });
//
//                }
//                else {
//                    Toast.makeText(getActivity(), "옵션을 정해주세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        cartCount = (TextView) findViewById(R.id.cart_count_text);
//
//        findViewById(R.id.cart_count_minus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cart_count--;
//                if (cart_count <= 0) {
//                    cart_count = 0;
//                    cartCount.setText("수량");
//                } else {
//                    cartCount.setText("" + cart_count);
//                }
//            }
//        });
//
//        findViewById(R.id.cart_count_plus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cart_count++;
//                cartCount.setText("" + cart_count);
//
//            }
//        });
//
//        size = (TextView) findViewById(R.id.tv_size_selector);
//        size.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new OptionDialog(getActivity()).show();
//            }
//        });
//
//        Animation fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
//        rlWating.startAnimation(fadeout);
//        rlWating.setVisibility(View.GONE);
//    }

    @Override
    public void onResume() {
        super.onResume();
//        update();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
//
//    private class OptionDialog extends Dialog {
//        private ListView list;
//        private HintAdapter mAdapter;
//        public OptionDialog(Context context) {
//            super(context);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            setContentView(R.layout.dialog_option);
//
//            mAdapter = new HintAdapter();
//            mAdapter.addAll(mItem.getProductSizes());
//            list = (ListView) findViewById(R.id.list);
//            list.setAdapter(mAdapter);
//            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    selectedPosition = position;
//                    size.setText(mAdapter.getItem(position).getSize());
//                    dismiss();
//                }
//            });
//        }
//
//        private class HintAdapter extends ArrayAdapter<ProductSize> {
//            public HintAdapter() {
//                super(getActivity(), 0);
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = convertView;
//                ProductSize item = getItem(position);
//
//                view = LayoutInflater.from(getActivity()).inflate(R.layout.spinner_dropdown_row, parent, false);
//
//                if(item.isSoldout()) {
//                    ((TextView) view.findViewById(R.id.tv_item_soldout)).setVisibility(View.GONE);
//
//                    ((TextView) view.findViewById(R.id.tv_item)).setVisibility(View.VISIBLE);
//                    ((TextView) view.findViewById(R.id.tv_item)).setText(item.getSize());
//                }
//                else {
//                    ((TextView) view.findViewById(R.id.tv_item)).setVisibility(View.GONE);
//
//                    ((TextView) view.findViewById(R.id.tv_item_soldout)).setVisibility(View.VISIBLE);
//                    ((TextView) view.findViewById(R.id.tv_item_soldout)).setText(item.getSize());
//                }
//
//                return view;
//            }
//        }
//    }

}


