package kr.wearit.android.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.model.Product;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.TextUtil;

/**
 * Created by KimJS on 2016-09-28.
 */
public class KeepPagerAdapter extends PagerAdapter {

    private String TAG = "BagPagerAdapter##";
    private LayoutInflater mInflater;
    private Product mProduct;

    private int mScreenWidth;

    public KeepPagerAdapter(Context mContext, Product product) {
        super();
        mInflater = LayoutInflater.from(mContext);
        mProduct = product;

        mScreenWidth = App.getInstance().getScreenWidth();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if(position == 0) {
            view = mInflater.inflate(R.layout.layout_left_delete, null);
        }
        else if(position == 1) {
            view = mInflater.inflate(R.layout.layout_listrow_keep, null);

            //set Bag List
            ImageView ivProduct = (ImageView)view.findViewById(R.id.ivProduct);
            TextView tvBrand = (TextView)view.findViewById(R.id.tvBrand);
            TextView tvProductName = (TextView)view.findViewById(R.id.tvProductName);
            TextView tvSalePrice = (TextView)view.findViewById(R.id.tvSalePrice);
            TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);

            //image
            ivProduct.getLayoutParams().height = mScreenWidth/2;
            ivProduct.getLayoutParams().width = mScreenWidth/3;
            ImageUtil.display(ivProduct, mProduct.getImagePath());

            tvBrand.setText(mProduct.getBrandName());
            tvProductName.setText(mProduct.getName());


            if(mProduct.isSale()) {
                SpannableString content = new SpannableString(TextUtil.formatPriceWon(mProduct.getPrice()));
                content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                tvSalePrice.setText(content);

                tvPrice.setText(TextUtil.formatPriceWon(mProduct.getSalePrice()));

                //오른쪽 정렬
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvPrice.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.tvSalePrice);
                tvPrice.setLayoutParams(params);
            } else {
                tvPrice.setText(TextUtil.formatPriceWon(mProduct.getPrice()));
            }
        }
        else {
            view = mInflater.inflate(R.layout.layout_right_add, null);
            TextView tvAddTo = (TextView)view.findViewById(R.id.tvAddTo);
            tvAddTo.setText("ITBAG\n담기");
        }
        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}