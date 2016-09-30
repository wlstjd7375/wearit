package kr.wearit.android.view.main;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.wearit.android.App;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.adapter.BagListAdapter;
import kr.wearit.android.adapter.KeepListAdapter;
import kr.wearit.android.adapter.KeepPagerAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.CartApi;
import kr.wearit.android.controller.MeApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.model.ProductSize;
import kr.wearit.android.view.MainActivity;
import kr.wearit.android.view.product.ProductActivity;

/**
 * Created by KimJS on 2016-09-26.
 */
public class KeepFragment extends Fragment {

    private final String TAG = "KeepFragment##";
    private TextView tvToolbarTitle;

    private View view;

    //Go Shopping
    private RelativeLayout rlGoShopping;
    private TextView tvNoItem;
    private Button btGoShopping;

    //Keep List
    private ListView lvKeepList;
    private KeepListAdapter mAdapter;
    private ArrayList<Product> mProductList;
    //private final String BAG_URL = "/me/product";

    private LinearLayout llSelector;
    private TextView tvSelSize;

    private int selSize;
    private int count;

    private Product mItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_keep, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("KEEP");

        rlGoShopping = (RelativeLayout)view.findViewById(R.id.rlGoShopping);
        lvKeepList = (ListView)view.findViewById(R.id.lvKeepList);

        llSelector = (LinearLayout) view.findViewById(R.id.ll_selector);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(App.getInstance().isLogin()) {
            ProductApi.getList("/me/product", new Api.OnDefaultListener<ArrayList<Product>>() {
                @Override
                public void onSuccess(ArrayList<Product> data) {
                    mProductList = data;
                    setListView();
                }
            });
        }
    }

    public void initialize_selector(){
        tvSelSize = (TextView) view.findViewById(R.id.tv_sel_size);
        ((RelativeLayout) view.findViewById(R.id.rl_size_sel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OptionDialog(getActivity()).show();
            }
        });

        ((RelativeLayout) view.findViewById(R.id.rl_plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount = Integer.valueOf(((TextView) view.findViewById(R.id.tv_count)).getText().toString());
                ((TextView) view.findViewById(R.id.tv_count)).setText(String.valueOf(currentCount+1));
                count = currentCount + 1;
            }
        });

        ((RelativeLayout) view.findViewById(R.id.rl_minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 1) {
                    int currentCount = Integer.valueOf(((TextView) view.findViewById(R.id.tv_count)).getText().toString());
                    ((TextView) view.findViewById(R.id.tv_count)).setText(String.valueOf(currentCount - 1));
                    count = currentCount - 1;
                }
            }
        });

        ((ImageButton) view.findViewById(R.id.bt_insel_itbag)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.valueOf(((TextView) view.findViewById(R.id.tv_count)).getText().toString());
                System.out.println("Sel Size : " + selSize + " Count : " + count);
                CartApi.add(selSize, count, new Api.OnAuthListener<Integer>() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(Integer data) {
                        Toast.makeText(getActivity(), "변경되었습니다", Toast.LENGTH_SHORT).show();
                        mAdapter.removeFromKeep(mItem);
                        llSelector.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFail() {
                        System.out.println("실패 ㅅㅂ");
                    }
                });
            }
        });
    }

    private void setListView() {
        if(mProductList.size() == 0) {
            setNoItemLayout();
        }
        else {
            setBagListLayout();
        }
    }


    private void setNoItemLayout() {
        //if there is no item
        //set layout_go_shopping
        lvKeepList.setVisibility(View.INVISIBLE);
        rlGoShopping.setVisibility(View.VISIBLE);

        tvNoItem = (TextView) view.findViewById(R.id.tvNoItem);
        tvNoItem.setText("현재 KEEP한 상품이 없습니다.\n마음에 드는 상품을 KEEP하세요.");

        btGoShopping = (Button) view.findViewById(R.id.btGoShopping);
        btGoShopping.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(Const.FRAGMENT_ITEMLIST);
            }
        });
    }

    private void setBagListLayout() {
        //No Item Layout invisible
        rlGoShopping.setVisibility(View.INVISIBLE);
        lvKeepList.setVisibility(View.VISIBLE);


        //Set Bag List

        //Create Adapter
        mAdapter = new KeepListAdapter(getActivity(), mProductList, KeepFragment.this);
        //setAdapter
        lvKeepList.setAdapter(mAdapter);
    }

    public void deleteRow(Product item) {
        mProductList.remove(item);
        setListView();
        //mAdapter.remove(item);
        //mAdapter.notifyDataSetChanged();
    }

    public class KeepListAdapter extends ArrayAdapter<Product> {
        private String TAG = "BagListAdapter##";
        private Context mContext;
        private ArrayList<Product> mDataList;
        private int mScreenWidth;
        private Fragment mParentFragment;
        // View lookup cache
        private class ViewHolder {
            ViewPager pager;
        }


        public KeepListAdapter(Context context, ArrayList<Product> arrayList, Fragment parentFragment) {
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

            final Product item = getItem(position);
            KeepPagerAdapter mAdapter = new KeepPagerAdapter(mContext, item);
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
                        //Delete from Keep
                        removeFromKeep(item);
                        //((KeepFragment)mParentFragment).deleteRow(item);
                    }
                    else if(position == 2) {
                        //TODO Api Call
                        //Add to Bag, remove From Keep
                        //removeFromKeep(item);
                        addToBag(item);
                        //((KeepFragment)mParentFragment).deleteRow(item);
                    }

                }
            });

            return view;
        }

        private void addToBag(Product item) {
            ProductApi.get(item.getKey(), new Api.OnDefaultListener<Product>() {
                @Override
                public void onSuccess(Product data) {
                    mItem = data;
                    initialize_selector();
                    llSelector.setVisibility(View.VISIBLE);
                }
            });
        }

        private void removeFromKeep(final Product item) {
            ProductApi.removeFavorite(item, new Api.OnAuthListener<Void>() {
                @Override
                public void onStart() {
                }
                @Override
                public void onSuccess(Void data) {
                    //remove(item);
                    ((KeepFragment)mParentFragment).deleteRow(item);
                }
                @Override
                public void onFail() {
                }
            });
        }
    }

    private class OptionDialog extends Dialog {
        private ListView list;
        private HintAdapter mAdapter;

        public OptionDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_size);

            mAdapter = new HintAdapter();
            mAdapter.addAll(mItem.getProductSizes());
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(mAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!mAdapter.getItem(position).isSoldout()) {
                        Toast.makeText(getActivity(),"품절된 옵션입니다",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        selSize = mAdapter.getItem(position).getKey();
                        tvSelSize.setText(mAdapter.getItem(position).getSize());
                        dismiss();
                    }
                }
            });
        }

        private class HintAdapter extends ArrayAdapter<ProductSize> {
            public HintAdapter() {
                super(getActivity(), 0);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                ProductSize item = getItem(position);

                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_product_size, parent, false);

                if(item.isSoldout()) {
                    ((TextView) view.findViewById(R.id.tv_soldout)).setVisibility(View.GONE);

                    ((TextView) view.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tv_name)).setText(item.getSize());
                }
                else {
                    ((TextView) view.findViewById(R.id.tv_name)).setVisibility(View.GONE);

                    ((TextView) view.findViewById(R.id.tv_soldout)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tv_soldout)).setText(item.getSize());
                }

                return view;
            }
        }
    }
}
