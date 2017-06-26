package com.tony_tang.android.demo.common.base;

import android.app.Fragment;
import android.content.Context;

import dagger.android.AndroidInjection;

public class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

}
