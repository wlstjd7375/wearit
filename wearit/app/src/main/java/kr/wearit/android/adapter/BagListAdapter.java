package kr.wearit.android.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.CartApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.view.main.BagFragment;


/**
 * Created by KimJS on 2016-09-27.
 */
public class BagListAdapter extends ArrayAdapter<ProductCart> {
    private String TAG = "BagListAdapter##";
    private Context mContext;
    private ArrayList<ProductCart> mDataList;
    private int mScreenWidth;
    private Fragment mParentFragment;

    // View lookup cache
    private static class ViewHolder {
        ViewPager pager;
    }


    public BagListAdapter(Context context, ArrayList<ProductCart> arrayList, Fragment parentFragment) {
        super(context, R.layout.listrow_pager_list, arrayList);
        // TODO Auto-generated constructor stub
        mContext = context;
        mDataList = arrayList;
        mParentFragment = parentFragment;
        mScreenWidth = App.getInstance().getScreenWidth();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listrow_pager_list, parent, false);

            viewHolder.pager = (ViewPager) view.findViewById(R.id.viewPager);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final ProductCart item = getItem(position);
        BagPagerAdapter mAdapter = new BagPagerAdapter(mContext, item);
        viewHolder.pager.setAdapter(mAdapter);
        viewHolder.pager.getLayoutParams().height = mScreenWidth/2;
        viewHolder.pager.setCurrentItem(1);
        viewHolder.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Drag 하는동안 호출
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Called when the scroll state changes.
            }

            @Override
            public void onPageSelected(int position) {
                //This method will be invoked when a new page becomes selected.
                if(position == 0) {
                    //TODO Api Call
                    //Delete from Bag
                    removeFromBag(item);
                    //((BagFragment)mParentFragment).deleteRow(item);
                }
                else if(position == 2) {
                    //TODO Api Call
                    //Add to keep, Remove from Bag
                    removeFromBag(item);
                    addToKeep(item);

                    //((BagFragment)mParentFragment).deleteRow(item);
                }

            }
        });

        return view;
    }

    private void addToKeep(ProductCart itemCart) {
        ProductApi.get(itemCart.getProduct(), new Api.OnWaitListener<Product>() {
            @Override
            public void onSuccess(Product data) {
                ProductApi.addFavorite(data, new Api.OnAuthListener() {

                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(Object data) {
                        Toast.makeText(mContext, "KEEP에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFail() {
                    }
                });
            }
        });
    }

    private void removeFromBag(final ProductCart itemCart) {
        CartApi.remove(itemCart, new Api.OnWaitListener<Void>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Void data) {
                //remove(itemCart);
                ((BagFragment)mParentFragment).deleteRow(itemCart);
                //Toast.makeText(mContext, "삭제되었습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail() {
            }
        });
    }

}
