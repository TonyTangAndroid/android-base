package com.tony.tang.note.ui.feature.note.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tony.tang.note.app.CleanFragment;
import com.tony.tang.note.app.DemoApplication;
import com.tony.tang.note.app.R;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.presenter.BasePresenter;
import com.tony.tang.note.presenter.NoteDetailPresenter;

import javax.inject.Inject;

public class NoteDetailFragment extends CleanFragment implements NoteDetailPresenter.NoteDetailView {


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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DemoApplication) context.getApplicationContext()).applicationComponent()
                .noteDetailSubComponentBuilder().fragment(this).build().inject(this);
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
