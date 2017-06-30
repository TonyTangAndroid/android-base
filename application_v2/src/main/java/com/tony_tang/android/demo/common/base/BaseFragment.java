package com.tony_tang.android.demo.common.base;

import android.app.Fragment;
import android.content.Context;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class BaseFragment extends Fragment implements HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

}
