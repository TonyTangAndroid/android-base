package com.tony_tang.android.demo.feature.note_creation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanActivity;


public class NoteCreateActivity extends CleanActivity {

    public static Intent constructIntent(Activity activity) {

        return new Intent(activity, NoteCreateActivity.class);
    }

    @Override
    protected void bindView() {

    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new NoteCreateFragment());
        }
    }

}
