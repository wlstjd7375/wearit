package kr.wearit.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.wearit.android.R;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.util.ImageUtil;

/**
 * Created by KimJS on 2016-09-27.
 */
public class BagListAdapter extends ArrayAdapter<ProductCart> {
    private String TAG = "BagListAdapter##";
    private Context mContext;
    private ArrayList<ProductCart> mDataList;
    private int mScreenWidth;

    // View lookup cache
    private static class ViewHolder {
        ImageView ivProduct;
        TextView tvBrand;
        TextView tvProductName;
        TextView tvSalePrice;
        TextView tvPrice;
        TextView tvSize;
        TextView tvItemCount;
        TextView tvModify;
    }


    public BagListAdapter(Context context, ArrayList<ProductCart> arrayList, int screenWidth) {
        super(context, R.layout.listrow_bag_list, arrayList);
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
            view = inflater.inflate(R.layout.listrow_bag_list, parent, false);

            viewHolder.ivProduct = (ImageView)view.findViewById(R.id.ivProduct);
            viewHolder.tvBrand = (TextView)view.findViewById(R.id.tvBrand);
            viewHolder.tvProductName = (TextView)view.findViewById(R.id.tvProductName);
            viewHolder.tvSalePrice = (TextView)view.findViewById(R.id.tvSalePrice);
            viewHolder.tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            viewHolder.tvSize = (TextView)view.findViewById(R.id.tvSize);
            viewHolder.tvItemCount = (TextView)view.findViewById(R.id.tvItemCount);
            viewHolder.tvModify = (TextView)view.findViewById(R.id.tvModify);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        ProductCart item = getItem(position);

        //image
        viewHolder.ivProduct.getLayoutParams().height = mScreenWidth/2;
        viewHolder.ivProduct.getLayoutParams().width = mScreenWidth/3;
        ImageUtil.display(viewHolder.ivProduct, item.getImagepath());

        viewHolder.tvBrand.setText(item.getBrandname());
        viewHolder.tvProductName.setText(item.getName());
        if(item.isSale()) {
            viewHolder.tvSalePrice.setText(item.getSale_price() + "원");
        }
        viewHolder.tvPrice.setText(item.getPrice() + "원");
        viewHolder.tvSize.setText("SIZE: " + item.getSize());
        viewHolder.tvItemCount.setText("개수: " + item.getCount());

        return view;
    }
}
