package kr.wearit.android.view.product;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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

import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.CartApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductSize;
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
    private LinearLayout llSelector;
    private TextView tvSelSize;

    private int selSize;
    private int count;

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
                    initialize_selector();
                }
            });
        }
        selSize = 0;

        llSelector = (LinearLayout) findViewById(R.id.ll_selector);
        rlWating = (RelativeLayout) findViewById(R.id.rl_waiting);
        rlWating.setVisibility(View.VISIBLE);
    }

    public void initialize_selector(){
        tvSelSize = (TextView) findViewById(R.id.tv_sel_size);
        ((RelativeLayout) findViewById(R.id.rl_size_sel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OptionDialog(getActivity()).show();
            }
        });

        ((RelativeLayout) findViewById(R.id.rl_plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.valueOf(((TextView) findViewById(R.id.tv_count)).getText().toString());
                ((TextView) findViewById(R.id.tv_count)).setText(String.valueOf(currentCount+1));
                count = currentCount + 1;
            }
        });

        ((RelativeLayout) findViewById(R.id.rl_minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > 1) {
                    int currentCount = Integer.valueOf(((TextView) findViewById(R.id.tv_count)).getText().toString());
                    ((TextView) findViewById(R.id.tv_count)).setText(String.valueOf(currentCount - 1));
                    count = currentCount - 1;
                }
            }
        });

        ((TextView) findViewById(R.id.tv_insel_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selSize != 0) {
                    new OrderDialog(getActivity()).show();
                }
            }
        });

        ((ImageButton) findViewById(R.id.bt_insel_itbag)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selSize != 0) {
                    CartApi.add(selSize, count, new Api.OnAuthListener<Integer>() {
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(Integer data) {
                            Toast.makeText(getActivity(), "ITBAG에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            llSelector.setVisibility(View.GONE);
                        }
                        @Override
                        public void onFail() {
                            System.out.println("실패 ㅅㅂ");
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "옵션과 수량을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        ((ImageButton) findViewById(R.id.bt_itbag)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout)findViewById(R.id.ll_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((TextView) findViewById(R.id.tv_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout)findViewById(R.id.ll_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((ScrollView) findViewById(R.id.sv_product)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((LinearLayout) findViewById(R.id.ll_selector)).getVisibility() == View.VISIBLE){
                    ((LinearLayout) findViewById(R.id.ll_selector)).setVisibility(View.GONE);
                }
            }
        });


        //Add to Keep
        ((TextView)findViewById(R.id.tv_keep)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductApi.addFavorite(mItem, new Api.OnAuthListener() {

                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(Object data) {
                        Toast.makeText(getApplicationContext(), "KEEP에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFail() {
                    }
                });
            }
        });
    }

    private class OrderDialog extends Dialog {

        public OrderDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_order);

            ((TextView) findViewById(R.id.tv_now_order)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            ((TextView) findViewById(R.id.tv_later_order)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }

    private class OptionDialog extends Dialog {
        private ListView list;
        private HintAdapter mAdapter;
        public OptionDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_size);

            mAdapter = new HintAdapter();
            mAdapter.addAll(mItem.getProductSizes());
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(mAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!mAdapter.getItem(position).isSoldout()) {
                        Toast.makeText(getActivity(),"품절된 옵션입니다",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //selSize = position;
                        selSize = mAdapter.getItem(position).getKey();
                        tvSelSize.setText(mAdapter.getItem(position).getSize());
                        ((TextView) getActivity().findViewById(R.id.tv_insel_order)).setTextColor(Color.parseColor("#000000"));
                        dismiss();
                    }
                }
            });
        }

        private class HintAdapter extends ArrayAdapter<ProductSize> {
            public HintAdapter() {
                super(getActivity(), 0);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                ProductSize item = getItem(position);

                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_size, parent, false);

                if(item.isSoldout()) {
                    ((TextView) view.findViewById(R.id.tv_soldout)).setVisibility(View.GONE);

                    ((TextView) view.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tv_name)).setText(item.getSize());
                }
                else {
                    ((TextView) view.findViewById(R.id.tv_name)).setVisibility(View.GONE);

                    ((TextView) view.findViewById(R.id.tv_soldout)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tv_soldout)).setText(item.getSize());
                }

                return view;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        update();
    }


    @Override
    public void onBackPressed() {
        if(findViewById(R.id.ll_selector).getVisibility() == View.VISIBLE) {
            findViewById(R.id.ll_selector).setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }
}


