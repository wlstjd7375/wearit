package kr.wearit.android.view.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.ui.ScrollListener;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.TextUtil;
import kr.wearit.android.view.BaseActivity;

public class AddOptionActivity extends BaseActivity {

    private String TAG = "AddOptionActivity##";
    private Context mContext;
    private TextView tvToolbarTitle;
    private TextView tvRightButton; // clear

    private ArrayList<Integer> brandList;

    //toolbar item count
    private TextView tvSelectedCount;
    private TextView tvTotalCount;

    //List
    private ListView lvAddOption;
    private AddOptionAdapter mAdapter;
    private LinearLayout llAddProduct;

    //result
    private HashSet<Product> optionItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_item);

        Intent intent = getIntent();
        brandList = intent.getExtras().getIntegerArrayList("brand_name");


        init();
    }

    private void init() {
        mContext = this;
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("추가상품 구성하기");
        tvRightButton = (TextView)findViewById(R.id.tvRightButton);
        tvRightButton.setText("CLEAR");
        tvRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                optionItemList.clear();
                mAdapter.notifyDataSetChanged();
                tvSelectedCount.setText("0");
            }
        });

        optionItemList = new HashSet<>();

        //TODO item count
        tvSelectedCount = (TextView)findViewById(R.id.tvSelectedCount);
        tvTotalCount = (TextView)findViewById(R.id.tvTotalCount);

        lvAddOption = (ListView)findViewById(R.id.lvAddOption);
        lvAddOption.setOnScrollListener(mFetchHandler);
        mFetchHandler.fetch(1);

        llAddProduct = (LinearLayout)findViewById(R.id.llAddProduct);
        llAddProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //확인버튼
                //TODO
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("option_item_list", new ArrayList<Product>(optionItemList));
                setResult(Const.GET_OPTION_ITEMS, intent);
                finish();
            }
        });
    }

    private void getItemList(final int page) {
        String brand = "";
        int brandCount = brandList.size();
        for(int i = 0; i < brandCount; i++) {
            brand += String.valueOf(brandList.get(i));
            brand += ",";
        }
        brand = brand.substring(0, brand.length() - 1);
        ProductApi.getListByBrand(brand,"neworder", page, new Api.OnAuthDefaultListener<Pagination<Product>>(){
            @Override
            public void onSuccess(Pagination<Product> data) {
                Log.d(TAG, "total count = " + data.getList().size());
                if(mAdapter == null) {
                    mAdapter = new AddOptionAdapter(mContext, data.getList(), App.getInstance().getScreenWidth());
                    lvAddOption.setAdapter(mAdapter);
                }
                else {
                    if(page == 1) {
                        mAdapter.clear();
                    }

                    mAdapter.addAll(data.getList());
                    //rlWating.setVisibility(View.GONE);
                }
                mFetchHandler.onFetched(data);
            }
        });
    }

    private ScrollListener mFetchHandler = new ScrollListener() {

        @Override
        public void onFetch(final ScrollListener listener, final int page) {
            //TODO Progressbar
            Log.d(TAG, "onFetch page = " + page);
            getItemList(page);
        }
    };


    //inner class, Adapter
    public class AddOptionAdapter extends ArrayAdapter<Product> {
        private String TAG = "AddOptionAdapter##";
        private Context mContext;
        private ArrayList<Product> mDataList;
        private int mScreenWidth;

        // View lookup cache
        private class ViewHolder {
            //Left
            LinearLayout itemLayoutLeft;
            RelativeLayout rlProductLeft;
            ImageView ivProductLeft;
            ImageView ivCheckLeft;
            TextView tvProductBrandLeft;
            TextView tvProductNameLeft;
            TextView tvProductPriceFinalLeft;
            TextView tvProductSalePriceLeft;
            TextView tvArrowLeft;

            //Right
            LinearLayout itemLayoutRight;
            RelativeLayout rlProductRight;
            ImageView ivProductRight;
            ImageView ivCheckRight;
            TextView tvProductBrandRight;
            TextView tvProductNameRight;
            TextView tvProductPriceFinalRight;
            TextView tvProductSalePriceRight;
            TextView tvArrowRight;

        }

        public AddOptionAdapter(Context context, int screenWidth) {
            super(context, R.layout.listrow_option_product_list);
            mScreenWidth = screenWidth;
        }

        public AddOptionAdapter(Context context, ArrayList<Product> arrayList, int screenWidth) {
            super(context, R.layout.listrow_option_product_list, arrayList);
            // TODO Auto-generated constructor stub
            mContext = context;
            mDataList = arrayList;
            mScreenWidth = screenWidth;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder viewHolder;
            if(view == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.listrow_option_product_list, parent, false);

                //Left
                viewHolder.itemLayoutLeft = (LinearLayout)view.findViewById(R.id.llProductLeft);
                viewHolder.rlProductLeft = (RelativeLayout)view.findViewById(R.id.rlProductLeft);
                viewHolder.ivProductLeft = (ImageView)view.findViewById(R.id.ivProductLeft);
                viewHolder.ivCheckLeft = (ImageView)view.findViewById(R.id.ivCheckLeft);
                viewHolder.tvProductBrandLeft = (TextView)view.findViewById(R.id.tvProductBrandLeft);
                viewHolder.tvProductNameLeft = (TextView)view.findViewById(R.id.tvProductNameLeft);
                viewHolder.tvProductPriceFinalLeft = (TextView)view.findViewById(R.id.tvProductPriceFinalLeft);
                viewHolder.tvProductSalePriceLeft = (TextView)view.findViewById(R.id.tvProductSalePriceLeft);
                viewHolder.tvArrowLeft = (TextView)view.findViewById(R.id.tvArrowLeft);

                viewHolder.rlProductLeft.getLayoutParams().height = mScreenWidth/2;
                viewHolder.rlProductLeft.requestLayout();

                //Right
                viewHolder.itemLayoutRight = (LinearLayout)view.findViewById(R.id.llProductRight);
                viewHolder.rlProductRight = (RelativeLayout)view.findViewById(R.id.rlProductRight);
                viewHolder.ivProductRight = (ImageView)view.findViewById(R.id.ivProductRight);
                viewHolder.ivCheckRight = (ImageView)view.findViewById(R.id.ivCheckRight);
                viewHolder.tvProductBrandRight = (TextView)view.findViewById(R.id.tvProductBrandRight);
                viewHolder.tvProductNameRight = (TextView)view.findViewById(R.id.tvProductNameRight);
                viewHolder.tvProductPriceFinalRight = (TextView)view.findViewById(R.id.tvProductPriceFinalRight);
                viewHolder.tvProductSalePriceRight = (TextView)view.findViewById(R.id.tvProductSalePriceRight);
                viewHolder.tvArrowRight = (TextView)view.findViewById(R.id.tvArrowRight);

                viewHolder.rlProductRight.getLayoutParams().height = mScreenWidth/2;
                viewHolder.rlProductRight.requestLayout();

                view.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) view.getTag();
            }

            final int idx = position * 2;
            //Log.d(TAG, "idx = " + idx);

            //Add Left
            final Product itemLeft = getItem(idx);
            ImageUtil.display(viewHolder.ivProductLeft, itemLeft.getImagePath());

            if(optionItemList.contains(itemLeft)) {
                viewHolder.ivCheckLeft.setImageResource(R.drawable.check_green);
            } else {
                viewHolder.ivCheckLeft.setImageResource(R.drawable.check_gray);
            }

            viewHolder.tvProductBrandLeft.setText(itemLeft.getBrandName());
            viewHolder.tvProductNameLeft.setText(itemLeft.getName());

            //가격
            if(itemLeft.hasStock()) {
                viewHolder.tvProductPriceFinalLeft.setText(TextUtil.formatPriceWon(itemLeft.getPrice()));
                //TODO sale price
                if(itemLeft.isSale()) {
                    viewHolder.tvProductSalePriceLeft.setVisibility(View.VISIBLE);
                    SpannableString content = new SpannableString(TextUtil.formatPriceWon(itemLeft.getSalePrice()));
                    content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                    viewHolder.tvProductSalePriceLeft.setText(content);

                    viewHolder.tvArrowLeft.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.tvProductPriceFinalLeft.setText("SOLD OUT");
            }
            //!가격

            viewHolder.itemLayoutLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "idx = " + idx);
                    if(optionItemList.contains(itemLeft)) {
                        optionItemList.remove(itemLeft);
                        tvSelectedCount.setText(optionItemList.size() + "");
                        viewHolder.ivCheckLeft.setImageResource(R.drawable.check_gray);
                    } else {
                        optionItemList.add(itemLeft);
                        tvSelectedCount.setText(optionItemList.size() + "");
                        viewHolder.ivCheckLeft.setImageResource(R.drawable.check_green);
                    }
                }
            });


            //Add Right
            final int idxRight = idx + 1;
            if(idxRight < mDataList.size() ) {
                final Product itemRight = getItem(idxRight);
                viewHolder.itemLayoutRight.setVisibility(View.VISIBLE);

                ImageUtil.display(viewHolder.ivProductRight, itemRight.getImagePath());

                if(optionItemList.contains(itemRight)) {
                    viewHolder.ivCheckRight.setImageResource(R.drawable.check_green);
                } else {
                    viewHolder.ivCheckRight.setImageResource(R.drawable.check_gray);
                }

                viewHolder.tvProductBrandRight.setText(itemRight.getBrandName());
                viewHolder.tvProductNameRight.setText(itemRight.getName());

                //가격
                if(itemRight.hasStock()) {
                    viewHolder.tvProductPriceFinalRight.setText(TextUtil.formatPriceWon(itemRight.getPrice()));
                    //TODO sale price
                    if(itemRight.isSale()) {
                        viewHolder.tvProductSalePriceRight.setVisibility(View.VISIBLE);
                        SpannableString content = new SpannableString(TextUtil.formatPriceWon(itemRight.getSalePrice()));
                        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                        viewHolder.tvProductSalePriceRight.setText(content);

                        viewHolder.tvArrowRight.setVisibility(View.VISIBLE);
                    }
                } else {
                    viewHolder.tvProductPriceFinalRight.setText("SOLD OUT");
                }
                //!가격

                viewHolder.itemLayoutRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "idx = " + idxRight);
                        if(optionItemList.contains(itemRight)) {
                            optionItemList.remove(itemRight);
                            tvSelectedCount.setText(optionItemList.size() + "");
                            viewHolder.ivCheckRight.setImageResource(R.drawable.check_gray);
                        } else {
                            optionItemList.add(itemRight);
                            tvSelectedCount.setText(optionItemList.size() + "");
                            viewHolder.ivCheckRight.setImageResource(R.drawable.check_green);
                        }
                    }
                });
            } else {
                viewHolder.itemLayoutRight.setVisibility(View.INVISIBLE);
                viewHolder.itemLayoutRight.setClickable(false);
                viewHolder.itemLayoutRight.setFocusable(false);
            }

            return view;
        }

        @Override
        public int getCount() {
            int size = mDataList.size();
            //Log.d(TAG, "size in getCount = " + size);
            return (size / 2) + (size % 2);
        }
    }
}
