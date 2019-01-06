package com.tony.tang.note.ui.feature.note.edit;

import android.app.Activity;
import android.content.Intent;

import com.tony.tang.note.app.BaseActivity;


public class NoteEditActivity extends BaseActivity {

    private static final String EXTRA_OBJECT_ID = "extra_object_id";

    public static Intent constructIntent(Activity activity, String objectId) {
        return new Intent(activity, NoteEditActivity.class).putExtra(EXTRA_OBJECT_ID, objectId);
    }

    @Override
    protected void onCreate() {
        bindFragment(NoteEditFragment.newInstance(getIntent().getStringExtra(EXTRA_OBJECT_ID)));
    }
}
