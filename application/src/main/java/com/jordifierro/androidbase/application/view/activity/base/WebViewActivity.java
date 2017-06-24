package com.jordifierro.androidbase.application.view.activity.base;

import android.os.Bundle;

import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.application.view.fragment.WebViewFragment;

public abstract class WebViewActivity extends BaseActivity implements WebViewFragment.Listener {

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new WebViewFragment());
        }
    }

}
