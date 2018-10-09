package com.tony_tang.android.demo.feature.note_creation;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;

import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanFragment;
import com.tony_tang.android.demo.presentation.presenter.NoteCreatePresenter;
import com.tony_tang.android.demo.presentation.view.NoteCreateView;

import javax.inject.Inject;

public class NoteCreateFragment extends CleanFragment implements NoteCreateView {

    @Inject
    NoteCreatePresenter noteCreatePresenter;

    EditText etTitle;
    EditText etContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note_create_edit;
    }

    @Override
    protected void bindView(View rootView) {
        etTitle = rootView.findViewById(R.id.et_title);
        etContent = rootView.findViewById(R.id.et_content);
        rootView.findViewById(R.id.btn_submit).setOnClickListener(view -> createButtonPressed());

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initUI() {
        etTitle.setText(getText(R.string.edit_text_title).toString() + System.currentTimeMillis());
        etContent.setText(getText(R.string.edit_text_content).toString() + System.currentTimeMillis());
    }

    @Override
    protected NoteCreatePresenter presenter() {
        return this.noteCreatePresenter;
    }

    public void createButtonPressed() {
        this.noteCreatePresenter.createButtonPressed(etTitle.getText().toString(), etContent.getText().toString());
    }

}
