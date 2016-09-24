package kr.wearit.android.model;

public enum Gender {
	ALL(1, "all"), BOY(2, "boy"), GIRL(3, "girl"), LIFESTYLE(4, "living"), LIVING(5, "lifestyle");

	public final int value;
	public final String title;

	private Gender(int value, String title) {
		this.value = value;
		this.title = title;
	}

	public static String[] titles() {
		return new String[] { ALL.title, BOY.title, GIRL.title, LIFESTYLE.title, LIVING.title };
	}
}
