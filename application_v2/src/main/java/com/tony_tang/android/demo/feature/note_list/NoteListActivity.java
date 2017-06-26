package com.tony_tang.android.demo.feature.note_list;

import android.os.Bundle;
import android.widget.ListView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.presenter.BasePresenter;
import com.jordifierro.androidbase.presentation.presenter.NoteListPresenter;
import com.jordifierro.androidbase.presentation.view.NoteListView;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteListActivity extends PresenterActivity implements NoteListView {

    @Inject
    NoteListPresenter noteListPresenter;
    @Inject
    NoteListAdapter adapter;

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    public void initUI() {
        adapter.setOnItemClickListener(note -> NoteListActivity.this.showNote(note.getObjectId()));
        listView.setAdapter(adapter);
    }

    protected int getLayoutId() {
        return R.layout.activity_note_list;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    public void showNote(String noteObjectId) {
        startActivity(NoteDetailActivity.getCallingIntent(this, noteObjectId));
    }

    @Override
    public void showNoteEntityList(List<NoteEntity> noteEntityList) {
        adapter.setNotes(noteEntityList);

    }

    @Override
    protected BasePresenter presenter() {
        return noteListPresenter;
    }
}
