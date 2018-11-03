package com.tony_tang.android.demo.feature.note_list.fragment;

import android.os.Bundle;

import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanActivity;

public class NoteListFragmentActivity extends CleanActivity {

    @Override
    protected void bindView() {

    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, NoteListFragment.newInstance())
                    .commit();
        }
    }


}