package com.jordifierro.androidbase.presentation.view.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.presenter.BasePresenter;
import com.jordifierro.androidbase.presentation.presenter.NoteDetailPresenter;
import com.jordifierro.androidbase.presentation.view.NoteDetailView;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteDetailFragmentForPager extends CleanFragment implements NoteDetailView {

    @Inject
    NoteDetailPresenter noteDetailPresenter;

    @BindView(R.id.tv_title)
    TextView titleTV;
    @BindView(R.id.tv_content)
    TextView contentTV;
    private String noteObjectId;

    public static NoteDetailFragmentForPager newInstance(String noteObjectId) {

        Bundle args = new Bundle();
        args.putString("argNoteId", noteObjectId);
        NoteDetailFragmentForPager fragment = new NoteDetailFragmentForPager();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void callInjection() {
        this.getFragmentInjector().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteObjectId = getArguments().getString("argNoteId");
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_note_detail;
    }

    @Override
    protected BasePresenter presenter() {
        return this.noteDetailPresenter;
    }

    @Override
    public void showNote(NoteEntity note) {
        titleTV.setText(note.getTitle());
        contentTV.setText(note.getContent());
    }

    @Override
    public String getNoteObjectId() {
        return noteObjectId;
    }


}
