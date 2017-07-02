package com.tony_tang.android.demo.feature.common;

/**
 * Creating a base holder class allows us to leverage ButterKnife's view binding for all subclasses.
 * This makes subclasses much cleaner, and is a highly recommended pattern.
 */
public class CleanViewStatus {
    public static final int IDLE = 0;
    public static final int LOADING = 1;
    public static final int REFRESHING = 2;
    public static final int LOADING_MORE = 3;

}
