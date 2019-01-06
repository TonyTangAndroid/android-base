package com.tony.tang.note.ui.feature.note.edit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.evernote.android.state.State;
import com.tony.tang.note.app.App;
import com.tony.tang.note.app.CleanFragment;
import com.tony.tang.note.app.R;
import com.tony.tang.note.app.Status;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.presenter.NoteEditPresenter;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Subcomponent;

public class NoteEditFragment extends CleanFragment implements NoteEditPresenter.NoteEditView {

    private static final String EXTRA_OBJECT_ID = "extra_object_id";
    @Inject
    NoteEditPresenter noteCreatePresenter;
    CheckBox cb_star;
    EditText et_title;
    EditText et_content;
    @State
    String objectId;
    private ProgressDialog progressDialog;

    public static NoteEditFragment newInstance(String objectId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_OBJECT_ID, objectId);
        NoteEditFragment fragment = new NoteEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        objectId = Objects.requireNonNull(getArguments()).getString(EXTRA_OBJECT_ID);
        ((App) context.getApplicationContext()).appComponent()
                .noteEditComponentBuilder().fragment(this).build().inject(this);
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
        et_title = rootView.findViewById(R.id.et_title);
        et_content = rootView.findViewById(R.id.et_content);
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

    @Override
    protected NoteEditPresenter presenter() {
        return this.noteCreatePresenter;
    }

    public void createButtonPressed() {
        this.noteCreatePresenter.createButtonPressed(data());
    }

    private NoteData data() {
        return NoteData.builder()
                .objectId(objectId)
                .title(et_title.getText().toString())
                .content(et_content.getText().toString())
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
    public void exit() {
        requireActivity().setResult(Activity.RESULT_OK);
        requireActivity().finish();
    }

    @Override
    public String objectId() {
        return objectId;
    }

    @Override
    public void bindData(NoteEntity noteEntity) {
        cb_star.setChecked(noteEntity.status() == Status.STAR);
        et_title.setText(noteEntity.title());
        et_content.setText(noteEntity.content());
    }

    @Subcomponent(modules = {Component.Module.class})
    public interface Component {

        void inject(NoteEditFragment fragment);

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Builder fragment(NoteEditFragment fragment);

            Component build();
        }

        @dagger.Module
        abstract class Module {

            @Binds
            abstract NoteEditPresenter.NoteEditView bind(NoteEditFragment fragment);
        }
    }
}
