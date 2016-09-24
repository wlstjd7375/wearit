package kr.wearit.android.model;

public enum ProductType {
	NEW(1), HOT(2), SALE(3);

	public final int value;

	private ProductType(int value) {
		this.value = value;
	}
}
