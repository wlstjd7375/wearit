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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.model.Order;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.OrderStatusUtil;
import kr.wearit.android.util.Util;

/**
 * Created by KimJS on 2016-10-09.
 */
public class OrderListAdapter extends ArrayAdapter<Order> {
    private String TAG = "BagListAdapter##";
    private Context mContext;
    private ArrayList<Order> mDataList;
    private int mScreenWidth;
    private int mHeight;

    // View lookup cache
    private static class ViewHolder {
        LinearLayout llImageBox;
        ImageView ivProduct;
        TextView tvOrderDate;
        TextView tvBrand;
        TextView tvProductName;
        TextView tvPrice;
        TextView tvOrderStatus;
    }


    public OrderListAdapter(Context context, ArrayList<Order> arrayList) {
        super(context, R.layout.listrow_order_list, arrayList);
        // TODO Auto-generated constructor stub
        mContext = context;
        mDataList = arrayList;
        mScreenWidth = App.getInstance().getScreenWidth();
        mHeight = mScreenWidth/2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listrow_order_list, parent, false);

            //inflate
            viewHolder.llImageBox = (LinearLayout)view.findViewById(R.id.llImageBox);

            viewHolder.ivProduct = (ImageView)view.findViewById(R.id.ivProduct);
            viewHolder.tvOrderDate = (TextView)view.findViewById(R.id.tvOrderDate);
            viewHolder.tvBrand = (TextView)view.findViewById(R.id.tvBrand);
            viewHolder.tvProductName = (TextView)view.findViewById(R.id.tvProductName);
            viewHolder.tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            viewHolder.tvOrderStatus = (TextView)view.findViewById(R.id.tvOrderStatus);

            //set image layout size
            //viewHolder.llImageBox.getLayoutParams().height = mHeight;
            viewHolder.llImageBox.getLayoutParams().width = mHeight;

            //image size
            viewHolder.ivProduct.getLayoutParams().height = mHeight;
            viewHolder.ivProduct.getLayoutParams().width = mScreenWidth/3;

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Order item = getItem(position);


        ImageUtil.display(viewHolder.ivProduct, item.getImagePath());

        //date
        /*
        if(item.getProductCount() > 1 ){
            String date = new SimpleDateFormat("yyyy.M.d").format(item.getDate());
            date += " 외 " + String.valueOf(item.getProductCount()-1) + "건";
            viewHolder.tvOrderDate.setText(date);
        } else{
            String date = new SimpleDateFormat("yyyy.M.d").format(item.getDate());
            viewHolder.tvOrderDate.setText(date);
        }*/
        String date = new SimpleDateFormat("yyyy.M.d").format(item.getDate());
        viewHolder.tvOrderDate.setText(date);

        //brand, name TODO 브랜드 이름
        viewHolder.tvBrand.setText(item.getBrand());

        //외 몇건
        if(item.getProductCount() > 1 ){
            String name = item.getName();
            name += " 외 " + String.valueOf(item.getProductCount()-1) + "개";
            viewHolder.tvProductName.setText(name);
        } else{
            String name = item.getName();
            viewHolder.tvProductName.setText(name);
        }
        //viewHolder.tvProductName.setText(item.getName());

        viewHolder.tvPrice.setText(Util.formatWon(item.getPrice()) + "원");
        viewHolder.tvOrderStatus.setText(OrderStatusUtil.getStatus(item.getStatus()));


        return view;
    }
}