package com.jordifierro.androidbase.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.activity.base.CleanActivity;
import com.jordifierro.androidbase.presentation.view.fragment.NoteEditFragment;

public class NoteEditActivity extends CleanActivity implements NoteEditFragment.Listener {

    public static final String PARAM_NOTE_ID = "param_note_id";
    public static final int RESULT_NOTE_DELETED = RESULT_OK + 1;

    private String noteId;
    private NoteEditFragment noteEditFragment;

    public static Intent getCallingIntent(Context context, String noteId) {
        Intent callingIntent = new Intent(context, NoteEditActivity.class);
        callingIntent.putExtra(PARAM_NOTE_ID, noteId);
        return callingIntent;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.noteId = getIntent().getStringExtra(PARAM_NOTE_ID);
            if (this.noteEditFragment == null) this.noteEditFragment = new NoteEditFragment();
            addFragment(R.id.fragment_container, this.noteEditFragment);
        } else this.noteId = savedInstanceState.getString(PARAM_NOTE_ID);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(PARAM_NOTE_ID, this.noteId);
        }
        super.onSaveInstanceState(outState);
    }

    public String getNoteObjectId() {
        return this.noteId;
    }

    @Override
    public void onNoteDeleted() {
        setResult(RESULT_NOTE_DELETED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        this.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_delete) {
                    NoteEditActivity.this.callDeleteOnPresenter();
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    private void callDeleteOnPresenter() {
        this.noteEditFragment.getNoteEditPresenter().deleteNoteButtonPressed();
    }
}
