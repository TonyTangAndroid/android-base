package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.entity.Movie;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.UseCase;
import com.tony.tang.note.domain.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SearchMovieUseCase extends UseCase<List<Movie>> {

    private final Observable<String> keywordStreamObservable;
    private final MovieRepository movieListRepository;

    @Inject
    public SearchMovieUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                              Observable<String> keywordStreamObservable,
                              MovieRepository movieListRepository) {
        super(threadExecutor, UIThread);
        this.movieListRepository = movieListRepository;
        this.keywordStreamObservable = keywordStreamObservable;
    }


    @Override
    protected Observable<List<Movie>> build() {
        return keywordStreamObservable.flatMap(this::search);
    }

    private Observable<List<Movie>> search(String keyword) {
        return this.movieListRepository.list(keyword).toObservable();

    }

}
