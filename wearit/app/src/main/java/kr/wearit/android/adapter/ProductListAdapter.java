package kr.wearit.android.adapter;


import android.content.Context;
import android.content.Intent;
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

        int idx = position * 2;
        Log.d(TAG, "idx = " + idx + ", size = " + mDataList.size());
        viewHolder.llProductLeft.removeAllViews();
        viewHolder.llProductLeft.addView(getProductLayout(idx));

        if((idx+1) < mDataList.size() ) {
            viewHolder.llProductRight.removeAllViews();
            viewHolder.llProductRight.addView(getProductLayout(idx+1));
        }
        else {
            viewHolder.llProductRight.removeAllViews();
        }

        return view;
    }

    @Override
    public int getCount() {
        int size = mDataList.size();
        Log.d(TAG, "size in getCount = " + size);
        return (size / 2) + (size % 2);
    }

    private View getProductLayout(int idx) {
        //Layout inflate
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemLayout = inflater.inflate(R.layout.layout_item_big, null);
        RelativeLayout llProduct = (RelativeLayout) itemLayout.findViewById(R.id.rlProduct);
        ImageView ivProduct = (ImageView) itemLayout.findViewById(R.id.ivProduct);
        TextView tvProductBrand = (TextView) itemLayout.findViewById(R.id.tvProductBrand);
        TextView tvProductName = (TextView) itemLayout.findViewById(R.id.tvProductName);
        TextView tvProductPriceFinal = (TextView) itemLayout.findViewById(R.id.tvProductPriceFinal);

        // 1 x 1
        llProduct.getLayoutParams().height = mScreenWidth/2;
        llProduct.requestLayout();

        // set values
        final Product productLeft = getItem(idx);
        ImageUtil.display(ivProduct, productLeft.getImagePath());

        tvProductBrand.setText(productLeft.getBrandName());
        tvProductName.setText(productLeft.getName());
        tvProductPriceFinal.setText(productLeft.getPrice() + "");

        //TODO sale price
        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProductActivity.class);
                intent.putExtra("key",productLeft.getKey());
                getContext().startActivity(intent);
            }
        });
        return itemLayout;
    }
}
