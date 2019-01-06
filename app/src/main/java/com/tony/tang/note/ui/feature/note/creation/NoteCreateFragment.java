package com.tony.tang.note.ui.feature.note.creation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tony.tang.note.app.App;
import com.tony.tang.note.app.CleanFragment;
import com.tony.tang.note.app.R;
import com.tony.tang.note.app.Status;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.presenter.NoteCreatePresenter;
import com.tony.tang.note.presenter.NoteCreatePresenter.NoteCreateView;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Subcomponent;

public class NoteCreateFragment extends CleanFragment implements NoteCreateView {

    private static final String EXTRA_OBJECT_ID = "extra_object_id";
    @Inject
    NoteCreatePresenter noteCreatePresenter;
    CheckBox cb_star;
    EditText etTitle;
    EditText etContent;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).appComponent()
                .noteCreationComponentBuilder().fragment(this).build().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note_create_edit;
    }

    @Override
    protected void bindView(View rootView) {
        cb_star = rootView.findViewById(R.id.cb_star);
        etTitle = rootView.findViewById(R.id.et_title);
        etContent = rootView.findViewById(R.id.et_content);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_create:
                createButtonPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        this.noteCreatePresenter.createButtonPressed(data());
    }

    private NoteData data() {
        return NoteData.builder()
                .title(etTitle.getText().toString())
                .content(etContent.getText().toString())
                .status(cb_star.isChecked() ? Status.STAR : Status.DEFAULT)
                .build();
    }

    @Override
    public void showLoader() {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(requireActivity());
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

    @Override
    public void exit(String objectId) {
        requireActivity().setResult(Activity.RESULT_OK,
                new Intent().putExtra(EXTRA_OBJECT_ID, objectId));
        requireActivity().finish();
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
