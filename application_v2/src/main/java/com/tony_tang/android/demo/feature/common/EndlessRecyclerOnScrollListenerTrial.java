package com.tony_tang.android.demo.feature.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


public class EndlessRecyclerOnScrollListenerTrial extends RecyclerView.OnScrollListener {

    private static final int DEFAULT_VISIBLE_THRESHOLD = 5;// The default amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = DEFAULT_VISIBLE_THRESHOLD; // The minimum amount of items to have below your current scroll position before loading more.

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewScrollListener listener;

    public EndlessRecyclerOnScrollListenerTrial(RecyclerView.LayoutManager layoutManager, RecyclerViewScrollListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("RecyclerViewScrollListener must not be null");
        }
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    @SuppressWarnings("unused")
    public void setVisibleThreshold(int visibleThreshold) {
        if (visibleThreshold <= 0) {
            throw new IllegalArgumentException("The minimum amount of items to have below your current scroll position before loading more should be larger than 0");
        }
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = getVisibleItemCount(recyclerView);
        int totalItemCount = getTotalItemCount();
        int firstVisibleItem = getFirstVisibleItem();

        if (isScrollingDown(dy)) {
            if (isReachingBottom(totalItemCount, visibleItemCount, firstVisibleItem, visibleThreshold)) {
                listener.onBottomToBeReached();
            }
        }

    }

    private boolean isScrollingDown(int dy) {
        return dy > 0;
    }

    private boolean isReachingBottom(int totalItemCount, int visibleItemCount, int firstVisibleItem, int visibleThreshold) {
        return totalItemCount <= (firstVisibleItem + visibleThreshold + visibleItemCount);
    }

    private int getVisibleItemCount(RecyclerView recyclerView) {
        return recyclerView.getChildCount();
    }

    private int getTotalItemCount() {
        return layoutManager.getItemCount();
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

        void onBottomToBeReached();

    }

}