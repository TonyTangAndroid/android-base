package com.jordifierro.androidbase.presentation.view.base;

import java.util.List;

public interface BaseListView extends CleanView {

    void showNoteEntityList(List<?> notes);

    void showProcessing();

    void onRetrievingDataCompleted();
}
