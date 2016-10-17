package kr.wearit.android.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Coupon;
import kr.wearit.android.model.Product;
import kr.wearit.android.util.TextUtil;
import kr.wearit.android.view.main.KeepFragment;

/**
 * Created by KimJS on 2016-10-04.
 */
public class CouponListAdapter extends ArrayAdapter<Coupon> {
    private String TAG = "BagListAdapter##";
    private Context mContext;
    private ArrayList<Coupon> mDataList;

    // View lookup cache
    public static class ViewHolder {
        private TextView tvCouponName;
        private TextView tvCouponValidation;
        private TextView tvCouponTerms;
        private TextView tvCouponSale;
        private int couponKey;

        public int getCouponKey() {
            return couponKey;
        }
    }


    public CouponListAdapter(Context context, ArrayList<Coupon> arrayList) {
        super(context, R.layout.listrow_coupon, arrayList);
        // TODO Auto-generated constructor stub
        mContext = context;
        mDataList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listrow_coupon, parent, false);

            viewHolder.tvCouponName = (TextView)view.findViewById(R.id.tvCouponName);
            viewHolder.tvCouponValidation = (TextView)view.findViewById(R.id.tvCouponValidation);
            viewHolder.tvCouponTerms = (TextView)view.findViewById(R.id.tvCouponTerms);
            viewHolder.tvCouponSale = (TextView)view.findViewById(R.id.tvCouponSale);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Coupon coupon = getItem(position);
        viewHolder.couponKey = coupon.getKey();
        String name = coupon.getName();

        String date1 = coupon.getDate1();
        String date2 = coupon.getDate2();
        date1 = date1.split(" ")[0];
        date2 = date2.split(" ")[0];
        String validation = "사용기간 : "+ date1 +" ~ "+ date2;

        int minCost = coupon.getMin_cost();
        String terms = minCost+"원 이상 구매시 사용가능";

        if(name != null) {
            viewHolder.tvCouponName.setText(name);
        }
        if(validation != null) {
            viewHolder.tvCouponValidation.setText(validation);
        }
        viewHolder.tvCouponTerms.setText(terms);

        int saleCost, saleRate;
        if(coupon.getSale_cost() != null) {
            saleCost = coupon.getSale_cost();
            viewHolder.tvCouponSale.setText(TextUtil.formatPriceWon(saleCost));
            viewHolder.tvCouponSale.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if(coupon.getSale_rate() != null) {
            saleRate = coupon.getSale_rate();
            viewHolder.tvCouponSale.setText(saleRate + "%");
            viewHolder.tvCouponSale.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }

        return view;
    }
}