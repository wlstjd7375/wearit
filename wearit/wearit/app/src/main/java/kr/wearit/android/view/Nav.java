package kr.wearit.android.view;

/**
 * Created by KimJS on 2016-09-07.
 */
public enum Nav {

    NONE, //
    MAIN_SHOP_BRAND, MAIN_PRODUCT_BRAND, //
    SHOP_INFO(0), SHOP_BRAND(1), SHOP_PRODUCT(2), SHOP_NEWS(3), SHOP_STYLE(4), //
    BRAND, BRAND_SHOP, BRAND_PRODUCT, //
    ;

    public final int tab;

    private Nav() {
        this.tab = 0;
    }

    private Nav(int tab) {
        this.tab = tab;
    }
}
