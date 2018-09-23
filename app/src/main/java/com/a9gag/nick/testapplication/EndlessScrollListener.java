package com.a9gag.nick.testapplication;

import android.util.Log;
import android.widget.AbsListView;

import static com.a9gag.nick.testapplication.HotPageFragment.isLoading;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int currentPage = 0;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    public EndlessScrollListener() {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
   /*     Log.d("9GAG", "totlaItemcount = " + totalItemCount
            + "firstVisibleItem = " + firstVisibleItem
            + "visibleItemCount = " + visibleItemCount);*/
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if ((firstVisibleItem + visibleItemCount == totalItemCount)
            && (totalItemCount != 0) && scrollState == SCROLL_STATE_IDLE) {
                 if(!isLoading) {
                     isLoading = true;
                    // Log.d("9GAG", "loadMore = " + scrollState);
                     onLoadMore(currentPage + 1, totalItemCount);
                 }
            }
    }
}