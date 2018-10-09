package com.tony_tang.android.demo.common.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tony_tang.android.demo.presentation.presenter.base.BasePresenter;
import com.tony_tang.android.demo.presentation.view.base.CleanView;

import dagger.android.AndroidInjection;

public abstract class CleanFragment extends BaseFragment implements CleanView {

    private ProgressDialog progressDialog;

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
    public void showLoader() {
        if (this.progressDialog == null) this.progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.show();
    }

    @Override
    public void hideLoader() {
        if (this.progressDialog != null) this.progressDialog.dismiss();
    }

    @Override
    public void handleError(Throwable throwable) {
        ((CleanView) getActivity()).handleError(throwable);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        ((CleanView) getActivity()).close();
    }

}
