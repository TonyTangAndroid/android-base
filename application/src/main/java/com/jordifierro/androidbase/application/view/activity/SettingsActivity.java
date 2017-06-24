package com.jordifierro.androidbase.application.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.application.view.activity.base.CleanActivity;
import com.jordifierro.androidbase.application.view.fragment.SettingsFragment;

public class SettingsActivity extends CleanActivity implements SettingsFragment.Listener {

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new SettingsFragment());
        }
    }

    @Override
    public void showTerms() {
        startActivity(new Intent(this, TermsActivity.class));
    }

    @Override
    public void showPrivacy() {
        startActivity(new Intent(this, PrivacyActivity.class));
    }
}
