package com.tony_tang.android.demo.feature.note_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.BaseActivityWithFragmentInjector;

/**

 */

public class NoteDetailActivity extends BaseActivityWithFragmentInjector implements NoteDetailFragment.Listener {

    public static final String PARAM_NOTE_ID = "param_note_id";

    private String noteObjectId;

    public static Intent getCallingIntent(Context context, String noteObjectId) {
        Intent callingIntent = new Intent(context, NoteDetailActivity.class);
        callingIntent.putExtra(PARAM_NOTE_ID, noteObjectId);
        return callingIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        if (savedInstanceState == null) {
            noteObjectId = getIntent().getStringExtra(PARAM_NOTE_ID);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, NoteDetailFragment.newInstance())
                    .commit();
        } else {
            this.noteObjectId = savedInstanceState.getString(PARAM_NOTE_ID);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(PARAM_NOTE_ID, this.noteObjectId);
        }
        super.onSaveInstanceState(outState);
    }

    public String getNoteObjectId() {
        return this.noteObjectId;
    }


}
