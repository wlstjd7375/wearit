package kr.wearit.android.view.check;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.App;
import kr.wearit.android.R;
import kr.wearit.android.model.Brand;
import kr.wearit.android.model.Coupon;
import kr.wearit.android.model.DeliverInfo;
import kr.wearit.android.model.Order;
import kr.wearit.android.model.ProductCart;
import kr.wearit.android.model.ProductCategory;
import kr.wearit.android.model.User;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.util.Util;

public class CartCheckActivity extends CheckActivity {

    private ArrayList<ProductCart> productList;
    private ArrayList<DeliverInfo> deliverList;

    private ArrayList<BrandCart> brandCartList;

    private ExpandableListView lvProduct;
    private BrandCartAdapter mAdapter;

    private View header;
    private View footer;

    private String orderType;

    private User user;

    private EditText etRequire;

    private int coupon;

    private Order order;

    private boolean orderFlag;

    private int totalCheck;
    private int totalDeliverPrice;

    private int couponPrice;

    private Coupon useCoupon;

    private ArrayList<Coupon> couponList;

    private Button btOrder;
    private String paytype;

    private TextView tvReceiverName;
    private TextView tvReceiverPhone;
    private TextView tvReceiverEmail;
    private TextView tvReceiverAddr1;
    private TextView tvReceiverAddr2;

    private LinearLayout llCouponList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_check);

        productList = getIntent().getParcelableArrayListExtra("cart");
        deliverList = getIntent().getParcelableArrayListExtra("deliver");

        lvProduct = (ExpandableListView) findViewById(R.id.lv_order);

        useCoupon = null;
        user = App.getInstance().getUser();
        couponList = App.getInstance().getCouponList();

        makeBrandCart();
        makeHeaderView();
        makeFooterView();
        makeListView();
    }

    public void makeHeaderView() {
        header = getActivity().getLayoutInflater().inflate(R.layout.header_order, null);
        lvProduct.addHeaderView(header);
    }

    public void makeFooterView() {
        footer = getActivity().getLayoutInflater().inflate(R.layout.footer_order, null);

        calculPrice();

        tvReceiverName = (TextView) footer.findViewById(R.id.tv_receiver_name);
        tvReceiverEmail = (TextView) footer.findViewById(R.id.tv_receiver_email);
        tvReceiverPhone = (TextView) footer.findViewById(R.id.tv_receiver_phone);
        tvReceiverAddr1 = (TextView) footer.findViewById(R.id.tv_receiver_address1);
        tvReceiverAddr2 = (TextView) footer.findViewById(R.id.tv_receiver_address2);

        tvReceiverName.setText(user.getName());
        tvReceiverPhone.setText(user.getPhone());
        tvReceiverEmail.setText(user.getId());
        tvReceiverAddr1.setText(user.getAddress1());
        tvReceiverAddr2.setText(user.getAddress2());

        ((TextView) footer.findViewById(R.id.tv_modify_receiver)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //수정 버튼 눌렀을 때 코딩
            }
        });
        //쿠폰 목록 만들기
        llCouponList = ((LinearLayout) footer.findViewById(R.id.ll_possible_coupon));

        for(int i=0;i<couponList.size();i++) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.listrow_order_coupon, null);
            ((TextView) view.findViewById(R.id.tv_coupon_name)).setText(couponList.get(i).getName());
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    useCoupon = couponList.get(finalI);
                    coupon = couponList.get(finalI).getKey();
                    ((TextView) findViewById(R.id.tv_select_coupon)).setText(couponList.get(finalI).getName());
                    llCouponList.setVisibility(View.GONE);
                    initTotal();
                }
            });
            llCouponList.addView(view);
        }
        ((RelativeLayout) footer.findViewById(R.id.rl_select_coupon)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llCouponList.getVisibility() == View.VISIBLE){
                    llCouponList.setVisibility(View.GONE);
                }
                else {
                    llCouponList.setVisibility(View.VISIBLE);
                }
            }
        });
        initTotal();
        lvProduct.addFooterView(footer);
    }

    public void calculPrice() {
        String calculProductPrice = "";
        String calculTipPrice = "";

        int productPrice = 0;
        int tipPrice = 0;

        for(int i=0;i<productList.size();i++) {
            ProductCart productCart = productList.get(i);
            if(productCart.isSale()) {
                calculProductPrice += (Util.formatWon(productCart.getSale_price() * productCart.getCount())+"원+");
                productPrice += (productCart.getSale_price() * productCart.getCount());
            }
            else {
                calculProductPrice += (Util.formatWon(productCart.getPrice() * productCart.getCount())+"원+");
                productPrice += (productCart.getPrice() * productCart.getCount());
            }
        }
        calculProductPrice = calculProductPrice.substring(0,calculProductPrice.length()-1);
        totalCheck = productPrice;

        for(int i=0;i<brandCartList.size();i++) {
            BrandCart brandCart = brandCartList.get(i);
            int brandProductPrice = 0;
            for(int j=0;j<brandCart.getCartList().size();j++) {
                ProductCart productCart = brandCart.getCartList().get(j);
                if(productCart.isSale()) {
                    brandProductPrice += productCart.getSale_price();
                }
                else {
                    brandProductPrice += productCart.getPrice();
                }
            }
            for(int j=0;j<deliverList.size();j++) {
                if(brandCart.getBrand() == deliverList.get(j).getBrand()) {
                    if(brandProductPrice >= deliverList.get(j).getBasis()) {
                        calculTipPrice += (Util.formatWon(deliverList.get(j).getDeliverPrice())+"원+");
                        tipPrice += deliverList.get(j).getDeliverPrice();
                        break;
                    }
                }
            }
        }
        calculTipPrice = calculTipPrice.substring(0,calculTipPrice.length()-1);
        totalDeliverPrice = tipPrice;

        ((TextView) footer.findViewById(R.id.tv_calcul_price)).setText(calculProductPrice);
        ((TextView) footer.findViewById(R.id.tv_order_product_price)).setText(Util.formatWon(productPrice)+"원");


        ((TextView) footer.findViewById(R.id.tv_calcul_tip)).setText(calculTipPrice);
        ((TextView) footer.findViewById(R.id.tv_tip_price)).setText(Util.formatWon(tipPrice)+"원");
    }

    public void initTotal() {
        int totalPrice = 0;
        int couponSalePrice = 0;
        if(useCoupon != null) {
            if(useCoupon.getSale_cost() != null && useCoupon.getSale_cost() != 0) {
//                totalCheck -= useCoupon.getSale_cost();
                couponSalePrice = useCoupon.getSale_cost();
            }
            else if(useCoupon.getSale_rate() != null && useCoupon.getSale_rate() != 0) {
//                totalCheck = totalCheck - (((totalCheck) * useCoupon.getSale_rate())/100);
                couponSalePrice = (((totalCheck) * useCoupon.getSale_rate())/100);
            }
        }
        totalPrice = totalCheck + totalDeliverPrice - couponSalePrice;

        ((TextView) footer.findViewById(R.id.tv_total_price)).setText(Util.formatWon(totalPrice) + "원");
        ((TextView) footer.findViewById(R.id.tv_coupon_price)).setText(Util.formatWon(couponSalePrice) + "원");
    }

    public void makeListView() {
        mAdapter = new BrandCartAdapter();
        mAdapter.setData(brandCartList);
        lvProduct.setAdapter(mAdapter);
        for(int i=0;i<brandCartList.size();i++) {
            lvProduct.expandGroup(i);
        }
    }

    public void makeBrandCart() {
        brandCartList = new ArrayList<BrandCart>();
        int currentBrand = 0;
//        BrandCart currentBrandCart = new BrandCart();
        int j = 0;
        for(int i=0;i<productList.size();i++) {
            if(currentBrand == 0) {
                BrandCart currentBrandCart = new BrandCart();
                j = brandCartList.size();
                currentBrand = productList.get(i).getBrand();
                currentBrandCart.setBrand(currentBrand);
                currentBrandCart.setBrandName(productList.get(i).getBrandname());
                currentBrandCart.addCart(productList.get(i));
                brandCartList.add(currentBrandCart);
                continue;
            }
            if(currentBrand == productList.get(i).getBrand()) {
                //현재 Product Cart의 BrandKey가 안바뀌었을 때
                //Cart를 Add한다.
                brandCartList.get(j).addCart(productList.get(i));
            }
            else {
                //ProductCart의 BrandKey가 바뀌었을 때
                //현재까지 만든 BrandCart를 리스트에 Add하고
                //currentBrandCart의 Brand값을 현재 productCart의 Brand로, CartList초기화
                BrandCart currentBrandCart = new BrandCart();

                currentBrand = productList.get(i).getBrand();
                currentBrandCart.setBrand(currentBrand);
                currentBrandCart.setBrandName(productList.get(i).getBrandname());
                currentBrandCart.addCart(productList.get(i));

                j = brandCartList.size();
                brandCartList.add(currentBrandCart);
            }
        }
        //for문이 끝난후 마지막 BrandCart Add
        System.out.println("Brand Cart Size : " + brandCartList.size());
    }


    private class BrandCartAdapter extends BaseExpandableListAdapter {

        private List<BrandCart> data;

        //

        public BrandCartAdapter() {
            data = new ArrayList<BrandCart>();
        }

        //
        public void setData(ArrayList<BrandCart> data) {
            this.data = data;
        }

        @Override
        public int getGroupCount() {
            return data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            List<ProductCart> list = data.get(groupPosition).getCartList();

            return list != null ? data.get(groupPosition).getCartList().size() : 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return data.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupPosition).getCartList().get(childPosition);
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
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_order_brand, parent, false);

            TextView text = (TextView) view.findViewById(R.id.tv_brand);
            BrandCart item = (BrandCart)getGroup(groupPosition);
            text.setText(item.getBrandName());

            return view;
        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null)
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_order_item, parent, false);

            ProductCart mItem = (ProductCart) getChild(groupPosition,childPosition);
            int currentPrice = 0;
            int count = 0;

            ((TextView) view.findViewById(R.id.tv_brand_in_item)).setText(mItem.getBrandname());

            if (mItem.isSale()) {
                ((TextView) view.findViewById(R.id.tvSalePrice)).setText(Util.formatWon(mItem.getSale_price()) + "원");
                ((TextView) view.findViewById(R.id.tvSalePrice)).setVisibility(View.VISIBLE);
                currentPrice = mItem.getSale_price();
            } else {
                ((TextView) view.findViewById(R.id.tvSalePrice)).setVisibility(View.GONE);
                currentPrice = mItem.getPrice();
            }
            ImageUtil.display(((ImageView) view.findViewById(R.id.ivProduct)), mItem.getImagepath());

            count = mItem.getCount();

            ((TextView) view.findViewById(R.id.tvSize)).setText("SIZE : " + mItem.getSize());
            ((TextView) view.findViewById(R.id.tvItemCount)).setText("개수 : " + String.valueOf(count));

            ((TextView) view.findViewById(R.id.tvPrice)).setText(Util.formatWon(count * currentPrice)+"원");

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

    class BrandCart {
        private int brand;
        private String brandName;
        private ArrayList<ProductCart> cartList;

        public BrandCart() {
            cartList = new ArrayList<ProductCart>();
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrand(int brand) {
            this.brand = brand;
        }

        public int getBrand() {
            return brand;
        }

        public ArrayList<ProductCart> getCartList() {
            return cartList;
        }

        public ProductCart getCart(int position) {
            return cartList.get(position);
        }

        public void initializeCartList() {
            brand = 0;
            cartList.clear();
        }

        public void addCart(ProductCart productCart) {
            cartList.add(productCart);
        }
    }
}