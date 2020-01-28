package com.jordifierro.androidbase.presentation.view.activity;

import android.os.Bundle;

import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.activity.base.CleanActivity;
import com.jordifierro.androidbase.presentation.view.LoginFragment;

public class LoginActivity extends CleanActivity implements LoginFragment.Listener {

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new LoginFragment());
        }
    }

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    public void viewNotes() {

    }

    @Override
    public void displayRegister() {
     }

    @Override
    public void forgotPassword() {
     }

}
