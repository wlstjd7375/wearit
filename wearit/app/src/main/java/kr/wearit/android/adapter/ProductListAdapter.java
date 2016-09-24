package kr.wearit.android.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.ArrayList;

import kr.wearit.android.R;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsPair;
import kr.wearit.android.model.Product;
import kr.wearit.android.util.ImageUtil;

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
        DynamicHeightImageView ivNewstLeft;
        DynamicHeightImageView ivNewsRight;
        TextView tvTitleLeft;
        TextView tvTitleRight;

        LinearLayout llProductLeft;
        LinearLayout llProductRight;
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


            //fix height
            view.getLayoutParams().height = mScreenWidth/2;
            view.requestLayout();

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }

    @Override
    public int getCount() {
        int size = mDataList.size();
        return (size / 2) + (size % 2);
    }
}
