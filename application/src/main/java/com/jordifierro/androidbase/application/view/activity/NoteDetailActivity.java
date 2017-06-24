package com.jordifierro.androidbase.application.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.application.view.activity.base.CleanActivity;
import com.jordifierro.androidbase.application.view.fragment.NoteDetailFragment;

public class NoteDetailActivity extends CleanActivity implements NoteDetailFragment.Listener {

    public static final String PARAM_NOTE_ID = "param_note_id";
    private static final int REQUEST_NOTE_EDIT = 1;

    private String noteObjectId;

    public static Intent getCallingIntent(Context context, String noteObjectId) {
        Intent callingIntent = new Intent(context, NoteDetailActivity.class);
        callingIntent.putExtra(PARAM_NOTE_ID, noteObjectId);
        return callingIntent;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.noteObjectId = getIntent().getStringExtra(PARAM_NOTE_ID);
            addFragment(R.id.fragment_container, new NoteDetailFragment());
        } else this.noteObjectId = savedInstanceState.getString(PARAM_NOTE_ID);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        this.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_edit) {
                    NoteDetailActivity.this.navigateToEdit();
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    public void navigateToEdit() {
        startActivityForResult(NoteEditActivity.getCallingIntent(this, this.getNoteObjectId()), REQUEST_NOTE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_NOTE_EDIT && resultCode == NoteEditActivity.RESULT_NOTE_DELETED) {
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
