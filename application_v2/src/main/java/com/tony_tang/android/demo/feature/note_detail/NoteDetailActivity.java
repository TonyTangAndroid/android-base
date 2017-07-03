package com.tony_tang.android.demo.feature.note_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.evernote.android.state.State;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanActivity;

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
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            noteObjectId = getIntent().getStringExtra(PARAM_NOTE_ID);
            addFragment(R.id.fragment_container, NoteDetailFragment.newInstance());
        }
    }


    public String getNoteObjectId() {
        return this.noteObjectId;
    }


}
