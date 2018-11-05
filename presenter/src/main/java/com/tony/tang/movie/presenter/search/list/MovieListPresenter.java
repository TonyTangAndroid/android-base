package com.tony.tang.movie.presenter.search.list;

import com.tony.tang.note.domain.entity.Movie;
import com.tony.tang.note.domain.interactor.note.SearchMovieUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;


public class MovieListPresenter {

    private final MovieListView movieListView;
    private final SearchMovieUseCase getNoteUseCase;

    @Inject
    public MovieListPresenter(MovieListView movieListView, SearchMovieUseCase searchMovieUseCase) {
        this.movieListView = movieListView;
        this.getNoteUseCase = searchMovieUseCase;
    }

    public void resume() {
        this.getNoteUseCase.execute(new MovieListSubscriber());
    }

    public void pause() {
        getNoteUseCase.unsubscribe();
    }

    public interface MovieListView {
        void bindData(List<Movie> list);

        void handleError(Throwable throwable);
    }

    protected class MovieListSubscriber extends DisposableObserver<List<Movie>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            movieListView.handleError(e);
        }

        @Override
        public void onNext(List<Movie> list) {
            movieListView.bindData(list);
        }
    }
}
