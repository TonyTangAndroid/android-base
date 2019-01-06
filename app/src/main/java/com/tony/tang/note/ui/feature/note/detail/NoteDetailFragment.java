package com.tony.tang.note.ui.feature.note.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.evernote.android.state.State;
import com.tony.tang.note.app.App;
import com.tony.tang.note.app.CleanFragment;
import com.tony.tang.note.app.R;
import com.tony.tang.note.app.Status;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.presenter.NoteDetailPresenter;
import com.tony.tang.note.presenter.NoteDetailPresenter.NoteDetailView;
import com.tony.tang.note.ui.feature.note.edit.NoteEditActivity;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.BindsInstance;
import dagger.Subcomponent;

public class NoteDetailFragment extends CleanFragment implements NoteDetailView {


    private static final String EXTRA_OBJECT_ID = "extra_object_id";
    @Inject
    NoteDetailPresenter noteDetailPresenter;

    CheckBox cb_star;
    TextView tv_title;
    TextView tv_content;

    @State
    String objectId;
    //late initial
    private ProgressDialog progressDialog;

    public static NoteDetailFragment newInstance(String objectId) {

        Bundle args = new Bundle();
        args.putString(EXTRA_OBJECT_ID, objectId);
        NoteDetailFragment fragment = new NoteDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        objectId = Objects.requireNonNull(getArguments()).getString(EXTRA_OBJECT_ID);
        ((App) context.getApplicationContext()).appComponent()
                .noteDetailComponentBuilder()
                .noteDetailView(this)
                .build()
                .inject(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                edit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void edit() {
        startActivity(NoteEditActivity.constructIntent(requireActivity(), objectId));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note_detail;
    }

    @Override
    protected void bindView(View rootView) {
        cb_star = rootView.findViewById(R.id.cb_star);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_content = rootView.findViewById(R.id.tv_content);
        cb_star.setOnCheckedChangeListener((buttonView, isChecked) -> presenter().toggleChecked(data(isChecked)));
    }

    private NoteData data(boolean isChecked) {
        return NoteData.builder().objectId(objectId).status(isChecked ? Status.STAR : Status.DEFAULT).build();
    }


    @Override
    protected NoteDetailPresenter presenter() {
        return noteDetailPresenter;
    }

    @Override
    public void showNote(NoteEntity note) {
        tv_title.setText(note.title());
        tv_content.setText(note.content());
        cb_star.setChecked(note.status() == Status.STAR);
    }

    @Override
    public String getNoteObjectId() {
        return objectId;
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

    @Subcomponent
    public interface Component {

        void inject(NoteDetailFragment fragment);

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Builder noteDetailView(NoteDetailView noteDetailView);

            Component build();
        }
    }
}
