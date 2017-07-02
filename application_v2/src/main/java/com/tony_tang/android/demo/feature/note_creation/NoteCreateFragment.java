package com.tony_tang.android.demo.feature.note_creation;

import android.annotation.SuppressLint;
import android.widget.EditText;

import com.tony_tang.android.demo.presentation.presenter.NoteCreatePresenter;
import com.tony_tang.android.demo.presentation.view.NoteCreateView;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.CleanFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class NoteCreateFragment extends CleanFragment implements NoteCreateView {

    @Inject
    NoteCreatePresenter noteCreatePresenter;

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note_create_edit;
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

    @OnClick(R.id.btn_submit)
    public void createButtonPressed() {
        this.noteCreatePresenter.createButtonPressed(etTitle.getText().toString(), etContent.getText().toString());
    }

}
