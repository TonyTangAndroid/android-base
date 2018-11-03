package com.tony_tang.android.demo.feature.common;

import com.airbnb.epoxy.EpoxyHolder;

/**
 * Creating a base holder class allows us to leverage ButterKnife's view binding for all subclasses.
 * This makes subclasses much cleaner, and is a highly recommended pattern.
 */
public abstract class BaseEpoxyHolder extends EpoxyHolder {

}
