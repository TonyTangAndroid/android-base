package com.tony.tang.note.ui.feature.note.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.evernote.android.state.State;
import com.tony.tang.note.app.CleanActivity;

/**

 */

public class NoteDetailActivity extends CleanActivity implements NoteDetailFragment.Listener {

    public static final String PARAM_NOTE_ID = "param_note_id";

    @State
    String noteObjectId;

    public static Intent getCallingIntent(Context context, String noteObjectId) {
        Intent callingIntent = new Intent(context, NoteDetailActivity.class);
        callingIntent.putExtra(PARAM_NOTE_ID, noteObjectId);
        return callingIntent;
    }


    @Override
    protected void bindView() {

    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            noteObjectId = getIntent().getStringExtra(PARAM_NOTE_ID);
            addFragment(NoteDetailFragment.newInstance());
        }
    }


    @Override
    public String getNoteObjectId() {
        return this.noteObjectId;
    }


}
