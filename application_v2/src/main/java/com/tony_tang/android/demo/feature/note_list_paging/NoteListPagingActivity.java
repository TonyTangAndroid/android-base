package com.tony_tang.android.demo.feature.note_list_paging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jordifierro.androidbase.data.repository.NoteBean;
import com.tony_tang.android.demo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import hugo.weaving.DebugLog;

@DebugLog
public class NoteListPagingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    RecyclerView rv_entity_list;
    SwipeRefreshLayout swipe_refresh_layout;
    private NoteBeanPagedListAdapter adapter;

    public static Intent constructIntent(Activity activity) {
        return new Intent(activity, NoteListPagingActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_list);
        bindView();
        bindData();
    }

    private void bindData() {
        rv_entity_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteBeanPagedListAdapter(new NoteBeanDiffUtilItemCallback());
        rv_entity_list.setAdapter(adapter);
        new NoteBeanAndroidViewModel(getApplication()).get().observe(this, new Observer<PagedList<NoteBean>>() {
            @Override
            public void onChanged(PagedList<NoteBean> noteBeans) {
                adapter.submitList(noteBeans);
            }
        });
    }


    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        rv_entity_list = findViewById(R.id.rv_entity_list);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {

    }
}
