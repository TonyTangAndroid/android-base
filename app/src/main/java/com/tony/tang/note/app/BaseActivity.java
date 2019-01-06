package com.tony.tang.note.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindToolbar();
        bindView();
        if (savedInstanceState == null) {
            onCreate();
        } else {
            onRecreate(savedInstanceState);
        }
    }

    protected void onRecreate(Bundle savedInstanceState) {

    }

    protected abstract void onCreate();


    private void bindToolbar() {
        toolbar = findViewById(R.id.toolbar);
        initializeToolbar();
    }

    protected void bindView() {

    }

    protected int getLayoutId() {
        return R.layout.activity_layout;
    }

    protected void initializeToolbar() {
        if (toolbar != null) {
            if (this.useToolbar()) {
                setSupportActionBar(this.toolbar);
                if (this.useBackToolbar()) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }
                    toolbar.setNavigationOnClickListener(v -> finish());
                }
            } else {
                toolbar.setVisibility(View.GONE);
            }
        }

    }

    protected boolean useToolbar() {
        return true;
    }

    protected boolean useBackToolbar() {
        return true;
    }

    protected void bindFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Nullable
    protected Toolbar getToolbar() {
        return this.toolbar;
    }
}
