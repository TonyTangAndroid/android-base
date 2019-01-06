package com.tony.tang.note.ui.feature.note.creation;

import android.app.Activity;
import android.content.Intent;

import com.tony.tang.note.app.BaseActivity;


public class NoteCreateActivity extends BaseActivity {

    public static Intent constructIntent(Activity activity) {
        return new Intent(activity, NoteCreateActivity.class);
    }

    @Override
    protected void onCreate() {
        bindFragment(new NoteCreateFragment());
    }
}
