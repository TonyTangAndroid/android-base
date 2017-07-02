package com.tony_tang.android.demo.feature.note_list;

import android.view.View;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.presenter.NoteListPresenter;
import com.jordifierro.androidbase.presentation.presenter.base.BaseListPresenter;
import com.tony_tang.android.demo.common.base.BaseModelListActivity;
import com.tony_tang.android.demo.feature.common.BaseModelController;

import javax.inject.Inject;

public class NoteListActivity extends BaseModelListActivity {

    @Inject
    NoteListPresenter noteListPresenter;

    @Inject
    NoteEntityListModelController controller;

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
}
