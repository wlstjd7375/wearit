package kr.wearit.android.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.R;
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
        super(context, R.layout.listrow_bag_list, arrayList);
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
            view = inflater.inflate(R.layout.listrow_bag_list, parent, false);

            viewHolder.pager = (ViewPager) view.findViewById(R.id.viewPager);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final ProductCart item = getItem(position);
        ListRowPagerAdapter mAdapter = new ListRowPagerAdapter(mContext, item);
        viewHolder.pager.setAdapter(mAdapter);
        viewHolder.pager.getLayoutParams().height = mScreenWidth/2;
        viewHolder.pager.setCurrentItem(1);
        viewHolder.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Drag 하는동안 호출
                /*
                Log.d(TAG, "position = " + position + ", positionOffset = " + positionOffset + ", positionOffsetPixels = " + positionOffsetPixels);
                if(position == 0 && positionOffsetPixels < 10) {
                    //TODO Api Call
                    ((BagFragment)mParentFragment).deleteRow(item);
                }
                else if(position == 2 && positionOffsetPixels < 10) {
                    //TODO Api Call
                    ((BagFragment)mParentFragment).deleteRow(item);
                }*/
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
                    ((BagFragment)mParentFragment).deleteRow(item);
                }
                else if(position == 2) {
                    //TODO Api Call
                    ((BagFragment)mParentFragment).deleteRow(item);
                }

            }
        });

        return view;
    }
}
