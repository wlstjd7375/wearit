package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Board implements Parcelable {

	public enum SearchRange {
		TITLE_CONTENT(1), TITLE(2), USER(3);

		public final int value;

		private SearchRange(int value) {
			this.value = value;
		}
	}

	//

	public static final Board ENTIRE = createBoard(0, "전체게시판", true);
	public static final Board RECENT = createBoard(-1, "최근 게시글", true);
	public static final Board SHOP_REVIEW = createBoard(1, "쇼핑 후기", true);

	//

	public static boolean equals(Board board1, Board board2) {
		if (board1 == board2)
			return true;

		if (board1 == null || board2 == null)
			return false;

		return board1.getKey() == board2.getKey();
	}

	//

	private static Board createBoard(int key, String title, boolean shop) {
		Board board = new Board();
		board.setKey(key);
		board.setTitle(title);

		return board;
	}

	//

	private int key;
	private String title;
	private boolean shop;

	//

	public Board() {
	}

	//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isShop() {
		return shop;
	}

	public void setShop(boolean shop) {
		this.shop = shop;
	}

	//

	@Override
	public String toString() {
		return title; // for android layout adapter
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(key);
		dest.writeString(title);
	}

	private Board(Parcel in) {
		key = in.readInt();
		title = in.readString();
	}

	public static final Creator<Board> CREATOR = new Creator<Board>() {

		@Override
		public Board createFromParcel(Parcel in) {
			return new Board(in);
		}

		@Override
		public Board[] newArray(int size) {
			return new Board[size];
		}
	};
}
