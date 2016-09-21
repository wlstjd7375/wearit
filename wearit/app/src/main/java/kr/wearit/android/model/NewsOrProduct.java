package kr.wearit.android.model;

import java.util.ArrayList;

/**
 * Created by KimJS on 2016-09-14.
 */
public class NewsOrProduct {
    private NewsPair newsPair;
    private ArrayList<Product> productList;
    private int dataType; // news pair = 1, product list = 2
    private int productType; // best item = 1, new item = 2, sale item = 3

    public NewsPair getNewsPair() {
        return newsPair;
    }

    public void setNewsPair(NewsPair newsPair) {
        this.newsPair = newsPair;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }
}
