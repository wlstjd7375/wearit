package kr.wearit.android.adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.TextUtil;
import kr.wearit.android.view.main.BagFragment;

/**
 * Created by KimJS on 2016-09-27.
 */
public class BagPagerAdapter extends PagerAdapter {

    private String TAG = "BagPagerAdapter##";
    private LayoutInflater mInflater;
    private ProductCart mProductCart;

    private int mScreenWidth;

    public BagPagerAdapter(Context mContext, ProductCart productCart) {
        super();
        mInflater = LayoutInflater.from(mContext);
        mProductCart = productCart;

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
            view = mInflater.inflate(R.layout.layout_listrow_bag, null);

            //set Bag List
            ImageView ivProduct = (ImageView)view.findViewById(R.id.ivProduct);
            TextView tvBrand = (TextView)view.findViewById(R.id.tvBrand);
            TextView tvProductName = (TextView)view.findViewById(R.id.tvProductName);
            TextView tvSalePrice = (TextView)view.findViewById(R.id.tvSalePrice);
            TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            TextView tvSize = (TextView)view.findViewById(R.id.tvSize);
            TextView tvItemCount = (TextView)view.findViewById(R.id.tvItemCount);
            TextView tvModify = (TextView)view.findViewById(R.id.tvModify);

            //image
            ivProduct.getLayoutParams().height = mScreenWidth/2;
            ivProduct.getLayoutParams().width = mScreenWidth/3;
            ImageUtil.display(ivProduct, mProductCart.getImagepath());

            tvBrand.setText(mProductCart.getBrandname());
            tvProductName.setText(mProductCart.getName());

            if(!mProductCart.isStock()) {
                if(mProductCart.isSale()) {
                    SpannableString content = new SpannableString(TextUtil.formatPriceWon(mProductCart.getPrice()));
                    content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                    tvSalePrice.setText(content);
                    tvPrice.setText(TextUtil.formatPriceWon(mProductCart.getSale_price()));
                    //오른쪽 정렬
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvPrice.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.tvSalePrice);
                    tvPrice.setLayoutParams(params);
                }
                else {
                    tvPrice.setText(TextUtil.formatPriceWon(mProductCart.getPrice()));
                }
            } else {
                //재고 없으면
                tvPrice.setText("OUT OF STOCK");
                tvPrice.setTextColor(Color.parseColor("#e33131"));
            }

            tvSize.setText("SIZE: " + mProductCart.getSize());
            tvItemCount.setText("개수: " + mProductCart.getCount());
        }
        else {
            view = mInflater.inflate(R.layout.layout_right_add, null);
            TextView tvAddTo = (TextView)view.findViewById(R.id.tvAddTo);
            tvAddTo.setText("KEEP\n담기");
        }
        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        //super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
