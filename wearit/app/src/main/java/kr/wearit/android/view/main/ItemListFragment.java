package kr.wearit.android.view.main;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import kr.wearit.android.R;
import kr.wearit.android.adapter.ProductListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.BrandApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Brand;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductCategory;
import kr.wearit.android.ui.ScrollListener;
import kr.wearit.android.view.MainActivity;

/**
 * Created by KimJS on 2016-09-23.
 */
public class ItemListFragment extends Fragment {
    private String TAG = "ItemListFramgent##";

    private Context mContext;

    private TextView tvToolbarTitle;
    private ListView lvItemList;

    private ProductListAdapter mAdapter;

    private RelativeLayout rlWating;

    private ExpandableListView lvCategory;
    private ListView lvBrand;

    private View view;

    private CategoryAdapter categoryAdapter;
    private BrandAdapter brandAdapter;

    private HashSet<Brand> selectBrand;
    private HashSet<ProductCategory> selectCategory;

    private int selectorFlag;
    //selectorFlag = 1 : Category 선택
    //selectorFlag = 2 : Brand 선택
    private int fetchFlag;
    //fetchFlag = 0 : 아무것도 선택 안함
    //fetchFlag = 1 : Category만 선택
    //fetchFlag = 2 : Brand만 선택
    //fetchFlag = 3 : Category, Brand 모두 선택

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

        lvCategory = (ExpandableListView) view.findViewById(R.id.lv_category_selector);
        lvBrand = (ListView) view.findViewById(R.id.lv_brand_selector);

        fetchFlag = 0;
        selectorFlag = 0;

//        ProductApi.getListOrder(1, "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
//            @Override
//            public void onSuccess(Pagination<Product> data) {
//                //Loading finish
//                rlWating.setVisibility(View.GONE);
//
//                mAdapter = new ProductListAdapter(mContext, data.getList(), getScreenWidth());
//                lvItemList.setAdapter(mAdapter);
//                //lvItemList.setOnScrollListener(mSelectorFetchHandler);
//            }
//        });
//        mAdapter = new ProductListAdapter(getActivity(), getScreenWidth());
//        lvItemList.setAdapter(mAdapter);
        lvItemList.setOnScrollListener(mFetchHandler);
        selectBrand = new HashSet<Brand>();
        selectCategory = new HashSet<ProductCategory>();

        mFetchHandler.fetch(1);


        setSelector();

        return view;
    }


    public void setSelector() {
        ((TextView) view.findViewById(R.id.tv_category)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removeBottomBar();
                selectorFlag = 1;
                lvCategory.setVisibility(View.VISIBLE);
                lvBrand.setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.tv_selector_title)).setText("CATEGORY");
                ((RelativeLayout) view.findViewById(R.id.rl_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((TextView) view.findViewById(R.id.tv_brand)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removeBottomBar();
                selectorFlag = 2;
                lvCategory.setVisibility(View.GONE);
                lvBrand.setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.tv_selector_title)).setText("BRAND");
                ((RelativeLayout) view.findViewById(R.id.rl_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((TextView) view.findViewById(R.id.tv_clear_selector)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectorFlag == 1) {
                    for (int i = 0; i < categoryAdapter.getGroupCount(); i++) {
                        ProductCategory groupItem = (ProductCategory) categoryAdapter.getGroup(i);
                        groupItem.setChecked(false);
                        for (int j = 0; j < categoryAdapter.getChildrenCount(i); j++) {
                            ProductCategory item = (ProductCategory) categoryAdapter.getChild(i, j);
                            item.setChecked(false);
                        }
                    }
                    selectCategory.clear();
                    categoryAdapter.notifyDataSetChanged();
                }
                if(selectorFlag == 2) {
                    selectBrand.clear();
                    brandAdapter.notifyDataSetChanged();
                }
            }
        });

        ((ImageButton) view.findViewById(R.id.bt_exit_selector)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RelativeLayout) view.findViewById(R.id.rl_selector)).setVisibility(View.GONE);
                selectorFlag = 0;
            }
        });

        ProductApi.getCategoGenderList(39, new Api.OnDefaultListener<ArrayList<ProductCategory>>() {

            @Override
            public void onSuccess(ArrayList<ProductCategory> data) {
                categoryAdapter = new CategoryAdapter();
                categoryAdapter.data.addAll(data);

                lvCategory.setAdapter(categoryAdapter);
            }
        });

        ((Button) view.findViewById(R.id.bt_complete_selector)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectorFlag == 1) {
                    for (int i = 0; i < categoryAdapter.getGroupCount(); i++) {
                        for (int j = 0; j < categoryAdapter.getChildrenCount(i); j++) {
                            ProductCategory item = (ProductCategory) categoryAdapter.getChild(i, j);
                            if (item.isChecked()) {
                                selectCategory.add(item);
                            }
                            else {
                                if(selectCategory.contains(item)){
                                    selectCategory.remove(item);
                                }
                            }
                        }
                    }
                }
                ((RelativeLayout) view.findViewById(R.id.rl_selector)).setVisibility(View.GONE);
                rlWating.setVisibility(View.VISIBLE);
                System.out.println("mFetch핸들러 호출직전!");
                mFetchHandler.fetch(1);
            }
        });

        BrandApi.getPage(1, new Api.OnDefaultListener<Pagination<Brand>>(){
            @Override
            public void onSuccess(Pagination<Brand> data) {
                brandAdapter = new BrandAdapter(getActivity(),R.layout.listrow_product_selector_child,data.getList());
                lvBrand.setAdapter(brandAdapter);
                lvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        if(selectBrand.contains(brandAdapter.getItem(position))){
                            ((TextView) view.findViewById(R.id.tv_name)).setTextColor(Color.parseColor("#000000"));
                            selectBrand.remove(brandAdapter.getItem(position));
                        }
                        else {
                            ((TextView) view.findViewById(R.id.tv_name)).setTextColor(Color.parseColor("#009688"));
                            selectBrand.add(brandAdapter.getItem(position));
                        }
                    }
                });
                lvBrand.setOnScrollListener(mSelectorFetchHandler);
            }
        });
    }

    private void getProductList(final int page) {
        System.out.println("GetProductList Call!!");
        if(page == 1) {
            rlWating.setVisibility(View.VISIBLE);
        }
        ProductCategory[] categoriArray = selectCategory.toArray(new ProductCategory[selectCategory.size()]);
        Brand[] brandArray = selectBrand.toArray(new Brand[selectBrand.size()]);

        if(selectCategory.size() != 0) {
            if(selectBrand.size() != 0) {
                fetchFlag = 3;
                //카테고리 브랜드 모두 선택
                ProductApi.getListByCategoryAndBrand(categoriArray[0].getKey(),brandArray[0].getKey(),page,new Api.OnAuthDefaultListener<Pagination<Product>>(){

                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        if(page == 1) {
                            mAdapter.clear();
                        }
                        mAdapter.addAll(data.getList());
                        rlWating.setVisibility(View.GONE);
                        selectorFlag = 0;
                        mFetchHandler.onFetched(data);
                    }
                });
            }
            else {
                //카테고리만 선택
                fetchFlag = 1;
                ProductApi.getListByCategory(categoriArray[0].getKey(), page, new Api.OnAuthDefaultListener<Pagination<Product>>(){

                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        if(page == 1) {
                            mAdapter.clear();
                        }
                        mAdapter.addAll(data.getList());
                        rlWating.setVisibility(View.GONE);
                        selectorFlag = 0;
                        mFetchHandler.onFetched(data);
                    }
                });
            }
        }
        else {
            if(selectBrand.size() != 0) {
                //브랜드만 선택
                fetchFlag = 2;
                ProductApi.getListByBrand(brandArray[0].getKey(),"neworder", page, new Api.OnAuthDefaultListener<Pagination<Product>>(){

                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        if(page == 1) {
                            mAdapter.clear();
                        }

                        mAdapter.addAll(data.getList());
                        rlWating.setVisibility(View.GONE);
                        selectorFlag = 0;
                        mFetchHandler.onFetched(data);
                    }
                });
            }
            else {
                //아무것도 선택안함
                fetchFlag = 0;
                ProductApi.getListOrder(page , "neworder", new Api.OnDefaultListener<Pagination<Product>>() {
                    @Override
                    public void onSuccess(Pagination<Product> data) {
                        if(mAdapter == null) {
                            mAdapter = new ProductListAdapter(mContext, data.getList(), getScreenWidth());
                            lvItemList.setAdapter(mAdapter);
                            rlWating.setVisibility(View.GONE);
                        }
                        else {
                            if (page == 1) {
                                mAdapter.clear();
                            }
                            mAdapter.addAll(data.getList());
                            rlWating.setVisibility(View.GONE);
                            selectorFlag = 0;
                        }
                        mFetchHandler.onFetched(data);
                    }
                });
            }
        }
    }

    private ScrollListener mFetchHandler = new ScrollListener() {

        @Override
        public void onFetch(final ScrollListener listener, final int page) {
            //TODO Progressbar
            Log.d(TAG, "onFetch page = " + page);
            getProductList(page);
        }
    };


    private ScrollListener mSelectorFetchHandler = new ScrollListener() {

        @Override
        public void onFetch(final ScrollListener listener, final int page) {
            //TODO Progressbar
            Log.d(TAG, "page = " + page);
            BrandApi.getPage(page, new Api.OnDefaultListener<Pagination<Brand>>() {
                @Override
                public void onSuccess(Pagination<Brand> data) {
                    if (page == 1) {
                        brandAdapter.clear();
                    }
                    brandAdapter.addAll(data.getList());
                    listener.onFetched(data);
                }
            });
        }
    };

    private class BrandAdapter extends ArrayAdapter<Brand> {
        ArrayList<Brand> data;

        public BrandAdapter(Context context, int resource, ArrayList<Brand> data) {
            super(context, resource, data);
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_selector_child, parent, false);
            }

            Brand item = getItem(position);
            if (item != null) {
                TextView tvName = (TextView) v.findViewById(R.id.tv_name);
                tvName.setTextColor(Color.parseColor("#000000"));
                tvName.setText(item.getName());
            }
            return v;
        }
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
        public View getGroupView(final int groupPosition, boolean isExpanded, final View convertView, final ViewGroup parent) {

            View view = convertView;

            if (view == null)
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_selctor_group, parent, false);

            TextView text = (TextView) view.findViewById(R.id.tv_name);
            final ProductCategory item = (ProductCategory)getGroup(groupPosition);
            text.setText(item.getName());

            if(item.isChecked()) {
                ((TextView) view.findViewById(R.id.tv_all_sel)).setTextColor(Color.parseColor("#009688"));
            }
            else {
                ((TextView) view.findViewById(R.id.tv_all_sel)).setTextColor(Color.parseColor("#bababa"));
            }

            ((TextView) view.findViewById(R.id.tv_all_sel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.isChecked()){
                        for(int i=0;i<item.getChildren().size();i++) {
                            item.getChildren().get(i).setChecked(false);
                        }
                        item.setChecked(false);
                    }
                    else {
                        for(int i=0;i<item.getChildren().size();i++) {
                            item.getChildren().get(i).setChecked(true);
                        }
                        item.setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });

            return view;
        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null)
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_selector_child, parent, false);

            final TextView name = (TextView) view.findViewById(R.id.tv_name);
            final ProductCategory item = (ProductCategory) getChild(groupPosition, childPosition);
            name.setText(item.getName());

            if(item.isChecked()) {
                name.setTextColor(Color.parseColor("#009688"));
            }
            else {
                name.setTextColor(Color.parseColor("#bababa"));
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductCategory parent = (ProductCategory)getGroup(groupPosition);
                    if(item.isChecked()) {
                        name.setTextColor(Color.parseColor("#bababa"));
                        item.setChecked(false);
                        if(parent.isChecked()) {
                            parent.setChecked(false);
                            notifyDataSetChanged();
                        }
                    }
                    else {
                        name.setTextColor(Color.parseColor("#009688"));
                        item.setChecked(true);
                        boolean flag = true;
                        for(int i=0;i<parent.getChildren().size();i++) {
                            if(!parent.getChildren().get(i).isChecked()) {
                                flag = false;
                                break;
                            }
                        }
                        if(flag) {
                            parent.setChecked(true);
                            notifyDataSetChanged();
                        }
                    }
                }
            });

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
