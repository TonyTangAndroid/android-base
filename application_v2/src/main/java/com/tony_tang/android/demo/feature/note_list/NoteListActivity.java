package com.tony_tang.android.demo.feature.note_list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.presenter.NoteListPresenter;
import com.jordifierro.androidbase.presentation.view.NoteListView;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.feature.common.CleanViewStatus;
import com.tony_tang.android.demo.feature.common.EndlessRecyclerOnScrollListenerTrial;
import com.tony_tang.android.demo.feature.common.LoadingStatus;
import com.tony_tang.android.demo.feature.note_creation.NoteCreateActivity;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteListActivity extends PresenterActivity implements
        NoteListView,
        EndlessRecyclerOnScrollListenerTrial.RecyclerViewScrollListener,
        NoteEntityListModelController.ItemClickListenerCallback,
        SwipeRefreshLayout.OnRefreshListener {


    @Inject
    NoteListPresenter noteListPresenter;
    @Inject
    NoteEntityListModelController controller;

    @BindView(R.id.rv_note_list)
    RecyclerView rvNoteList;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @LoadingStatus
    private int status;

    @Override
    public void initUI() {
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvNoteList.setLayoutManager(layoutManager);
        rvNoteList.addOnScrollListener(new EndlessRecyclerOnScrollListenerTrial(layoutManager, this));
        rvNoteList.setAdapter(controller.getAdapter());
        rvNoteList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        loadData();
    }

    private void loadData() {
        if (isIdle()) {
            status = CleanViewStatus.LOADING;
            presenter().loadData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private boolean isIdle() {
        return status == CleanViewStatus.IDLE;
    }

    protected int getLayoutId() {
        return R.layout.activity_note_list;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    public void showNote(String noteObjectId) {
        startActivity(NoteDetailActivity.getCallingIntent(this, noteObjectId));
    }

    @Override
    public void showNoteEntityList(List<NoteEntity> noteEntityList) {
        controller.bindDataListToUI(noteEntityList);
        swipeRefreshLayout.setRefreshing(false);
        status = CleanViewStatus.IDLE;
    }

    @Override
    public void showProcessing() {
        super.showLoader();
    }

    @Override
    public void showLoader() {
        controller.setData(null);
    }

    @Override
    protected NoteListPresenter presenter() {
        return noteListPresenter;
    }

    @Override
    public void onItemClicked(View view, NoteEntity entity) {
        showNote(entity.getObjectId());
    }

    @Override
    public void onFooterClicked() {

    }

    @Override
    public void retry() {
        presenter().loadData();
    }

    @Override
    public void bottomViewClicked() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_create, menu);
        this.getToolbar().setOnMenuItemClickListener(this::onMenuItemClicked);
        return true;
    }

    private boolean onMenuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_create:
                NoteListActivity.this.navigateCreate();
                return true;
            case R.id.item_generate:
                presenter().generateNotes();
                break;
            case R.id.item_clear:
                presenter().clearNotes();
                break;
        }
        return false;
    }

    private void navigateCreate() {
        startActivity(NoteCreateActivity.constructIntent(this));
    }

    @Override
    public void onRefresh() {
        if (isIdle()) {
            status = CleanViewStatus.REFRESHING;
            presenter().refreshData();
        }

    }

    @Override
    public void onBottomToBeReached() {
        if (isIdle()) {
            status = CleanViewStatus.LOADING_MORE;
            presenter().loadMoreData();
        }
    }

    @Override
    public void handleError(Throwable error) {
        super.handleError(error);
        status = CleanViewStatus.IDLE;
    }
}
