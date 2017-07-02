package com.jordifierro.androidbase.presentation.view.base;

import java.util.List;

public interface BaseListView extends CleanView {

    void showEntityList(List<?> entityList);

    void showProcessing();

    void onRetrievingDataCompleted();
}
