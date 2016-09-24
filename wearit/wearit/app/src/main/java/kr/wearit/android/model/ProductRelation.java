package kr.wearit.android.model;

import android.os.Parcelable;

import java.util.ArrayList;

public interface ProductRelation extends Parcelable {

	int getProductRelationKey();

	int getProductRelationCount();

	ArrayList<Product> getProductRelationList();

	void setProductRelationList(ArrayList<Product> list);
}
