package com.jordifierro.androidbase.presentation.view.fragment;

import android.widget.ListView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.presenter.NotesPresenter;
import com.jordifierro.androidbase.presentation.view.NotesView;
import com.jordifierro.androidbase.presentation.view.adapter.NotesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class NotesFragment extends CleanFragment implements NotesView {

    @Inject
    NotesPresenter notesPresenter;

    @Bind(R.id.listview)
    ListView listView;

    @Inject
    NotesAdapter adapter;

    @Override
    protected void callInjection() {
        this.getFragmentInjector().inject(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_notes;
    }

    @Override
    protected NotesPresenter presenter() {
        return this.notesPresenter;
    }

    @Override
    public void showNotes(List<NoteEntity> notes) {
        adapter.setNotes(notes);
    }

    @OnClick(R.id.btn_create_new_note)
    public void createNewNoteButtonPressed() {
        ((Listener) getActivity()).displayNoteCreator();
    }

    public void showNote(String noteObjectId) {
        ((Listener) getActivity()).showNote(noteObjectId);
    }

    @Override
    public void initUI() {
        adapter.setOnItemClickListener(note -> NotesFragment.this.showNote(note.getObjectId()));
        listView.setAdapter(adapter);
    }

    public interface Listener {
        void displayNoteCreator();

        void showNote(String noteObjectId);
    }

}
