package com.tony.tang.note.ui.feature.note.detail;

import android.content.Context;
import android.content.Intent;

import com.tony.tang.note.app.BaseActivity;

public class NoteDetailActivity extends BaseActivity {

    private static final String EXTRA_OBJECT_ID = "extra_object_id";

    public static Intent constructIntent(Context context, String noteObjectId) {
        Intent callingIntent = new Intent(context, NoteDetailActivity.class);
        callingIntent.putExtra(EXTRA_OBJECT_ID, noteObjectId);
        return callingIntent;
    }

    @Override
    protected void onCreate() {
        bindFragment(NoteDetailFragment.newInstance(getIntent().getStringExtra(EXTRA_OBJECT_ID)));
    }
}
