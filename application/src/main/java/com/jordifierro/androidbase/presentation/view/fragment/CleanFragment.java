package com.jordifierro.androidbase.presentation.view.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jordifierro.androidbase.presentation.dependency.component.FragmentInjector;
import com.jordifierro.androidbase.presentation.presenter.BasePresenter;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.activity.base.CleanActivity;

import butterknife.ButterKnife;

public abstract class CleanFragment extends Fragment implements CleanView {

    private ProgressDialog progressDialog;

    protected abstract void callInjection();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callInjection();
    }

    protected abstract int layoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(layoutId(), container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void initUI() {

    }

    protected abstract BasePresenter presenter();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter().create(this);
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
    public void handleError(Throwable error) {
        ((CleanView) getActivity()).handleError(error);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        ((CleanView) getActivity()).close();
    }

    public void closeAndDisplayLogin() {
        ((CleanView) getActivity()).closeAndDisplayLogin();
    }

    protected FragmentInjector getFragmentInjector() {
        return ((CleanActivity) getActivity()).getFragmentInjector();
    }
}
