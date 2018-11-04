package com.tony.tang.note.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tony.tang.note.presenter.BasePresenter;
import com.tony.tang.note.presenter.CleanView;

import dagger.android.AndroidInjection;

public abstract class CleanFragment extends BaseFragment implements CleanView {

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    protected abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(getLayoutId(), container, false);
        bindView(fragmentView);
        return fragmentView;
    }

    protected abstract void bindView(View rootView);

    @Override
    public void initUI() {

    }

    protected abstract BasePresenter presenter();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter().create();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter().resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter().pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter().destroy();
    }

    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void handleError(Throwable throwable) {
        ((CleanView) getActivity()).handleError(throwable);
    }

}
