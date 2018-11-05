package com.tony.tang.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Provides;
import io.reactivex.Observable;
import timber.log.Timber;


public class MovieEntitySearchActivity extends AppCompatActivity implements MovieEntitySearchPresenter.MovieListView {

    @Inject
    MovieEntitySearchPresenter presenter;
    private EditText inputSearchText;
    private RecyclerView recyclerView;
    private FrameLayout topEmptyView;
    private ProgressBar progressBar;
    private TextView tvEmptyViewHint;
    private MovieEntityAdapter movieAdapter;

    public static Intent constructIntent(Activity activity) {
        return new Intent(activity, MovieEntitySearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindView();
        initUI();
        inject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    private void bindView() {
        inputSearchText = findViewById(R.id.edit_text);
        recyclerView = findViewById(R.id.recycler_view);
        topEmptyView = findViewById(R.id.top_empty_view);
        progressBar = findViewById(R.id.progress_bar);
        tvEmptyViewHint = findViewById(R.id.tv_empty_hint);

    }


    public void bindData(List<MovieEntity> list) {
        if (list != null) {
            if (list.size() > 0) {
                movieAdapter.updateDataSet(list);
                hideEmptyView();
            } else {
                showEmptyView(getString(R.string.no_result));
            }
        } else {
            showEmptyView(getString(R.string.no_result));
        }

    }

    private void hideEmptyView() {
        topEmptyView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    private void showEmptyView(String hint) {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        topEmptyView.setVisibility(View.VISIBLE);
        tvEmptyViewHint.setText(hint);
        tvEmptyViewHint.setVisibility(View.VISIBLE);
    }


    private void showLoadingView() {
        //TODO
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        topEmptyView.setVisibility(View.VISIBLE);
        tvEmptyViewHint.setVisibility(View.INVISIBLE);
    }


    public void initUI() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        movieAdapter = new MovieEntityAdapter(this);
        recyclerView.setAdapter(movieAdapter);


    }

    private void inject() {
        DaggerMovieEntitySearchActivity_Component.builder()
                .module(new Module(this, streaming()))
                .appComponent(((App) getApplication()).appComponent())
                .build().inject(this);
    }

    private Observable<String> streaming() {
        return RxTextView.textChangeEvents(inputSearchText)
                .debounce(800, TimeUnit.MILLISECONDS)
                .map(this::toKeyword)
                .filter(this::notNull)
                .distinctUntilChanged();
    }

    private boolean notNull(String keyWord) {
        return keyWord.length() > 0;
    }

    private String toKeyword(TextViewTextChangeEvent textViewTextChangeEvent) {
        return textViewTextChangeEvent.getText().toString();
    }

    @Override
    public void handleError(Throwable throwable) {
        showEmptyView(getString(R.string.error) + throwable.getMessage());
        Timber.e(throwable);
    }


    @ActivityScope
    @dagger.Component(modules = Module.class,
            dependencies = AppComponent.class)
    interface Component {
        void inject(MovieEntitySearchActivity activity);
    }

    @dagger.Module
    static class Module {

        private final MovieEntitySearchActivity fragment;
        private final Observable<String> keywordStreamingObservable;

        Module(MovieEntitySearchActivity fragment, Observable<String> keywordStreamingObservable) {
            this.fragment = fragment;
            this.keywordStreamingObservable = keywordStreamingObservable;
        }

        @ActivityScope
        @Provides
        MovieEntitySearchPresenter.MovieListView ui() {
            return fragment;
        }

        @ActivityScope
        @Provides
        Observable<String> keywordStreamingObservable() {
            return keywordStreamingObservable;
        }
    }

}