package kr.wearit.android.model;

public enum NewsCategory {
	ALL(0), SALE(1), NEW_IN(2), OTHERS(3);

	public final int value;

	private NewsCategory(int value) {
		this.value = value;
	}
}
