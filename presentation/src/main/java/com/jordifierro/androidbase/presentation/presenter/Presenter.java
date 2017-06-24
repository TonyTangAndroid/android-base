package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.presentation.view.CleanView;

public interface Presenter {

    void create(CleanView view);

    void resume();

    void pause();

    void destroy();

}
