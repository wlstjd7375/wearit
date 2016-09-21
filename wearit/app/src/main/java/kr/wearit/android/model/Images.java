package kr.wearit.android.model;

import android.os.Parcelable;

public interface Images extends Parcelable {

	enum Type {
		SHOP, BRAND, PRODUCT, STYLE
	}

	Type getImagesType();

	int getImagesSize();

	Resource getImagesResource(int i);
}
