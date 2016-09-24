package kr.wearit.android.model;

public enum StyleCategory {
	ALL(0), NEW(1), HOT(2);

	public final int value;

	private StyleCategory(int value) {
		this.value = value;
	}
}
