package com.jordifierro.androidbase.presentation.view;

public interface CleanView {

    void initUI();

    void showLoader();

    void hideLoader();

    void handleError(Throwable throwable);

    void showMessage(String message);

    void close();

}
