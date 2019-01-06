package com.tony.tang.note.ui.feature.note.list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tony.tang.note.app.ActivityScope;
import com.tony.tang.note.app.App;
import com.tony.tang.note.app.AppComponent;
import com.tony.tang.note.app.R;
import com.tony.tang.note.app.Status;
import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.ui.feature.note.creation.NoteCreateActivity;
import com.tony.tang.note.ui.feature.note.detail.NoteDetailActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.Provides;

public class NoteListPagingActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        NoteBeanViewHolder.Listener,
        NotePagingListPresenter.NotePagingUI {

    @Inject
    NotePagingListPresenter notePagingListPresenter;
    private RecyclerView rv_entity_list;
    private NoteBeanPagedListAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.item_show_paging);
        item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_create:
                startActivity(NoteCreateActivity.constructIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        setContentView(R.layout.activity_entity_list);
        bindView();
        bindData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notePagingListPresenter.destroy();
    }

    private void inject() {
        DaggerNoteListPagingActivity_Component.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((App) getApplication()).appComponent())
                .build().inject(this);
    }

    private void bindData() {
        rv_entity_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteBeanPagedListAdapter(new NoteBeanDiffUtilItemCallback(), this);
        rv_entity_list.setAdapter(adapter);
        new NoteBeanAndroidViewModel(getApplication()).get().observe(this, this::onDataReady);
    }

    private void onDataReady(PagedList<NoteBean> noteBeans) {
        System.out.println("new page size :" + noteBeans.size());
        Toast.makeText(NoteListPagingActivity.this, "total:" + noteBeans.size(), Toast.LENGTH_SHORT).show();
        adapter.submitList(noteBeans);
    }


    private void bindView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rv_entity_list = findViewById(R.id.rv_entity_list);
        SwipeRefreshLayout swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {

    }

    @Override
    public void delete(NoteBean notebean) {
        notePagingListPresenter.delete(notebean.objectId);
    }

    @Override
    public void toggleTitle(NoteBean notebean) {

    }

    @Override
    public void toggleOrder(NoteBean notebean) {

    }

    @Override
    public void toggleStatus(boolean isChecked, String objectId) {
        notePagingListPresenter.toggleStatus(data(isChecked, objectId));
    }

    @Override
    public void view(NoteBean item) {
        startActivity(NoteDetailActivity.constructIntent(this, item.objectId));
    }

    private NoteData data(boolean isChecked, String objectId) {
        return NoteData.builder()
                .status(isChecked ? Status.STAR : Status.DEFAULT)
                .objectId(objectId).build();
    }

    @Override
    public void handleError(Throwable e) {

    }

    @ActivityScope
    @dagger.Component(modules = NoteListPagingActivity.ActivityModule.class,
            dependencies = AppComponent.class)
    interface Component {
        void inject(NoteListPagingActivity activity);
    }

    @dagger.Module
    static class ActivityModule {

        private final NoteListPagingActivity activity;

        ActivityModule(NoteListPagingActivity activity) {
            this.activity = activity;
        }

        @ActivityScope
        @Provides
        NotePagingListPresenter.NotePagingUI ui() {
            return activity;
        }
    }


}
