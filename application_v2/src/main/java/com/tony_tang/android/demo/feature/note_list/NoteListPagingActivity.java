package com.tony_tang.android.demo.feature.note_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.common.base.BaseModelListActivity;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;
import com.tony_tang.android.demo.presentation.presenter.NoteListPresenter;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;

import javax.inject.Inject;

public class NoteListPagingActivity extends BaseModelListActivity {

    @Inject
    NoteListPresenter noteListPresenter;

    @Inject
    NoteEntityListModelController controller;

    public static Intent constructIntent(Activity activity) {
        return new Intent(activity, NoteListPagingActivity.class);
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
        startActivity(NoteDetailActivity.getCallingIntent(this, noteObjectId));
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

}
