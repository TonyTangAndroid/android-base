package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SearchMovieEntityUseCase extends UseCase<List<MovieEntity>> {

    private final Observable<String> keywordStreamObservable;
    private final MovieEntityRepository movieListRepository;

    @Inject
    public SearchMovieEntityUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                                    Observable<String> keywordStreamObservable,
                                    MovieEntityRepository movieListRepository) {
        super(threadExecutor, UIThread);
        this.movieListRepository = movieListRepository;
        this.keywordStreamObservable = keywordStreamObservable;
    }


    @Override
    protected Observable<List<MovieEntity>> build() {
        return keywordStreamObservable.flatMap(this::search);
    }

    private Observable<List<MovieEntity>> search(String keyword) {
        return this.movieListRepository.list(keyword).toObservable();

    }

}
