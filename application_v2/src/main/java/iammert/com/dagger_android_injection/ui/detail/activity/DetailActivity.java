package iammert.com.dagger_android_injection.ui.detail.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import iammert.com.dagger_android_injection.R;
import iammert.com.dagger_android_injection.ui.detail.BaseActivityWithFragmentInjector;
import iammert.com.dagger_android_injection.ui.detail.fragment.v2.NoteDetailFragment;

/**
 * Created by mertsimsek on 25/05/2017.
 */

public class DetailActivity extends BaseActivityWithFragmentInjector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, getFragment())
                    .commit();
    }

    private Fragment getFragment() {
        return NoteDetailFragment.newInstance();
    }


}
