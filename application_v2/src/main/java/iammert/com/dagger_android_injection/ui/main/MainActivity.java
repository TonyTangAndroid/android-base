package iammert.com.dagger_android_injection.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import iammert.com.dagger_android_injection.R;
import iammert.com.dagger_android_injection.ui.detail.BaseActivity;
import iammert.com.dagger_android_injection.ui.detail.activity.DetailActivity;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter mainPresenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });
        mainPresenter.loadMain();

    }

    @Override
    public void onMainLoaded() {
        Log.v("TEST", "Main page is loaded.");
        view.callOnClick();
    }
}
