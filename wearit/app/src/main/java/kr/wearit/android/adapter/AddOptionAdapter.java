package kr.wearit.android.adapter;

import android.content.Context;
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
import java.util.HashSet;

import kr.wearit.android.R;
import kr.wearit.android.model.Product;
import kr.wearit.android.util.ImageUtil;

/**
 * Created by KimJS on 2016-10-10.
 */
public class AddOptionAdapter extends ArrayAdapter<Product> {
    private String TAG = "AddOptionAdapter##";
    private Context mContext;
    private ArrayList<Product> mDataList;
    private int mScreenWidth;

    private HashSet<Product> addProductList;

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

        //Right
        LinearLayout itemLayoutRight;
        RelativeLayout rlProductRight;
        ImageView ivProductRight;
        ImageView ivCheckRight;
        TextView tvProductBrandRight;
        TextView tvProductNameRight;
        TextView tvProductPriceFinalRight;

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
        addProductList = new HashSet<>();
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

            viewHolder.rlProductRight.getLayoutParams().height = mScreenWidth/2;
            viewHolder.rlProductRight.requestLayout();

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        int idx = position * 2;
        Log.d(TAG, "idx = " + idx);

        //Add Left
        final Product itemLeft = getItem(idx);
        ImageUtil.display(viewHolder.ivProductLeft, itemLeft.getImagePath());

        if(addProductList.contains(itemLeft)) {
            viewHolder.ivCheckLeft.setImageResource(R.drawable.check_green);
        } else {
            viewHolder.ivCheckLeft.setImageResource(R.drawable.check_gray);
        }

        viewHolder.tvProductBrandLeft.setText(itemLeft.getBrandName());
        viewHolder.tvProductNameLeft.setText(itemLeft.getName());
        viewHolder.tvProductPriceFinalLeft.setText(itemLeft.getPrice() + "");


        viewHolder.itemLayoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addProductList.contains(itemLeft)) {
                    addProductList.remove(itemLeft);
                    viewHolder.ivCheckLeft.setImageResource(R.drawable.check_gray);
                } else {
                    addProductList.add(itemLeft);
                    viewHolder.ivCheckLeft.setImageResource(R.drawable.check_green);
                }
            }
        });


        //Add Right
        int idxRight = idx + 1;
        if(idxRight < mDataList.size() ) {
            final Product itemRight = getItem(idxRight);
            viewHolder.itemLayoutRight.setVisibility(View.VISIBLE);

            ImageUtil.display(viewHolder.ivProductRight, itemRight.getImagePath());

            if(addProductList.contains(itemRight)) {
                viewHolder.ivCheckRight.setImageResource(R.drawable.check_green);
            } else {
                viewHolder.ivCheckRight.setImageResource(R.drawable.check_gray);
            }

            viewHolder.tvProductBrandRight.setText(itemRight.getBrandName());
            viewHolder.tvProductNameRight.setText(itemRight.getName());
            viewHolder.tvProductPriceFinalRight.setText(itemRight.getPrice() + "");

            viewHolder.itemLayoutRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(addProductList.contains(itemRight)) {
                        addProductList.remove(itemRight);
                        viewHolder.ivCheckRight.setImageResource(R.drawable.check_gray);
                    } else {
                        addProductList.add(itemRight);
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

    //TODO idx 대신 product key 넣어서 해시맵 만들기
    public HashSet<Product> getSelectedOptionItems() {
        return addProductList;
    }

}
