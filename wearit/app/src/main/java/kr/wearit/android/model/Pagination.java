package kr.wearit.android.model;

import android.os.Parcelable;

import java.util.ArrayList;

public class Pagination<T extends Parcelable> /*implements Parcelable*/{

	public static final int SIZE = 10;

	//

	private ArrayList<T> list;
	private int index;
	private int count;

	//

	public Pagination() {
	}

	//

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/*
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(list);
		dest.writeInt(index);
		dest.writeInt(count);
	}

	private Pagination(Parcel in) {
		in.readTypedList(list, null);
		index = in.readInt();
		count = in.readInt();
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator<Pagination> CREATOR = new Parcelable.Creator<Pagination>() {

		public Pagination createFromParcel(Parcel in) {
			return new Pagination(in);
		}

		public Pagination[] newArray(int size) {
			return new Pagination[size];
		}
	}
	*/
}
