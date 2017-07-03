package com.tony_tang.android.demo.feature.common;

/**
 * Creating a base holder class allows us to leverage ButterKnife's view binding for all subclasses.
 * This makes subclasses much cleaner, and is a highly recommended pattern.
 */
public class SpanType {
    public static final int FULL = 12;
    public static final int HALF = 6;
    public static final int ONE_THIRD = 4;
    public static final int QUARTER = 3;

}
