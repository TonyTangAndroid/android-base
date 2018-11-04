package com.tony_tang.android.demo.feature.note_detail;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanFragment;
import com.tony_tang.android.demo.presentation.presenter.NoteDetailPresenter;
import com.tony_tang.android.demo.presentation.presenter.base.BasePresenter;
import com.tony_tang.android.demo.presentation.view.NoteDetailView;

import javax.inject.Inject;

public class NoteDetailFragment extends CleanFragment implements NoteDetailView {


    @Inject
    NoteDetailPresenter noteDetailPresenter;

    TextView titleTV;
    TextView contentTV;

    //late initial
    private ProgressDialog progressDialog;

    public static NoteDetailFragment newInstance() {

        return new NoteDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note_detail;
    }

    @Override
    protected void bindView(View rootView) {
        titleTV = rootView.findViewById(R.id.tv_title);
        contentTV = rootView.findViewById(R.id.tv_content);
    }


    @Override
    protected BasePresenter presenter() {
        return noteDetailPresenter;
    }

    @Override
    public void showNote(NoteEntity note) {
        titleTV.setText(note.title());
        contentTV.setText(note.content());
    }

    @Override
    public String getNoteObjectId() {
        return ((Listener) getActivity()).getNoteObjectId();
    }

    @Override
    public void showLoader() {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(getActivity());
        }
        this.progressDialog.show();
    }

    @Override
    public void hideLoader() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    public interface Listener {
        String getNoteObjectId();
    }

}
