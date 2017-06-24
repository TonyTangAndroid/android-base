package com.jordifierro.androidbase.application.view.fragment;

import android.widget.ListView;

import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.application.view.adapter.NotesAdapter;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.presenter.NotesPresenter;
import com.jordifierro.androidbase.presentation.view.NotesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class NotesFragment extends CleanFragment implements NotesView {

    @Inject
    NotesPresenter notesPresenter;

    @BindView(R.id.listview)
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
