package com.jordifierro.androidbase.application.view.fragment;

import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.presentation.presenter.NoteDetailPresenter;
import com.jordifierro.androidbase.presentation.view.NoteDetailView;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteDetailFragment extends CleanFragment implements NoteDetailView {

    @Inject
    NoteDetailPresenter noteDetailPresenter;

    @BindView(R.id.tv_title)
    TextView titleTV;
    @BindView(R.id.tv_content)
    TextView contentTV;

    @Override
    protected void callInjection() {
        this.getFragmentInjector().inject(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_note_detail;
    }

    @Override
    protected NoteDetailPresenter presenter() {
        return this.noteDetailPresenter;
    }

    @Override
    public void showNote(NoteEntity note) {
        titleTV.setText(note.getTitle());
        contentTV.setText(note.getContent());
    }

    @Override
    public String getNoteObjectId() {
        return ((Listener) getActivity()).getNoteObjectId();
    }

    public interface Listener {
        String getNoteObjectId();
    }

}
