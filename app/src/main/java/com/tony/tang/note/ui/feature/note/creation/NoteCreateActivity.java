package com.tony.tang.note.ui.feature.note.creation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tony.tang.note.app.R;
import com.tony.tang.note.app.CleanActivity;


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
