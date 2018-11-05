package com.tony.tang.movie.ui.feature.search.list;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.tony.tang.movie.presenter.search.list.MovieListPresenter;
import com.tony.tang.note.app.ActivityScope;
import com.tony.tang.note.app.DemoApplication;
import com.tony.tang.note.app.DemoApplicationComponent;
import com.tony.tang.note.app.R;
import com.tony.tang.note.domain.entity.Movie;

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


public class MovieSearchActivity extends AppCompatActivity implements MovieListPresenter.MovieListView {

    @Inject
    MovieListPresenter presenter;
    private EditText inputSearchText;
    private RecyclerView recyclerView;
    private FrameLayout topEmptyView;
    private ProgressBar progressBar;
    private TextView tvEmptyViewHint;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
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


    public void bindData(List<Movie> list) {
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
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        topEmptyView.setVisibility(View.VISIBLE);
        tvEmptyViewHint.setVisibility(View.INVISIBLE);
    }


    public void initUI() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);


    }

    private void inject() {
        DaggerMovieSearchActivity_Component.builder()
                .module(new Module(this, streaming()))
                .demoApplicationComponent(((DemoApplication) getApplication()).applicationComponent())
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
        showEmptyView(getString(R.string.network_error));
        Timber.e(throwable);
    }


    @ActivityScope
    @dagger.Component(modules = Module.class,
            dependencies = DemoApplicationComponent.class)
    interface Component {
        void inject(MovieSearchActivity activity);
    }

    @dagger.Module
    static class Module {

        private final MovieSearchActivity fragment;
        private final Observable<String> keywordStreamingObservable;

        Module(MovieSearchActivity fragment, Observable<String> keywordStreamingObservable) {
            this.fragment = fragment;
            this.keywordStreamingObservable = keywordStreamingObservable;
        }

        @ActivityScope
        @Provides
        MovieListPresenter.MovieListView ui() {
            return fragment;
        }

        @ActivityScope
        @Provides
        Observable<String> keywordStreamingObservable() {
            return keywordStreamingObservable;
        }
    }

}