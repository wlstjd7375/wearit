package kr.wearit.android.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.R;
import kr.wearit.android.model.Product;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.TextUtil;
import kr.wearit.android.view.product.ProductActivity;

/**
 * Created by KimJS on 2016-09-24.
 */
public class ProductListAdapter extends ArrayAdapter<Product> {
    private String TAG = "ProductListAdapter##";
    private Context mContext;
    private ArrayList<Product> mDataList;
    private int mScreenWidth;

    // View lookup cache
    private static class ViewHolder {
        LinearLayout llProductLeft;
        LinearLayout llProductRight;
    }

    public ProductListAdapter(Context context, int screenWidth) {
        super(context, R.layout.listrow_product_list);
        mScreenWidth = screenWidth;
    }

    public ProductListAdapter(Context context, ArrayList<Product> arrayList, int screenWidth) {
        super(context, R.layout.listrow_product_list, arrayList);
        // TODO Auto-generated constructor stub
        mContext = context;
        mDataList = arrayList;
        mScreenWidth = screenWidth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listrow_product_list, parent, false);

            viewHolder.llProductLeft = (LinearLayout)view.findViewById(R.id.llProductLeft);
            viewHolder.llProductRight = (LinearLayout)view.findViewById(R.id.llProductRight);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final int idx = position * 2;
        Log.d(TAG, "idx = " + idx + ", size = " + mDataList.size());
        //TODO NullPoint 왜뜸 시발
        viewHolder.llProductLeft.removeAllViews();
        viewHolder.llProductLeft.addView(getProductLayout(idx));
        viewHolder.llProductLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sibal = " + (idx));
                Intent intent = new Intent(getContext(), ProductActivity.class);
                intent.putExtra("key",getItem(idx).getKey());
                getContext().startActivity(intent);
            }
        });

        if((idx+1) < mDataList.size() ) {
            viewHolder.llProductRight.removeAllViews();
            viewHolder.llProductRight.addView(getProductLayout(idx+1));
            viewHolder.llProductRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProductActivity.class);
                    intent.putExtra("key",getItem(idx+1).getKey());
                    getContext().startActivity(intent);
                }
            });
        }
        else {
            viewHolder.llProductRight.removeAllViews();
            viewHolder.llProductRight.setClickable(false);
            viewHolder.llProductRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return view;
    }

    @Override
    public int getCount() {
        int size = mDataList.size();
        Log.d(TAG, "size in getCount = " + size);
        return (size / 2) + (size % 2);
    }

    private View getProductLayout(final int idx) {
        //Layout inflate
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemLayout = inflater.inflate(R.layout.layout_item_big, null);
        RelativeLayout llProduct = (RelativeLayout) itemLayout.findViewById(R.id.rlProduct);
        ImageView ivProduct = (ImageView) itemLayout.findViewById(R.id.ivProduct);
        TextView tvProductBrand = (TextView) itemLayout.findViewById(R.id.tvProductBrand);
        TextView tvProductName = (TextView) itemLayout.findViewById(R.id.tvProductName);
        TextView tvProductPriceFinal = (TextView) itemLayout.findViewById(R.id.tvProductPriceFinal);
        TextView tvProductSalePrice = (TextView) itemLayout.findViewById(R.id.tvProductSalePrice);
        TextView tvArrow = (TextView) itemLayout.findViewById(R.id.tvArrow);

        // 1 x 1
        llProduct.getLayoutParams().height = mScreenWidth/2;
        llProduct.requestLayout();

        // set values
        final Product product = getItem(idx);
        ImageUtil.display(ivProduct, product.getImagePath());

        tvProductBrand.setText(product.getBrandName());
        tvProductName.setText(product.getName());

        if(product.hasStock()) {
            //TODO sale price
            if(product.isSale()) {
                //tvProductSalePrice.setText(TextUtil.formatPriceWon(product.getSalePrice()));
                //tvProductSalePrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvProductSalePrice.setVisibility(View.VISIBLE);
                SpannableString content = new SpannableString(TextUtil.formatPriceWon(product.getPrice()));
                content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                tvProductSalePrice.setText(content);

                tvArrow.setVisibility(View.VISIBLE);

                tvProductPriceFinal.setText(TextUtil.formatPriceWon(product.getSalePrice()));
            } else {
                tvProductPriceFinal.setText(TextUtil.formatPriceWon(product.getPrice()));
            }
        } else {
            tvProductPriceFinal.setText("SOLD OUT");
        }
        return itemLayout;
    }
}
