package com.tony_tang.android.demo.feature.note_list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.base.BaseModelListActivity;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.feature.note_creation.NoteCreateActivity;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;
import com.tony_tang.android.demo.feature.note_list_paging.NoteListPagingActivity;
import com.tony_tang.android.demo.presentation.presenter.NoteListPresenter;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;

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

    public void showNote(String noteObjectId) {
        startActivity(NoteDetailActivity.getCallingIntent(this, noteObjectId));
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_create:
                startActivity(NoteCreateActivity.constructIntent(this));
                return true;
            case R.id.item_show_paging:
                startActivity(NoteListPagingActivity.constructIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
