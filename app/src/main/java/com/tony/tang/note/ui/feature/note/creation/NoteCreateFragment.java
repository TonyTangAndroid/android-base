package com.tony.tang.note.ui.feature.note.creation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.tony.tang.note.app.CleanFragment;
import com.tony.tang.note.app.DemoApplication;
import com.tony.tang.note.app.R;
import com.tony.tang.note.presenter.NoteCreatePresenter;
import com.tony.tang.note.presenter.NoteCreatePresenter.NoteCreateView;

import javax.inject.Inject;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Subcomponent;

public class NoteCreateFragment extends CleanFragment implements NoteCreateView {

    @Inject
    NoteCreatePresenter noteCreatePresenter;
    EditText etTitle;
    EditText etContent;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DemoApplication) context.getApplicationContext()).applicationComponent()
                .noteCreationComponentBuilder().fragment(this).build().inject(this);
    }

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
            this.progressDialog = null;
        }
    }

    @Subcomponent(modules = {Component.NoteCreateModule.class})
    public interface Component {

        void inject(NoteCreateFragment fragment);

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Builder fragment(NoteCreateFragment fragment);

            Component build();
        }

        @Module
        abstract class NoteCreateModule {

            @Binds
            abstract NoteCreateView bind(NoteCreateFragment noteCreateFragment);
        }
    }
}
