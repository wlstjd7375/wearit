package kr.wearit.android.model;

import android.os.Parcelable;

public interface Key<T> extends Entity, Parcelable {

	public interface Name<T> extends Key<T> {

		String getName();
	}
}
