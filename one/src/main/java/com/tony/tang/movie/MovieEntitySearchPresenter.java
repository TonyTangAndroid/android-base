package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;


public class MovieEntitySearchPresenter {

    private final MovieListView movieListView;
    private final SearchMovieEntityUseCase getNoteUseCase;

    @Inject
    public MovieEntitySearchPresenter(MovieListView movieListView, SearchMovieEntityUseCase searchMovieUseCase) {
        this.movieListView = movieListView;
        this.getNoteUseCase = searchMovieUseCase;
    }

    public void resume() {
        observe();
    }

    private void observe() {
        this.getNoteUseCase.execute(new MovieListSubscriber());
    }

    public void pause() {
        getNoteUseCase.unsubscribe();
    }

    public interface MovieListView {
        void bindData(List<MovieEntity> list);

        void handleError(Throwable throwable);
    }

    protected class MovieListSubscriber extends DisposableObserver<List<MovieEntity>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            movieListView.handleError(e);
        }

        @Override
        public void onNext(List<MovieEntity> list) {
            movieListView.bindData(list);
        }
    }
}
