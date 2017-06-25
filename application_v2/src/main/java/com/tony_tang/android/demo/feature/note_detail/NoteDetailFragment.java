package com.tony_tang.android.demo.feature.note_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.BaseFragment;

import javax.inject.Inject;

public class NoteDetailFragment extends BaseFragment implements NoteDetailView {


    @Inject
    NoteDetailPresenter noteDetailPresenter;
    TextView titleTV;
    TextView contentTV;

    public static NoteDetailFragment newInstance() {

        return new NoteDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false);
        titleTV = (TextView) rootView.findViewById(R.id.tv_title);
        contentTV = (TextView) rootView.findViewById(R.id.tv_content);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteDetailPresenter.loadData();
    }

    @Override
    public void onNoteEntityLoaded(NoteEntity note) {
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
