package com.tony.tang.movie.ui.feature.list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tony.tang.movie.ActivityScope;
import com.tony.tang.movie.R;
import com.tony.tang.movie.app.App;
import com.tony.tang.movie.app.AppComponent;
import com.tony.tang.movie.domain.MovieEntity;
import com.tony.tang.movie.ui.feature.detail.MovieEntityDetailActivity;
import com.tony.tang.movie.ui.feature.search.MovieEntitySearchActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Provides;

public class MovieEntityListPagingActivity extends AppCompatActivity
        implements MovieEntityPagingViewHolder.Listener,
        MovieEntityPagingPresenter.UI {

    @Inject
    MovieEntityPagingPresenter notePagingListPresenter;
    private RecyclerView recycler_view;
    private MovieEntityPagedListAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_paging_list, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                showSearchActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSearchActivity() {
        startActivity(MovieEntitySearchActivity.constructIntent(this));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        setContentView(R.layout.activity_paging_list);
        bindView();
        bindData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notePagingListPresenter.destroy();
    }

    private void inject() {
        DaggerMovieEntityListPagingActivity_Component.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((App) getApplication()).appComponent())
                .build().inject(this);
    }

    private void bindData() {
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieEntityPagedListAdapter(new MovieEntityDiffUtilItemCallback(), this);
        recycler_view.setAdapter(adapter);
        new MovieEntityAndroidViewModel(getApplication()).get().observe(this, this::onDataReady);
    }

    private void onDataReady(PagedList<MovieEntity> noteBeans) {
        System.out.println("new getPage size :" + noteBeans.size());
        Toast.makeText(MovieEntityListPagingActivity.this, "total:" + noteBeans.size(), Toast.LENGTH_SHORT).show();
        adapter.submitList(noteBeans);
        if (noteBeans.size() == 0) {
            showSearchActivity();
        }
    }


    private void bindView() {
        recycler_view = findViewById(R.id.recycler_view);
    }

    @Override
    public void delete(MovieEntity noteEntity) {
        notePagingListPresenter.delete(noteEntity.getId());
    }

    @Override
    public void toggleTitle(MovieEntity noteEntity) {
        startActivity(MovieEntityDetailActivity.constructIntent(this, noteEntity.getId()));
    }

    @Override
    public void toggleOrder(MovieEntity noteEntity) {

    }

    @Override
    public void handleError(Throwable e) {

    }

    @ActivityScope
    @dagger.Component(modules = MovieEntityListPagingActivity.ActivityModule.class,
            dependencies = AppComponent.class)
    interface Component {
        void inject(MovieEntityListPagingActivity activity);
    }

    @dagger.Module
    static class ActivityModule {

        private final MovieEntityListPagingActivity activity;

        ActivityModule(MovieEntityListPagingActivity activity) {
            this.activity = activity;
        }

        @ActivityScope
        @Provides
        MovieEntityPagingPresenter.UI ui() {
            return activity;
        }
    }


}
