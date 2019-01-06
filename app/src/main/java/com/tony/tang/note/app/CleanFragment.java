package com.tony.tang.note.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tony.tang.note.presenter.BasePresenter;
import com.tony.tang.note.presenter.CleanView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class CleanFragment extends Fragment implements CleanView {

    protected abstract int getLayoutId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
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

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(requireContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
