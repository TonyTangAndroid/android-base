package com.tony.tang.movie.domain;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SearchMovieEntityUseCase extends UseCase<List<MovieEntity>> {

    private final Observable<String> keywordStreamObservable;
    private final MovieEntityRepository movieListRepository;
    private final Scheduler scheduler;

    @Inject
    public SearchMovieEntityUseCase(ThreadExecutor threadExecutor, PostThread UIThread,
                                    Observable<String> keywordStreamObservable,
                                    MovieEntityRepository movieListRepository, Scheduler scheduler) {
        super(threadExecutor, UIThread);
        this.movieListRepository = movieListRepository;
        this.keywordStreamObservable = keywordStreamObservable;
        this.scheduler = scheduler;
    }


    @Override
    protected Observable<List<MovieEntity>> build() {
        return keywordStreamObservable.observeOn(scheduler).flatMap(this::search);
    }

    private Observable<List<MovieEntity>> search(String keyword) {
        return this.movieListRepository.list(keyword).toObservable();

    }

}
