package com.tony_tang.android.demo.presentation.view.base;

public interface CleanView {

    void initUI();

    void showLoader();

    void hideLoader();

    void handleError(Throwable throwable);

    void showMessage(String message);

    void close();

}
