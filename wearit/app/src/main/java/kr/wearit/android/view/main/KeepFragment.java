package kr.wearit.android.view.main;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import kr.wearit.android.adapter.KeepListAdapter;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.CartApi;
import kr.wearit.android.controller.ProductApi;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductSize;
import kr.wearit.android.view.MainActivity;

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

    private Handler refresh = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                setListView();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_keep, container, false);

        tvToolbarTitle = (TextView)view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("KEEP");

        rlGoShopping = (RelativeLayout)view.findViewById(R.id.rlGoShopping);
        lvKeepList = (ListView)view.findViewById(R.id.lvKeepList);

        llSelector = (LinearLayout) view.findViewById(R.id.ll_selector);

        ((RelativeLayout) view.findViewById(R.id.top_bar)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(llSelector.getVisibility() == View.VISIBLE) {
                    llSelector.setVisibility(View.GONE);
                }

                return false;
            }
        });

        lvKeepList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(llSelector.getVisibility() == View.VISIBLE) {
                    llSelector.setVisibility(View.GONE);
                }

                return false;
            }
        });

        ((RelativeLayout) view.findViewById(R.id.rl_main_layout)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(llSelector.getVisibility() == View.VISIBLE) {
                    llSelector.setVisibility(View.GONE);
                }

                return false;
            }
        });

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
                if(selSize != 0) {
                    CartApi.add(selSize, count, new Api.OnAuthListener<Integer>() {
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(Integer data) {
                            Toast.makeText(getActivity(), "ITBAG에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            //mAdapter.removeFromKeep(mItem);
                            llSelector.setVisibility(View.GONE);
                            ProductApi.removeFavorite(mItem, new Api.OnAuthListener<Void>() {
                                @Override
                                public void onStart() {
                                }
                                @Override
                                public void onSuccess(Void data) {
                                    refreshFragment();
                                }
                                @Override
                                public void onFail() {
                                }
                            });
                        }
                        @Override
                        public void onFail() {
                            System.out.println("실패 ㅅㅂ");
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "옵션과 수량을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
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
        Log.d(TAG, "deleteRow");
        mProductList.remove(item);
        setListView();
        //mAdapter.remove(item);
        //mAdapter.notifyDataSetChanged();
    }

    public void setSelctor(Product data) {
        mItem = data;
        initialize_selector();
        llSelector.setVisibility(View.VISIBLE);
    }

    private void refreshFragment() {
        getFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
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
