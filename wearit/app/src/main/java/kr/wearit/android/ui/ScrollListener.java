package kr.wearit.android.ui;

import android.util.Log;
import android.widget.AbsListView;

import kr.wearit.android.Config;
import kr.wearit.android.model.Pagination;

/**
 * Created by KimJS on 2016-09-11.
 */
public abstract class ScrollListener implements AbsListView.OnScrollListener {

    private static final String TAG = ScrollListener.class.getSimpleName();
    private static final boolean LOG = Config.LOG;

    //

    private int mPage = 0;
    private int mPageSize = Integer.MAX_VALUE;
    private boolean mFetching = false;

    private int currentItem;
    //

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (LOG)
            Log.d(TAG, "onScrollStateChanged // scrollState = " + scrollState);

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (LOG)
            Log.d(TAG, "onScroll // firstVisibleItem = " + firstVisibleItem + ", visibleItemCount = " + visibleItemCount + ", totalItemCount = " + totalItemCount +", Current Page : " + mPage);

        if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount > 1)
            fetch(mPage + 1);

    }

    //

    public void fetch(int page) {
        if (LOG) {
            Log.d(TAG, "fetch // page = " + page);
            Log.d(TAG, "fetch // size = " + mPageSize);
            Log.d(TAG, "fetch // fetching = " + mFetching);
        }

        if (page > mPageSize)
            return;

        if (mFetching)
            return;

        mFetching = true;

        onFetch(this, page);
    }

    public abstract void onFetch(ScrollListener listener, int page);

//    public void setFetching(boolean flag){
//        mFetching = flag;
//    }

    public void initialize() {
        if(LOG)
            Log.d(TAG,"initialize");
        mPage = 0;
        mPageSize = Integer.MAX_VALUE;
        mFetching = false;
    }

    public void onFetched(Pagination<?> data) {
        if (LOG)
            Log.d(TAG, "onFetched // page = " + data.getIndex() + ", size = " + data.getCount());

        mPage = data.getIndex();
        mPageSize = data.getCount();
        mFetching = false;
    }
}
