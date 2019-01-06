package com.tony.tang.note.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.evernote.android.state.StateSaver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**

 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateSaver.restoreInstanceState(this, savedInstanceState);
        setContentView(getLayoutId());

        bindToolbar();
        this.initializeActivity(savedInstanceState);
        this.initializeToolbar();
    }


    private void bindToolbar() {
        toolbar = findViewById(R.id.toolbar);
        bindView();
    }

    protected abstract void bindView();


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateSaver.saveInstanceState(this, outState);
    }


    protected int getLayoutId() {
        return R.layout.activity_layout;
    }

    protected abstract void initializeActivity(Bundle savedInstanceState);

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

    protected void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public Context context() {
        return getApplicationContext();
    }

    protected Toolbar getToolbar() {
        return this.toolbar;
    }

    public boolean isLoaderShowing() {
        if (this.progressDialog == null) return false;
        return this.progressDialog.isShowing();
    }

    public void showLoader() {
        if (this.progressDialog == null) this.progressDialog = new ProgressDialog(this);
        this.progressDialog.show();
    }

    public void hideLoader() {
        if (this.progressDialog != null) this.progressDialog.dismiss();
        this.progressDialog = null;
    }

}
