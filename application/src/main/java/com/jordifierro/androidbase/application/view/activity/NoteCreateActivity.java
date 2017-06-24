package com.jordifierro.androidbase.application.view.activity;

import android.os.Bundle;

import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.application.view.activity.base.CleanActivity;
import com.jordifierro.androidbase.application.view.fragment.NoteCreateFragment;

public class NoteCreateActivity extends CleanActivity {

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new NoteCreateFragment());
        }
    }

}
