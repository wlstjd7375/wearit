package kr.wearit.android.view.search;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import kr.wearit.android.R;
import kr.wearit.android.adapter.ProductListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.BrandApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.controller.SearchApi;
import kr.wearit.android.database.DBManager;
import kr.wearit.android.model.Brand;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductCategory;
import kr.wearit.android.model.Search;
import kr.wearit.android.model.SearchWord;
import kr.wearit.android.ui.ScrollListener;
import kr.wearit.android.view.BaseActivity;
import kr.wearit.android.view.MainActivity;
import kr.wearit.android.view.main.ItemListFragment;

public class SearchResultActivity extends BaseActivity {
    private String TAG = "SearchResultActivity##";

    private String query;
    private RelativeLayout rlWaiting;

    private ListView lvItemList;

    private ProductListAdapter mAdapter;

    private ExpandableListView lvCategory;
    private ListView lvBrand;

    private View toolbar;

    private CategoryAdapter categoryAdapter;
    private BrandAdapter brandAdapter;

    private HashSet<Brand> selectBrand;
    private HashSet<ProductCategory> selectCategory;
    private ArrayList<Product> initProductList;
    private ArrayList<Brand> brandList;
    private ArrayList<ProductCategory> categoryList;

    private int selectorFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        query = getIntent().getStringExtra("word");

        setContentView(R.layout.activity_search_result);


        rlWaiting = (RelativeLayout) findViewById(R.id.rl_waiting);

        if(query != null && query.length() != 0) {
            ((TextView) findViewById(R.id.tvToolbarTitle)).setText(query);
            rlWaiting.setVisibility(View.VISIBLE);
            SearchWord word = new SearchWord();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            String time = dateFormat.format(new Date(System.currentTimeMillis()));
            String insert = "";
            try {
                insert = URLEncoder.encode(query, "utf-8");
                Log.d("dd", "encode : "+insert);

            } catch (Exception e) {
                e.printStackTrace();
            }
            word.setSearchWord(insert);
            word.setDate(time);
            System.out.println("SearchWord : " + word.getSearchWord());
            DBManager.getManager(getActivity().getApplicationContext()).insertSearchWord(word);

            SearchApi.getProduct(query,1, new Api.OnDefaultListener<Pagination<Product>>() {
                @Override
                public void onSuccess(Pagination<Product> data) {
                    rlWaiting.setVisibility(View.GONE);
                    initProductList = data.getList();
//                    brandList = data.getBrand();
                    initialize();
                }
            });
        }
    }

    public void initialize() {
        selectorFlag = 0;

        lvItemList = (ListView)findViewById(R.id.lvItemList);

        lvCategory = (ExpandableListView) findViewById(R.id.lv_category_selector);
        lvBrand = (ListView) findViewById(R.id.lv_brand_selector);


        selectBrand = new HashSet<Brand>();
        selectCategory = new HashSet<ProductCategory>();

        mAdapter = new ProductListAdapter(getActivity(), initProductList, getScreenWidth());
        lvItemList.setAdapter(mAdapter);
        rlWaiting.setVisibility(View.GONE);

        setSelector();
    }
    public void setSelector() {
        ((TextView) findViewById(R.id.tv_category)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removeBottomBar();
                selectorFlag = 1;
                lvCategory.setVisibility(View.VISIBLE);
                lvBrand.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.tv_selector_title)).setText("CATEGORY");
                ((RelativeLayout) findViewById(R.id.rl_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((TextView) findViewById(R.id.tv_brand)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removeBottomBar();
                selectorFlag = 2;
                lvCategory.setVisibility(View.GONE);
                lvBrand.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.tv_selector_title)).setText("BRAND");
                ((RelativeLayout) findViewById(R.id.rl_selector)).setVisibility(View.VISIBLE);
            }
        });

        ((TextView) findViewById(R.id.tv_clear_selector)).setOnClickListener(new View.OnClickListener() {
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

        ((ImageButton) findViewById(R.id.bt_exit_selector)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RelativeLayout) findViewById(R.id.rl_selector)).setVisibility(View.GONE);
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

        ((Button) findViewById(R.id.bt_complete_selector)).setOnClickListener(new View.OnClickListener() {
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
                ((RelativeLayout) findViewById(R.id.rl_selector)).setVisibility(View.GONE);
                rlWaiting.setVisibility(View.VISIBLE);
                modifyProductList();
            }
        });

//        brandAdapter = new BrandAdapter(getActivity(),R.layout.listrow_product_selector_child, brandList);
//        lvBrand.setAdapter(brandAdapter);
//        lvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                if(selectBrand.contains(brandAdapter.getItem(position))){
//                    ((TextView) view.findViewById(R.id.tv_name)).setTextColor(Color.parseColor("#000000"));
//                    selectBrand.remove(brandAdapter.getItem(position));
//                }
//                else {
//                    ((TextView) view.findViewById(R.id.tv_name)).setTextColor(Color.parseColor("#009688"));
//                    selectBrand.add(brandAdapter.getItem(position));
//                }
//            }
//        });
    }

    private void modifyProductList() {
        System.out.println("modifyProductList Call!!");

        ProductCategory[] categoriArray = selectCategory.toArray(new ProductCategory[selectCategory.size()]);
        Brand[] brandArray = selectBrand.toArray(new Brand[selectBrand.size()]);
        ArrayList<Product> changeList = new ArrayList<Product>();

        if(selectCategory.size() != 0) {
            if(selectBrand.size() != 0) {
                //카테고리 브랜드 모두 선택
                for(int i=0;i<initProductList.size();i++) {
                    Product product = initProductList.get(i);
                    for(int l=0; l<brandArray.length;l++) {
                        if (brandArray[l].getName().equals(product.getBrandName())) {
                            //선택된 브랜드와 아이템 브랜드가 같음
                            if(product.getCategoryArray() != null) {
                                for (int j = 0; j < product.getCategoryArray().length; j++) {
                                    for (int k = 0; k < categoriArray.length; k++) {
                                        if (categoriArray[k].getKey() == Integer.valueOf(product.getCategoryArray()[j])) {
                                            //선택된 카테고리의 키와 아이템의 카테고리 키가 같음
                                            changeList.add(product);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                mAdapter.clear();
                mAdapter.addAll(changeList);
                mAdapter.notifyDataSetChanged();
                System.out.println("브랜드, 카테고리 선택 Size : " + changeList.size());
            }
            else {
                //카테고리만 선택
                for(int i=0;i<initProductList.size();i++) {
                    Product product = initProductList.get(i);
                    if(product.getCategoryArray() != null) {
                        for (int j = 0; j < product.getCategoryArray().length; j++) {
                            for (int k = 0; k < categoriArray.length; k++) {
                                if (categoriArray[k].getKey() == Integer.valueOf(product.getCategoryArray()[j])) {
                                    //선택된 카테고리의 키와 아이템의 카테고리 키가 같음
                                    changeList.add(product);
                                }
                            }
                        }
                    }
                }
                mAdapter.clear();
                mAdapter.addAll(changeList);
                mAdapter.notifyDataSetChanged();
                System.out.println("카테고리만 선택 Size : " + changeList.size());
            }
        }
        else {
            if(selectBrand.size() != 0) {
                //브랜드만 선택
                for(int i=0;i<initProductList.size();i++) {
                    Product product = initProductList.get(i);
                    for(int l=0; l<brandArray.length;l++) {
                        if (brandArray[l].getName().equals(product.getBrandName())) {
                            //선택된 브랜드와 아이템 브랜드가 같음
                            changeList.add(product);
                        }
                    }
                }
                mAdapter.clear();
                mAdapter.addAll(changeList);
                mAdapter.notifyDataSetChanged();
                System.out.println("브랜드만 선택 Size : " + changeList.size());
            }
            else {
                //아무것도 선택안함
                mAdapter.clear();
                mAdapter.addAll(initProductList);
                mAdapter.notifyDataSetChanged();
                System.out.println("아무것도 선택 안함 Size : " + changeList.size());
            }
        }
        rlWaiting.setVisibility(View.GONE);
    }

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
