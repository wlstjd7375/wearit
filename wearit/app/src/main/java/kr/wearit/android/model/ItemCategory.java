package kr.wearit.android.model;

/**
 * Created by Administrator on 2015-11-20.
 */
public enum ItemCategory {
    WOMEN(0), MEN(1), LIFE_STYLE(2), LIVING(3);

    public final int value;

    private ItemCategory(int value) {
        this.value = value;
    }
}
