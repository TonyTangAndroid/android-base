package com.jordifierro.androidbase.presentation.view;

import android.content.Context;

public interface CleanView {

    void initUI();

    Context context();

    void showLoader();

    void hideLoader();

    void handleError(Throwable error);

    void showMessage(String message);

    void close();

    void closeAndDisplayLogin();
}
