package com.tony_tang.android.demo.common.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.evernote.android.state.StateSaver;
import com.tony_tang.android.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**

 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateSaver.restoreInstanceState(this, savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        this.initializeActivity(savedInstanceState);
        this.initializeToolbar();
    }


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

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
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
