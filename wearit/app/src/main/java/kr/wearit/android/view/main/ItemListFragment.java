package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.R;
import kr.wearit.android.adapter.ProductListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductCategory;
import kr.wearit.android.view.MainActivity;

/**
 * Created by KimJS on 2016-09-23.
 */
public class ItemListFragment extends Fragment {

    private Context mContext;

    private TextView tvToolbarTitle;
    private ListView lvItemList;

    private ProductListAdapter mAdapter;

    private RelativeLayout rlWating;

    private ExpandableListView exSelector;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mContext = getActivity();

        //Loading
        rlWating = (RelativeLayout) view.findViewById(R.id.rl_waiting);
        rlWating.setVisibility(View.VISIBLE);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("ITEM");
        lvItemList = (ListView)view.findViewById(R.id.lvItemList);

        exSelector = (ExpandableListView) view.findViewById(R.id.lv_selector);

        ProductApi.getListOrder(1, "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
            @Override
            public void onSuccess(Pagination<Product> data) {
                //Loading finish
                rlWating.setVisibility(View.GONE);

                mAdapter = new ProductListAdapter(mContext, data.getList(), getScreenWidth());
                lvItemList.setAdapter(mAdapter);
                //lvItemList.setOnScrollListener(mFetchHandler);
            }
        });

        ((TextView) view.findViewById(R.id.tv_category)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removeBottomBar();
                ((RelativeLayout) view.findViewById(R.id.rl_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((TextView) view.findViewById(R.id.tv_brand)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removeBottomBar();
                ((RelativeLayout) view.findViewById(R.id.rl_selector)).setVisibility(View.VISIBLE);
            }
        });

        ProductApi.getCategoGenderList(39, new Api.OnDefaultListener<ArrayList<ProductCategory>>() {

            @Override
            public void onSuccess(ArrayList<ProductCategory> data) {
                final CategoryAdapter adapter = new CategoryAdapter();
                adapter.data.addAll(data);

                exSelector.setAdapter(adapter);
            }
        });
        return view;
    }

    private class CategoryAdapter extends BaseExpandableListAdapter {

        private List<ProductCategory> data;

        //

        public CategoryAdapter() {
            data = new ArrayList<ProductCategory>();
        }

        //

        @Override
        public int getGroupCount() {
            return data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            List<ProductCategory> list = data.get(groupPosition).getChildren();

            return list != null ? data.get(groupPosition).getChildren().size() : 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return data.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            if(groupPosition == 0){
//                View view = convertView;
//
//                if (view == null)
//                    view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_selctor_group, parent, false);
//
//                TextView text = (TextView) view.findViewById(R.id.tv_name);
//
//                text.setText(((ProductCategory) getGroup(groupPosition)).getName());
//
//                return view;
//            }

            View view = convertView;

            if (view == null)
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_selctor_group, parent, false);

            TextView text = (TextView) view.findViewById(R.id.tv_name);
            text.setText(((ProductCategory) getGroup(groupPosition)).getName());

            ((TextView) view.findViewById(R.id.tv_all_sel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TextView) view.findViewById(R.id.tv_all_sel)).setTextColor(Color.parseColor("#009688"));
                }
            });

            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null)
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_selector_child, parent, false);

            TextView name = (TextView) view.findViewById(R.id.tv_name);
            name.setText(((ProductCategory) getChild(groupPosition, childPosition)).getName());

            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
