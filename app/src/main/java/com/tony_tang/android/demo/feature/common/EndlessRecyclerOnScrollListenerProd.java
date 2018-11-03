package com.tony_tang.android.demo.feature.common;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import javax.inject.Inject;

public class EndlessRecyclerOnScrollListenerProd extends RecyclerView.OnScrollListener {
    public static final int STAGGER_VIEW_COLUMN_COUNT = 2;

    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    private int currentPage = 1;
    private RecyclerViewScrollListener listener;

    private RecyclerView.LayoutManager layoutManager;
    private boolean isFirstItemVisible;

    @Inject
    public EndlessRecyclerOnScrollListenerProd(RecyclerView.LayoutManager layoutManager, RecyclerViewScrollListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("RecyclerViewScrollListener must not be null");
        }
        this.listener = listener;
        this.layoutManager = layoutManager;
    }

    public void reset(int previousTotal, boolean loading) {
        this.previousTotal = previousTotal;
        this.loading = loading;
        this.currentPage = 1;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = getFirstVisibleItem();
//        Log.d("onScrolled", "firstVisibleItem:" + firstVisibleItem + ", visibleItemCount:" + visibleItemCount + ", position: " + (visibleItemCount > 0 ? recyclerView.getChildAt(0).getTop() : -999));
        boolean isFirstItemVisible = firstVisibleItem < STAGGER_VIEW_COLUMN_COUNT && visibleItemCount > 0 && reachedTop(recyclerView);
        if (isFirstItemVisible != this.isFirstItemVisible) {
            listener.onFirstItemVisibilityChange(isFirstItemVisible);
            this.isFirstItemVisible = isFirstItemVisible;
        }
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            currentPage++;
            listener.triggerLoadMore(currentPage);
            loading = true;
        }
    }

    private boolean reachedTop(RecyclerView recyclerView) {
        return recyclerView.getChildAt(0).getTop() >= 0;
    }

    private int getFirstVisibleItem() {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] positions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            return getMinimum(positions);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
    }

    private int getMinimum(int[] positions) {
        int result = positions[0];
        for (int i = 1; i < positions.length; i++) {
            if (result < positions[i]) {
                result = positions[i];
            }
        }
        return result;
    }


    public interface RecyclerViewScrollListener {

        void triggerLoadMore(int currentPage);

        void onFirstItemVisibilityChange(boolean visible);

    }

}