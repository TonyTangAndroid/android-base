package com.tony_tang.android.demo.feature.note_list.fragment;

import android.view.View;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.presentation.presenter.NoteListPresenter;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;
import com.tony_tang.android.demo.common.base.BaseModelListFragment;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;
import com.tony_tang.android.demo.feature.note_list.NoteEntityListModelController;

import javax.inject.Inject;

public class NoteListFragment extends BaseModelListFragment {


    @Inject
    NoteListPresenter noteListPresenter;
    @Inject
    NoteEntityListModelController controller;

    public static NoteListFragment newInstance() {

        return new NoteListFragment();
    }

    @Override
    protected BaseModelController controller() {
        return controller;
    }

    @Override
    protected BaseListPresenter presenter() {
        return noteListPresenter;
    }

    @Override
    public void onItemClicked(View view, Object entity) {
        showNote(((NoteEntity) entity).getObjectId());
    }

    public void showNote(String noteObjectId) {
        startActivity(NoteDetailActivity.getCallingIntent(getActivity(), noteObjectId));
    }
}
