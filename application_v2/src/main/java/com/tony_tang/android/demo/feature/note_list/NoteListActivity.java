package com.tony_tang.android.demo.feature.note_list;

import android.os.Bundle;
import android.widget.ListView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.BaseActivity;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;

import java.util.List;

import javax.inject.Inject;

public class NoteListActivity extends BaseActivity implements NoteListView {

    @Inject
    NoteListPresenter noteListPresenter;
    @Inject
    NoteListAdapter adapter;


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        listView = (ListView) findViewById(R.id.list_view);
        adapter.setOnItemClickListener(note -> NoteListActivity.this.showNote(note.getObjectId()));
        listView.setAdapter(adapter);
        noteListPresenter.loadData();

    }

    public void showNote(String noteObjectId) {
        startActivity(NoteDetailActivity.getCallingIntent(this, noteObjectId));
    }

    @Override
    public void showNoteEntityList(List<NoteEntity> noteEntityList) {
        adapter.setNotes(noteEntityList);

    }
}
