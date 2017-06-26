package com.tony_tang.android.demo.feature.note_detail;

import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.presenter.BasePresenter;
import com.jordifierro.androidbase.presentation.presenter.NoteDetailPresenter;
import com.jordifierro.androidbase.presentation.view.NoteDetailView;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteDetailFragment extends CleanFragment implements NoteDetailView {


    @Inject
    NoteDetailPresenter noteDetailPresenter;

    @BindView(R.id.tv_title)
    TextView titleTV;
    @BindView(R.id.tv_content)
    TextView contentTV;

    public static NoteDetailFragment newInstance() {

        return new NoteDetailFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_note_detail;
    }


    @Override
    protected BasePresenter presenter() {
        return noteDetailPresenter;
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
