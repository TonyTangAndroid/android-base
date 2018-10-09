package com.tony_tang.android.demo.feature.note_list_paging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jordifierro.androidbase.data.repository.NoteBean;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.feature.note_creation.NoteCreateActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NoteListPagingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    RecyclerView rv_entity_list;
    SwipeRefreshLayout swipe_refresh_layout;
    private NoteBeanPagedListAdapter adapter;

    public static Intent constructIntent(Activity activity) {
        return new Intent(activity, NoteListPagingActivity.class);
    }


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
        setContentView(R.layout.activity_entity_list);
        bindView();
        bindData();
    }

    private void bindData() {
        rv_entity_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteBeanPagedListAdapter(new NoteBeanDiffUtilItemCallback());
        rv_entity_list.setAdapter(adapter);
        new NoteBeanAndroidViewModel(getApplication()).get().observe(this, this::onDataReady);
    }

    private void onDataReady(PagedList<NoteBean> noteBeans) {
        System.out.println("new page size :" + noteBeans.size());
        Toast.makeText(NoteListPagingActivity.this, "total size:" + noteBeans.size(), Toast.LENGTH_SHORT).show();
        adapter.submitList(noteBeans);
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
